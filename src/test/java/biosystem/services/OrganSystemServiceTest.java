package biosystem.services;

import biosystem.domain.entities.OrganSystem;
import biosystem.domain.models.service.OrganSystemServiceModel;
import biosystem.repository.OrganSystemRepository;
import biosystem.serviceImpl.OrganSystemServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrganSystemServiceTest {
    @InjectMocks
    private OrganSystemServiceImpl organSystemServiceImpl;
    @Mock
    private OrganSystemRepository organSystemRepository;
    @Mock
    private ModelMapper modelMapper;

    private List<OrganSystem> organSystems;

    private List<OrganSystemServiceModel> organSystemServiceModels;

    @Before
    public void setupTest() {
        organSystems = new ArrayList<>();
        organSystemServiceModels = new ArrayList<>();
        when(organSystemRepository.findAllOrderByName()).thenReturn(organSystems);
    }

    @Test
    public void findAllOrderedByName_whenHasOrganSystem_returnOrganSystems() {
        List<OrganSystem> organSystemsList = List.of(
                new OrganSystem() {{
                    setId("1457");
                    setName("Cardiac system");
                }},
                new OrganSystem() {{
                    setId("8546");
                    setName("Neural system");
                }}
        );
        organSystems.addAll(organSystemsList);

        List<OrganSystemServiceModel> organSystemServiceModelList = List.of(
                new OrganSystemServiceModel() {{
                    setId("1457");
                    setName("Cardiac system");
                }},
                new OrganSystemServiceModel() {{
                    setId("8546");
                    setName("Neural system");
                }}
        );
        organSystemServiceModels.addAll(organSystemServiceModelList);

        for (int i = 0; i < organSystems.size(); i++) {
            when(modelMapper.map(organSystems.get(i), OrganSystemServiceModel.class)).thenReturn(organSystemServiceModels.get(i));
        }

        List<OrganSystemServiceModel> organSystemsOrderedByName = this.organSystemServiceImpl.findAllOrderedByName();
        int size = organSystemServiceImpl.findAllOrderedByName().size();

        assertEquals(2, size);
        assertEquals("Neural system", organSystemsOrderedByName.get(1).getName());
    }

    @Test
    public void findAllOrderedByName_whenNoOrganSystem_returnEmpty() {

        for (int i = 0; i < organSystems.size(); i++) {
            when(modelMapper.map(organSystems.get(i), OrganSystemServiceModel.class)).thenReturn(organSystemServiceModels.get(i));
        }

        List<OrganSystemServiceModel> organSystemsOrderedByName = this.organSystemServiceImpl.findAllOrderedByName();

        assertTrue(organSystemsOrderedByName.isEmpty());
    }

    @Test
    public void saveOrganSystem_whenHasOrganSystem_saveOrganSystem() {
        OrganSystem organSystem = new OrganSystem() {{
            setId("15478");
            setName("Respiratory system");
        }};

        OrganSystemServiceModel organSystemServiceModesl = new OrganSystemServiceModel() {{
            setId("15478");
            setName("Respiratory system");
        }};

        when(modelMapper.map(organSystemServiceModesl, OrganSystem.class)).thenReturn(organSystem);
        when(organSystemRepository.saveAndFlush(organSystem)).thenReturn(organSystem);
        when(modelMapper.map(organSystem, OrganSystemServiceModel.class)).thenReturn(organSystemServiceModesl);

        OrganSystemServiceModel organSystemServiceModel = this.organSystemServiceImpl.saveOrganSystem(organSystemServiceModesl);

        assertEquals("15478", organSystemServiceModel.getId());
        assertEquals(18, organSystemServiceModel.getName().length());
        assertEquals("Respiratory system", organSystemServiceModel.getName());
    }

    @Test
    public void editOrganSystem_WhenHasOrganSystem_editOrganSystem() {
        OrganSystem organSystem = new OrganSystem() {{
            setId("4854");
            setName("Neural system");
        }};
        OrganSystemServiceModel organSystemServiceModel = new OrganSystemServiceModel() {{
            setId("13");
            setName("Cardiac system");
        }};
        when(organSystemRepository.getOne(any())).thenReturn(organSystem);
        when(modelMapper.map(organSystemServiceModel, OrganSystem.class)).thenReturn(organSystem);
        when(organSystemRepository.save(organSystem)).thenReturn(organSystem);

        organSystemServiceImpl.editOrganSystem(organSystemServiceModel);

        assertEquals("Neural system", organSystem.getName());
    }

    @Test
    public void findById_WhenHasOrganSystem_returnOrganSystem() { /*findById*/

        OrganSystem organSystem = new OrganSystem() {{
            setId("458965");
            setName("Respiratory system");
        }};
        OrganSystemServiceModel organSystemServiceModel = new OrganSystemServiceModel() {{
            setId("458965");
            setName("Respiratory system");
        }};
        when(this.organSystemRepository.findById("458965")).thenReturn(java.util.Optional.of(organSystem));
        when(modelMapper.map(organSystem, OrganSystemServiceModel.class)).thenReturn(organSystemServiceModel);

        OrganSystemServiceModel foundOrganSystem = this.organSystemServiceImpl.findById("458965");
        assertEquals("458965", foundOrganSystem.getId());
        assertEquals("Respiratory system", foundOrganSystem.getName());
    }
}
