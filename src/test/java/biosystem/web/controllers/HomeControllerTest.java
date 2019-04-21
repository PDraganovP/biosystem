package biosystem.web.controllers;

import biosystem.BioSystemApplication;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BioSystemApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class HomeControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    public void testIndex() throws Exception {
        this.mvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
                .andExpect(view().name("home/index"));

    }

    @Test
    @WithMockUser(roles = "USER")
    public void testHome() throws Exception {
        this.mvc.perform(get("/home")).andDo(print()).andExpect(status().isOk())
                .andExpect(view().name("home/home"));

    }
}
