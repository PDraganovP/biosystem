package biosystem.web.controllers;

import biosystem.BioSystemApplication;
import biosystem.domain.entities.OrganSystem;
import biosystem.repository.OrganSystemRepository;
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
public class OrganSystemControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private OrganSystemRepository organSystemRepository;

    @Before
    public void setUp() {
        this.organSystemRepository.deleteAll();
        OrganSystem firstOrganSystem = new OrganSystem();
        firstOrganSystem.setName("Respiratory system");

        OrganSystem secondOrganSystem = new OrganSystem();
        secondOrganSystem.setName("Urinary system");

        this.organSystemRepository.saveAndFlush(firstOrganSystem);
        this.organSystemRepository.saveAndFlush(secondOrganSystem);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testShow() throws Exception {
        this.mvc.perform(get("/organSystems/show")).andDo(print()).andExpect(status().isOk())
                .andExpect(view().name("organSystems/all-organSystems"));

    }

    @Test
    @WithMockUser(roles = "MODERATOR")
    public void testGetAddOrganSystemPage() throws Exception {
        this.mvc.perform(get("/organSystems/add")).andDo(print()).andExpect(status().isOk())
                .andExpect(view().name("organSystems/add-organSystem"));

    }

    @Test
    @WithMockUser(roles = "MODERATOR")
    public void testGetEditOrganSystemPage() throws Exception {
        this.mvc.perform(get("/organSystems/edit")).andDo(print()).andExpect(status().isNotFound());

    }

    @Test
    @WithMockUser(roles = "MODERATOR")
    public void testAddOrganSystem() throws Exception {
        this.mvc.perform(post("/organSystems/add")
                .param("name", "Cardiac system")
                .param("size", "10"));

        this.mvc.perform(post("/organSystems/add")
                .param("name", "Neural system")
                .param("size", "19")
        );

        List<OrganSystem> organSystems = organSystemRepository.findAll();
        String name = organSystems.get(3).getName();
        int size = organSystemRepository.findAll().size();

        assertEquals(4, size);
        assertEquals("Neural system", name);
    }

    @Test
    @WithMockUser(roles = "MODERATOR")
    public void testEditOrganSystem() throws Exception {
        OrganSystem firstOrganSystem = this.organSystemRepository.findAll().get(0);
        OrganSystem secondOrganSystem = this.organSystemRepository.findAll().get(1);

        this.mvc
                .perform(post("/organSystems/edit/" + firstOrganSystem.getId())
                        .param("name", "respiratory system")
                        .param("size", "17")
                );

        this.mvc
                .perform(post("/organSystems/edit/" + secondOrganSystem.getId())
                        .param("name", "urinary system")
                        .param("size", "158")
                );

        OrganSystem firstEditedOrganSystem = this.organSystemRepository.findById(firstOrganSystem.getId()).orElse(null);
        OrganSystem secondEditedOrganSystem = this.organSystemRepository.findById(secondOrganSystem.getId()).orElse(null);

        assertEquals("respiratory system", firstEditedOrganSystem.getName());
        assertEquals("urinary system", secondEditedOrganSystem.getName());

    }

    @Test
    @WithMockUser(roles = "MODERATOR")
    public void testDeleteOrganSystem() throws Exception {
        OrganSystem firstOrganSystem = this.organSystemRepository.findAll().get(0);
        OrganSystem secondOrganSystem = this.organSystemRepository.findAll().get(1);
        this.mvc
                .perform(
                        post("/organSystems/delete/" + secondOrganSystem.getId())
                );
        assertEquals(1, this.organSystemRepository.count());
    }
}
