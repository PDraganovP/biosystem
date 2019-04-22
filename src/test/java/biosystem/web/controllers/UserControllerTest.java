package biosystem.web.controllers;

import biosystem.BioSystemApplication;
import biosystem.domain.entities.Role;
import biosystem.domain.entities.User;
import biosystem.repository.RoleRepository;
import biosystem.repository.UserRepository;
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

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BioSystemApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Before
    public void setUp() {
        this.userRepository.deleteAll();
        this.roleRepository.deleteAll();

        this.roleRepository.saveAndFlush(new Role("ROLE_USER"));
        this.roleRepository.saveAndFlush(new Role("ROLE_MODERATOR"));
        this.roleRepository.saveAndFlush(new Role("ROLE_ADMIN"));
        this.roleRepository.saveAndFlush(new Role("ROLE_ROOT"));

        User user = new User();
        user.setUsername("Kaloyan");
        user.setPassword("1207");
        user.setEmail("kaloyan@mnm");

        this.userRepository.saveAndFlush(user);
    }

    @Test
    public void testRegister() throws Exception {
        this.mvc.perform(get("/users/register")).andDo(print()).andExpect(status().isOk())
                .andExpect(view().name("users/register"));
    }

    @Test
    public void testLogin() throws Exception {
        this.mvc.perform(get("/users/login")).andDo(print()).andExpect(status().isOk())
                .andExpect(view().name("users/login"));
    }

    @Test
    @WithMockUser(roles = "USER", username = "Kaloyan", password = "1207")
    public void testProfile() throws Exception {
        this.mvc.perform(get("/users/profile")).andDo(print())
                .andExpect(view().name("users/profile"));
         }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testAllUsers() throws Exception {
        this.mvc.perform(get("/users/all")).andDo(print()).andExpect(status().isOk())
                .andExpect(view().name("users/all-users"));
    }

    @Test
        public void testRegisterConfirm() throws Exception {
        this.mvc
                .perform(post("/users/register")
                        .param("username", "Simeon")
                        .param("password", "boris")
                        .param("confirmPassword", "boris")
                        .param("email", "boris@nmk"));

        this.mvc
                .perform(post("/users/register")
                        .param("username", "Ivan")
                        .param("password", "Asen")
                        .param("confirmPassword", "Asen")
                        .param("email", "ivan@nmk"));

        User firstUser = userRepository.findAll().get(1);
        User secondUser = userRepository.findAll().get(2);

        int size = this.userRepository.findAll().size();

        assertEquals(3, size);
        assertEquals("Simeon", firstUser.getUsername());
        assertEquals("Ivan", secondUser.getUsername());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testSetUser() throws Exception {
              User user = this.userRepository.findAll().get(0);
        this.mvc
                .perform(post("/users/set-user/" + user.getId()))
                .andExpect(view().name("redirect:/users/all"));
          }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testSetModerator() throws Exception {
              User user = this.userRepository.findAll().get(0);
        this.mvc
                .perform(post("/users/set-moderator/" + user.getId()))
                .andExpect(view().name("redirect:/users/all"));
           }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testSetAdmin() throws Exception {
        User user = this.userRepository.findAll().get(0);
        this.mvc
                .perform(post("/users/set-admin/" + user.getId()))
                .andExpect(view().name("redirect:/users/all"));
    }

    @Test
    @WithMockUser(roles = "USER", username = "Kaloyan", password = "1207")
    public void testEditProfileConfirm() throws Exception {
        this.mvc.perform(get("/users/edit"))
                .andExpect(view().name("users/edit-profile"));
    }

}
