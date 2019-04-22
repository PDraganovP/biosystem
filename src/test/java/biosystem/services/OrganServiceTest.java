package biosystem.services;

import biosystem.domain.entities.Organ;
import biosystem.domain.models.service.OrganServiceModel;
import biosystem.repository.OrganRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class OrganServiceTest {
    @InjectMocks
    private OrganServiceImpl organServiceImpl;
    @Mock
    private OrganRepository organRepository;
    @Mock
    private ModelMapper modelMapper;

    private List<Organ> organs;

    private List<OrganServiceModel> organServiceModels;

    @Before
    public void setupTest() {
        organs = new ArrayList<>();
        organServiceModels = new ArrayList<>();
        when(organRepository.findAllOrderByName()).thenReturn(organs);
    }

    @Test
    public void findAllOrderedByName_whenHasOrgan_returnOrgans() {
        List<Organ> organsList = List.of(
                new Organ() {{
                    setId("8564");
                    setName("Heart");
                }},
                new Organ() {{
                    setId("98265");
                    setName("Liver");
                }}
        );
        organs.addAll(organsList);

        List<OrganServiceModel> organServiceModelList = List.of(
                new OrganServiceModel() {{
                    setId("8564");
                    setName("Heart");
                }},
                new OrganServiceModel() {{
                    setId("98265");
                    setName("Liver");
                }}
        );
        organServiceModels.addAll(organServiceModelList);

        for (int i = 0; i < organs.size(); i++) {
            when(modelMapper.map(organs.get(i), OrganServiceModel.class)).thenReturn(organServiceModels.get(i));
        }

        List<OrganServiceModel> organsOrderedByName = this.organServiceImpl.findAllOrderedByName();
        int size = organServiceImpl.findAllOrderedByName().size();

        assertEquals(2, size);
        assertEquals("Liver", organsOrderedByName.get(1).getName());
    }

    @Test
    public void findAllOrderedByName_whenNoOrgan_returnEmpty() {

        for (int i = 0; i < organs.size(); i++) {
            when(modelMapper.map(organs.get(i), OrganServiceModel.class)).thenReturn(organServiceModels.get(i));
        }

        List<OrganServiceModel> organsOrderedByName = this.organServiceImpl.findAllOrderedByName();

        assertTrue(organsOrderedByName.isEmpty());
    }

    @Test
    public void saveOrgan_whenHasOrgan_saveOrgan() {
        Organ organ = new Organ() {{
            setId("59864");
            setName("Stomach");
        }};

        OrganServiceModel organServiceModesl = new OrganServiceModel() {{
            setId("59864");
            setName("Stomach");
        }};

        when(modelMapper.map(organServiceModesl, Organ.class)).thenReturn(organ);
        when(organRepository.saveAndFlush(organ)).thenReturn(organ);
        when(modelMapper.map(organ, OrganServiceModel.class)).thenReturn(organServiceModesl);

        OrganServiceModel organServiceModel = this.organServiceImpl.saveOrgan(organServiceModesl);

        assertEquals("59864", organServiceModel.getId());
        assertEquals(7, organServiceModel.getName().length());
        assertEquals("Stomach", organServiceModel.getName());
    }

    @Test
    public void editOrgan_WhenHasOrgan_editOrgan() {
        Organ organ = new Organ() {{
            setId("13");
            setName("Lung");
        }};
        OrganServiceModel organServiceModel = new OrganServiceModel() {{
            setId("13");
            setName("Leg");
        }};
        when(organRepository.getOne(any())).thenReturn(organ);
        when(modelMapper.map(organServiceModel, Organ.class)).thenReturn(organ);
        when(organRepository.save(organ)).thenReturn(organ);

        organServiceImpl.editOrgan(organServiceModel);

        assertEquals("Lung", organ.getName());
    }

    @Test
    public void findById_WhenHasOrgan_returnOrgan() {

        Organ organ = new Organ() {{
            setId("15587");
            setName("Arm");
        }};
        OrganServiceModel organServiceModel = new OrganServiceModel() {{
            setId("15587");
            setName("Arm");
        }};
        when(this.organRepository.findById("15587")).thenReturn(java.util.Optional.of(organ));
        when(modelMapper.map(organ, OrganServiceModel.class)).thenReturn(organServiceModel);

        OrganServiceModel foundOrgan = this.organServiceImpl.findById("15587");
        assertEquals("15587", foundOrgan.getId());
        assertEquals("Arm", foundOrgan.getName());
    }
}
