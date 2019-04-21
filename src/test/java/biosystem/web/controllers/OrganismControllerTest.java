package biosystem.web.controllers;

import biosystem.BioSystemApplication;
import biosystem.domain.entities.Organism;
import biosystem.domain.entities.enums.OrganismType;
import biosystem.repository.OrganismRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BioSystemApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class OrganismControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private OrganismRepository organismRepository;

    @Before
    public void setUp() {
        this.organismRepository.deleteAll();
        Organism firstOrganism = new Organism();
        firstOrganism.setSpeciesName("lion");
        firstOrganism.setOrganismType(OrganismType.ANIMAL);

        Organism secondOrganism = new Organism();
        secondOrganism.setSpeciesName("zebra");
        secondOrganism.setOrganismType(OrganismType.ANIMAL);

        this.organismRepository.saveAndFlush(firstOrganism);
        this.organismRepository.saveAndFlush(secondOrganism);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testShow() throws Exception {
        this.mvc.perform(get("/organisms/show")).andDo(print()).andExpect(status().isOk())
                .andExpect(view().name("organisms/all-organisms"));

    }

    @Test
    @WithMockUser(roles = "MODERATOR")
    public void testGetAddOrganismPage() throws Exception {
        this.mvc.perform(get("/organisms/add")).andDo(print()).andExpect(status().isOk())
                .andExpect(view().name("organisms/add-organism"));

    }

    @Test
    @WithMockUser(roles = "MODERATOR")
    public void testGetEditOrganismPage() throws Exception {
        this.mvc.perform(get("/organisms/edit")).andDo(print()).andExpect(status().isNotFound());

    }

    @Test
    @WithMockUser(roles = "MODERATOR")
    public void testAddOrganism() throws Exception {
        this.mvc.perform(post("/organisms/add")
                .param("speciesName", "Tree")
                .param("organismType", "PLANT"));

        this.mvc.perform(post("/organisms/add")
                .param("speciesName", "Lemur")
                .param("organismType", "ANIMAL")
        );

        List<Organism> organisms = organismRepository.findAll();
        String name = organisms.get(3).getSpeciesName();
        int size = organismRepository.findAll().size();

        assertEquals(4, size);
        assertEquals("Lemur", name);
    }

    @Test
    @WithMockUser(roles = "MODERATOR")
    public void testEditOrganism() throws Exception {
        Organism firstOrganism = this.organismRepository.findAll().get(0);
        Organism secondOrganism = this.organismRepository.findAll().get(1);

        this.mvc
                .perform(post("/organisms/edit/" + firstOrganism.getId())
                        .param("speciesName", "Lion")
                        .param("organismType", "ANIMAL")
                );

        this.mvc
                .perform(post("/organisms/edit/" + secondOrganism.getId())
                        .param("speciesName", "ZEBRA")
                        .param("organismType", "ANIMAL")
                );

        Organism firstEditedOrganism = this.organismRepository.findById(firstOrganism.getId()).orElse(null);
        Organism secondEditedOrganism = this.organismRepository.findById(secondOrganism.getId()).orElse(null);

        assertEquals("Lion", firstEditedOrganism.getSpeciesName());
        assertEquals("ZEBRA", secondEditedOrganism.getSpeciesName());
    }

    @Test
    @WithMockUser(roles = "MODERATOR")
    public void testDeleteOrganism() throws Exception {
        Organism firstOrganism = this.organismRepository.findAll().get(0);
        Organism secondOrganism = this.organismRepository.findAll().get(1);
        this.mvc
                .perform(
                        post("/organisms/delete/" + firstOrganism.getId())
                );
        assertEquals(1, this.organismRepository.count());
    }
}
