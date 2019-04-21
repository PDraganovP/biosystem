package biosystem.web.controllers;

import biosystem.BioSystemApplication;
import biosystem.domain.entities.Organ;
import biosystem.domain.entities.enums.OrganType;
import biosystem.repository.OrganRepository;
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
public class OrganControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private OrganRepository organRepository;

    @Before
    public void setUp() {
        this.organRepository.deleteAll();
        Organ firstOrgan = new Organ();
        firstOrgan.setName("Brain");
        firstOrgan.setOrganType(OrganType.INTERNAL);

        Organ secondOrgan = new Organ();
        secondOrgan.setName("Liver");
        secondOrgan.setOrganType(OrganType.INTERNAL);

        this.organRepository.saveAndFlush(firstOrgan);
        this.organRepository.saveAndFlush(secondOrgan);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testShow() throws Exception {
        this.mvc.perform(get("/organs/show")).andDo(print()).andExpect(status().isOk())
                .andExpect(view().name("organs/all-organs"));
    }

    @Test
    @WithMockUser(roles = "MODERATOR")
    public void testGetAddOrganPage() throws Exception {
        this.mvc.perform(get("/organs/add")).andDo(print()).andExpect(status().isOk())
                .andExpect(view().name("organs/add-organ"));
    }

    @Test
    @WithMockUser(roles = "MODERATOR")
    public void testGetEditOrganPage() throws Exception {
        this.mvc.perform(get("/organs/edit")).andDo(print()).andExpect(status().isNotFound());

    }

    @Test
    @WithMockUser(roles = "USER")
    public void testGetCompareTissuesPage() throws Exception {
        this.mvc.perform(get("/organs/compareOrgans")).andDo(print()).andExpect(status().isOk())
                .andExpect(view().name("organs/compare-organs"));
    }

    @Test
    @WithMockUser(roles = "MODERATOR")
    public void testAddOrgan() throws Exception {
        this.mvc.perform(post("/organs/add")
                .param("name", "Hearth")
                .param("organType", "INTERNAL"));

        this.mvc.perform(post("/organs/add")
                .param("name", "Lung")
                .param("organType", "INTERNAL"));


        List<Organ> organs = organRepository.findAll();
        String name = organs.get(3).getName();
        String type = organs.get(3).getOrganType().getType();
        int size = this.organRepository.findAll().size();

        assertEquals(4, size);
        assertEquals("Internal", type);
        assertEquals("Lung", name);
    }

    @Test
    @WithMockUser(roles = "MODERATOR")
    public void testEditOrgan() throws Exception {
        Organ firstOrgan = this.organRepository.findAll().get(0);
        Organ secondOrgan = this.organRepository.findAll().get(1);

        this.mvc
                .perform(post("/organs/edit/" + firstOrgan.getId())
                        .param("name", "BRAIN")
                        .param("organType", "INTERNAL")
                );

        this.mvc
                .perform(post("/organs/edit/" + secondOrgan.getId())
                        .param("name", "LIVER")
                        .param("organType", "INTERNAL")
                );

        Organ firstEditedOrgan = this.organRepository.findById(firstOrgan.getId()).orElse(null);
        Organ secondEditedOrgan = this.organRepository.findById(secondOrgan.getId()).orElse(null);

        assertEquals("BRAIN", firstEditedOrgan.getName());
        assertEquals("LIVER", secondEditedOrgan.getName());
    }

    @Test
    @WithMockUser(roles = "MODERATOR")
    public void testDeleteOrgan() throws Exception {
        Organ firstOrgan = this.organRepository.findAll().get(0);
        Organ secondOrgan = this.organRepository.findAll().get(1);
        this.mvc
                .perform(
                        post("/organs/delete/" + firstOrgan.getId())
                );
        assertEquals(1, this.organRepository.count());
    }
}
