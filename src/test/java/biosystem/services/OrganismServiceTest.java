package biosystem.services;

import biosystem.domain.entities.Organism;
import biosystem.domain.models.service.OrganismServiceModel;
import biosystem.repository.OrganismRepository;
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
public class OrganismServiceTest {
    @InjectMocks
    private OrganismServiceImpl organismServiceImpl;
    @Mock
    private OrganismRepository organismRepository;
    @Mock
    private ModelMapper modelMapper;

    private List<Organism> organisms;

    private List<OrganismServiceModel> organismServiceModels;

    @Before
    public void setupTest() {
        organisms = new ArrayList<>();
        organismServiceModels = new ArrayList<>();
        when(organismRepository.findAllOrderByName()).thenReturn(organisms);
    }

    @Test
    public void findAllOrderedByName_whenHasOrganism_returnOrganisms() {
        List<Organism> organismsList = List.of(
                new Organism() {{
                    setId("9578");
                    setSpeciesName("Fish");
                }},
                new Organism() {{
                    setId("47895");
                    setSpeciesName("Zebra");
                }}
        );
        organisms.addAll(organismsList);

        List<OrganismServiceModel> organismServiceModelList = List.of(
                new OrganismServiceModel() {{
                    setId("9578");
                    setSpeciesName("Fish");
                }},
                new OrganismServiceModel() {{
                    setId("47895");
                    setSpeciesName("Zebra");
                }}
        );
        organismServiceModels.addAll(organismServiceModelList);

        for (int i = 0; i < organisms.size(); i++) {
            when(modelMapper.map(organisms.get(i), OrganismServiceModel.class)).thenReturn(organismServiceModels.get(i));
        }

        List<OrganismServiceModel> organismsOrderedByName = this.organismServiceImpl.findAllOrderedByName();
        int size = organismServiceImpl.findAllOrderedByName().size();

        assertEquals(2, size);
        assertEquals("Fish", organismsOrderedByName.get(0).getSpeciesName());
        assertEquals("Zebra", organismsOrderedByName.get(1).getSpeciesName());
    }

    @Test
    public void findAllOrderedByName_whenNoOrganism_returnEmpty() {

        for (int i = 0; i < organisms.size(); i++) {
            when(modelMapper.map(organisms.get(i), OrganismServiceModel.class)).thenReturn(organismServiceModels.get(i));
        }

        List<OrganismServiceModel> organismsOrderedByName = this.organismServiceImpl.findAllOrderedByName();

        assertTrue(organismsOrderedByName.isEmpty());
    }

    @Test
    public void saveOrganism_whenHasOrganism_saveOrganism() {
        Organism organism = new Organism() {{
            setId("4859");
            setSpeciesName("Elephant");
        }};

        OrganismServiceModel organismServiceModesl = new OrganismServiceModel() {{
            setId("4859");
            setSpeciesName("Elephant");
        }};

        when(modelMapper.map(organismServiceModesl, Organism.class)).thenReturn(organism);
        when(organismRepository.saveAndFlush(organism)).thenReturn(organism);
        when(modelMapper.map(organism, OrganismServiceModel.class)).thenReturn(organismServiceModesl);

        OrganismServiceModel organismServiceModel = this.organismServiceImpl.saveOrganism(organismServiceModesl);

        assertEquals("4859", organismServiceModel.getId());
        assertEquals(8, organismServiceModel.getSpeciesName().length());
        assertEquals("Elephant", organismServiceModel.getSpeciesName());
    }

    @Test
    public void editOrganism_WhenHasOrganism_editOrganism() {
        Organism organism = new Organism() {{
            setId("1153");
            setSpeciesName("Lion");
        }};
        OrganismServiceModel organismServiceModel = new OrganismServiceModel() {{
            setId("1153");
            setSpeciesName("Bear");
        }};
        when(organismRepository.getOne(any())).thenReturn(organism);
        when(modelMapper.map(organismServiceModel, Organism.class)).thenReturn(organism);
        when(organismRepository.save(organism)).thenReturn(organism);

        organismServiceImpl.editOrganism(organismServiceModel);

        assertEquals("Lion", organism.getSpeciesName());
    }

    @Test
    public void findById_WhenHasOrganism_returnOrganism() {

        Organism organism = new Organism() {{
            setId("2589");
            setSpeciesName("Leopard");
        }};
        OrganismServiceModel organismServiceModel = new OrganismServiceModel() {{
            setId("2589");
            setSpeciesName("Leopard");
        }};
        when(this.organismRepository.findById("2589")).thenReturn(java.util.Optional.of(organism));
        when(modelMapper.map(organism, OrganismServiceModel.class)).thenReturn(organismServiceModel);

        OrganismServiceModel foundOrganism = this.organismServiceImpl.findById("2589");
        assertEquals("2589", foundOrganism.getId());
        assertEquals("Leopard", foundOrganism.getSpeciesName());
    }
}
