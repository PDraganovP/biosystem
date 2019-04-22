package biosystem.services;

import biosystem.domain.entities.Role;
import biosystem.domain.models.service.RoleServiceModel;
import biosystem.repository.RoleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class RoleServiceTest {
    @InjectMocks
    private RoleServiceImpl roleServiceImpl;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private ModelMapper modelMapper;

    @Test
    public void testFindAllRoles_whenHas_returnRoles() {
        List<Role> roles = List.of(
                new Role() {{
                    setAuthority("ROLE_USER");
                }},
                new Role() {{
                    setAuthority("ROLE_MODERATOR");
                }},
                new Role() {{
                    setAuthority("ROLE_ADMIN");
                }}
        );
        List<RoleServiceModel> roleServiceModelList = List.of(
                new RoleServiceModel() {{
                    setAuthority("ROLE_USER");
                }},
                new RoleServiceModel() {{
                    setAuthority("ROLE_MODERATOR");
                }},
                new RoleServiceModel() {{
                    setAuthority("ROLE_ADMIN");
                }}
        );

        when(this.roleRepository.findAll()).thenReturn(roles);
        for (int i = 0; i < roles.size(); i++) {
            when(this.modelMapper.map(roles.get(i), RoleServiceModel.class))
                    .thenReturn(roleServiceModelList.get(i));
        }

        Set<RoleServiceModel> allRoles = this.roleServiceImpl.findAllRoles();
        int size = allRoles.size();

        assertEquals(3, size);
    }

    @Test
    public void testFindByAuthority_whenHasAuthority_returnRole() {
        Role role = new Role();
        role.setAuthority("ROLE_USER");

        RoleServiceModel roleServiceModel = new RoleServiceModel();
        roleServiceModel.setAuthority("ROLE_USER");

        when(this.roleRepository.findByAuthority("ROLE_USER")).thenReturn(role);
        when(this.modelMapper.map(role, RoleServiceModel.class)).thenReturn(roleServiceModel);

        RoleServiceModel foundRole = this.roleServiceImpl.findByAuthority("ROLE_USER");
        String authority = foundRole.getAuthority();

        assertEquals("ROLE_USER", authority);

    }
}
