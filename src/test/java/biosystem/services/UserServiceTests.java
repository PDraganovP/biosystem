package biosystem.services;

import biosystem.domain.entities.User;
import biosystem.domain.models.service.UserServiceModel;
import biosystem.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.LinkedHashSet;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UserServiceTests {
    @InjectMocks
    private UserServiceImpl userServiceImpl;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleService roleService;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private User user;

    private UserServiceModel userServiceModel;

    @Before
    public void setupTest() {
        userServiceModel = new UserServiceModel();
        userServiceModel.setUsername("simeon");
        userServiceModel.setPassword("123");
        userServiceModel.setEmail("simeon@bnh");

        user = new User();
        user.setUsername("simeon");
        user.setPassword("123");
        user.setEmail("simeon@bnh");

    }


    @Test
    public void testRegisterUser_withUsernameAndPassword_returnUserServiceModel() {
        when(modelMapper.map(userServiceModel, User.class)).thenReturn(user);
        when(this.userRepository.saveAndFlush(user)).thenReturn(user);
        when(modelMapper.map(user, UserServiceModel.class)).thenReturn(userServiceModel);
        when(this.bCryptPasswordEncoder.encode(userServiceModel.getPassword()))
                .thenReturn("123");
        user.setPassword(this.bCryptPasswordEncoder.encode(userServiceModel.getPassword()));

        UserServiceModel savedUser = this.userServiceImpl.registerUser(userServiceModel);

        String username = savedUser.getUsername();
        String email = savedUser.getEmail();

        assertEquals("simeon", username);
        assertEquals("simeon@bnh", email);

    }

    @Test
    public void testLoadUserByUsername_whenHasUsername_returnUser() {
        when(this.userRepository.findByUsername("simeon")).thenReturn(java.util.Optional.of(user));

        UserDetails simeon = this.userServiceImpl.loadUserByUsername("simeon");
        String username = simeon.getUsername();
        String password = simeon.getPassword();

        assertEquals("123", password);
        assertEquals("simeon", username);
    }

    @Test
    public void testFindUserByUserName_whenHasUsername_returnUserServiceModel() {
        when(this.userRepository.findByUsername("simeon")).thenReturn(java.util.Optional.of(user));
        when(this.modelMapper.map(user, UserServiceModel.class)).thenReturn(userServiceModel);

        UserServiceModel foundUser = this.userServiceImpl.findUserByUserName("simeon");
        String username = foundUser.getUsername();
        String password = foundUser.getPassword();

        assertEquals("123", password);
        assertEquals("simeon", username);
    }


    @Test
    public void testEditUserProfile_withUsernameAndPassword_returnUserServiceModel() {
        when(this.userRepository.findByUsername("simeon")).thenReturn(java.util.Optional.of(user));
        when(this.userRepository.saveAndFlush(user)).thenReturn(user);
        when(modelMapper.map(user, UserServiceModel.class)).thenReturn(userServiceModel);
        when(this.bCryptPasswordEncoder.encode(userServiceModel.getPassword()))
                .thenReturn("123");
        when(this.bCryptPasswordEncoder.matches("123", user.getPassword()))
                .thenReturn(true);
        user.setPassword(this.bCryptPasswordEncoder.encode(userServiceModel.getPassword()));

        UserServiceModel savedUser = this.userServiceImpl
                .editUserProfile(userServiceModel, userServiceModel.getPassword());

        String username = savedUser.getUsername();
        String email = savedUser.getEmail();

        assertEquals("simeon", username);
        assertEquals("simeon@bnh", email);

    }

    @Test
    public void testFindAllUsers_wheHasUser_returnUsers() {
        List<User> users = List.of(
                new User() {{
                    setUsername("Ivan");
                    setPassword("1598");

                }}, new User() {{
                    setUsername("boris");
                    setPassword("1478");
                }}
        );
        List<UserServiceModel> userServiceModels = List.of(
                new UserServiceModel() {{
                    setUsername("Ivan");
                    setPassword("1598");
                }},
                new UserServiceModel() {{
                    setUsername("boris");
                    setPassword("1478");
                }}
        );

        when(this.userRepository.findAll()).thenReturn(users);
        for (int i = 0; i < users.size(); i++) {
            when(this.modelMapper.map(users.get(i), UserServiceModel.class))
                    .thenReturn(userServiceModels.get(i));
        }
        List<UserServiceModel> allUsers = this.userServiceImpl.findAllUsers();

        int size = allUsers.size();
        String username = allUsers.get(1).getUsername();

        assertEquals(2, size);
        assertEquals("boris", username);
    }

    @Test
    public void testSetUserRole_whenHasRole_saveChanges() {
        UserServiceModel userServiceModel = new UserServiceModel();
        userServiceModel.setUsername("simeon");
        userServiceModel.setPassword("123");
        userServiceModel.setEmail("simeon@bnh");
        userServiceModel.setAuthorities(new LinkedHashSet<>());

        User user = new User();
        user.setUsername("Simeon");
        user.setPassword("123");
        user.setEmail("simeon@bnh");
        user.setAuthorities(new LinkedHashSet<>());

        when(this.userRepository.findById("7538")).thenReturn(java.util.Optional.of(user));
        when(this.modelMapper.map(user, UserServiceModel.class)).thenReturn(userServiceModel);
        when(this.modelMapper.map(userServiceModel, User.class)).thenReturn(user);
        when(this.userRepository.saveAndFlush(user)).thenReturn(user);

        this.userServiceImpl.setUserRole("7538", "user");
    }

}


