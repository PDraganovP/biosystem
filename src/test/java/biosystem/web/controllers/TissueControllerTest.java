package biosystem.web.controllers;

import biosystem.BioSystemApplication;
import biosystem.domain.entities.Tissue;
import biosystem.domain.entities.enums.TissueType;
import biosystem.repository.TissueRepository;
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
public class TissueControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private TissueRepository tissueRepository;

    @Before
    public void setUp() {
        this.tissueRepository.deleteAll();
        Tissue firstTissue = new Tissue();
        firstTissue.setName("Muscular tissue");
        firstTissue.setTissueType(TissueType.ANIMAL_TISSUE);

        Tissue secondTissue = new Tissue();
        secondTissue.setName("Animal tissue");
        secondTissue.setTissueType(TissueType.ANIMAL_TISSUE);

        firstTissue = this.tissueRepository.saveAndFlush(firstTissue);
        secondTissue = this.tissueRepository.saveAndFlush(secondTissue);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testShow() throws Exception {
        this.mvc.perform(get("/tissues/show")).andDo(print()).andExpect(status().isOk())
                .andExpect(view().name("tissues/all-tissues"));
    }

    @Test
    @WithMockUser(roles = "MODERATOR")
    public void testGetAddTissuePage() throws Exception {
        this.mvc.perform(get("/tissues/add")).andDo(print()).andExpect(status().isOk())
                .andExpect(view().name("tissues/add-tissue"));
    }

    @Test
    @WithMockUser(roles = "MODERATOR")
    public void testGetEditTissuePage() throws Exception {
        this.mvc.perform(get("/tissues/edit")).andDo(print()).andExpect(status().isNotFound());

    }

    @Test
    @WithMockUser(roles = "USER")
    public void testGetCompareTissuesPage() throws Exception {
        this.mvc.perform(get("/tissues/compareTissues")).andDo(print()).andExpect(status().isOk())
                .andExpect(view().name("tissues/compare-tissues"));
    }

    @Test
    @WithMockUser(roles = "MODERATOR")
    public void testAddTissue() throws Exception {
        this.mvc.perform(post("/tissues/add")
                .param("name", "muscular tissue")
                .param("tissueType", "ANIMAL_TISSUE"));

        this.mvc.perform(post("/tissues/add")
                .param("name", "plant tissue")
                .param("tissueType", "PLANT_TISSUE")
        );

        List<Tissue> tissues = tissueRepository.findAll();
        String name = tissues.get(2).getName();
        int size = tissueRepository.findAll().size();

        assertEquals(4, size);
        assertEquals("muscular tissue", name);
    }

    @Test
    @WithMockUser(roles = "MODERATOR")
    public void testEditTissue() throws Exception {
        Tissue firstTissue = this.tissueRepository.findAll().get(0);
        Tissue secondTissue = this.tissueRepository.findAll().get(1);

        this.mvc
                .perform(post("/tissues/edit/" + firstTissue.getId())
                        .param("name", "muscular tissue")
                        .param("tissueType", "ANIMAL_TISSUE")
                );

        this.mvc
                .perform(post("/tissues/edit/" + secondTissue.getId())
                        .param("name", "animal tissue")
                        .param("tissueType", "ANIMAL_TISSUE")
                );

        Tissue firstEditedTissue = this.tissueRepository.findById(firstTissue.getId()).orElse(null);
        Tissue secondEditedTissue = this.tissueRepository.findById(secondTissue.getId()).orElse(null);

        assertEquals("muscular tissue", firstEditedTissue.getName());
        assertEquals("animal tissue", secondEditedTissue.getName());

    }

    @Test
    @WithMockUser(roles = "MODERATOR")
    public void testDeleteTissue() throws Exception {
              Tissue firstTissue = this.tissueRepository.findAll().get(0);
        Tissue secondTissue = this.tissueRepository.findAll().get(1);

        this.mvc
                .perform(
                        post("/tissues/delete/" + firstTissue.getId())
                );
        assertEquals(1, this.tissueRepository.count());
    }
}
