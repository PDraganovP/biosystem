package biosystem.services;

import biosystem.domain.entities.Tissue;
import biosystem.domain.models.service.TissueServiceModel;
import biosystem.repository.TissueRepository;
import biosystem.serviceImpl.TissueServiceImpl;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class TissueServiceTest {
    @InjectMocks
    private TissueServiceImpl tissueServiceImpl;
    @Mock
    private TissueRepository tissueRepository;
    @Mock
    private ModelMapper modelMapper;

    private List<Tissue> tissues;

    private List<TissueServiceModel> tissueServiceModels;

    @Before
    public void setupTest() {
        tissues = new ArrayList<>();
        tissueServiceModels = new ArrayList<>();
        when(tissueRepository.findAllOrderByName()).thenReturn(tissues);
    }

    @Test
    public void findAllOrderedByName_whenHasTissue_returnTissues() {
        List<Tissue> tissuesList = List.of(
                new Tissue() {{
                    setId("3698");
                    setName("Epithelial tissue");
                }},
                new Tissue() {{
                    setId("3698");
                    setName("Neural tissue");
                }}
        );
        tissues.addAll(tissuesList);

        List<TissueServiceModel> tissueServiceModelList = List.of(
                new TissueServiceModel() {{
                    setId("3698");
                    setName("Epithelial tissue");
                }},
                new TissueServiceModel() {{
                    setId("3698");
                    setName("Neural tissue");
                }}
        );
        tissueServiceModels.addAll(tissueServiceModelList);

        for (int i = 0; i < tissues.size(); i++) {
            when(modelMapper.map(tissues.get(i), TissueServiceModel.class)).thenReturn(tissueServiceModels.get(i));
        }

        List<TissueServiceModel> tissuesOrderedByName = this.tissueServiceImpl.findAllOrderedByName();
        int size = tissueServiceImpl.findAllOrderedByName().size();

        assertEquals(2, size);
        assertEquals("Neural tissue", tissuesOrderedByName.get(1).getName());
    }

    @Test
    public void findAllOrderedByName_whenNoTissue_returnEmpty() {

        for (int i = 0; i < tissues.size(); i++) {
            when(modelMapper.map(tissues.get(i), TissueServiceModel.class)).thenReturn(tissueServiceModels.get(i));
        }

        List<TissueServiceModel> tissuesOrderedByName = this.tissueServiceImpl.findAllOrderedByName();

        assertTrue(tissuesOrderedByName.isEmpty());
    }

    @Test
    public void saveTissue_whenHasTissue_saveTissue() {
        Tissue tissue = new Tissue() {{
            setId("1586");
            setName("Human tissue");
        }};

        TissueServiceModel tissueServiceModesl = new TissueServiceModel() {{
            setId("1586");
            setName("Human tissue");
        }};

        when(modelMapper.map(tissueServiceModesl, Tissue.class)).thenReturn(tissue);
        when(tissueRepository.saveAndFlush(tissue)).thenReturn(tissue);
        when(modelMapper.map(tissue, TissueServiceModel.class)).thenReturn(tissueServiceModesl);

        TissueServiceModel tissueServiceModel = this.tissueServiceImpl.saveTissue(tissueServiceModesl);

        assertEquals("1586", tissueServiceModel.getId());
        assertEquals(12, tissueServiceModel.getName().length());
        assertEquals("Human tissue", tissueServiceModel.getName());

    }

    @Test
    public void editTissue_WhenHasTissue_editTissue() {
        Tissue tissue = new Tissue() {{
            setId("13");
            setName("Muscular tissue");
            setSize(1.48);
        }};
        TissueServiceModel tissueServiceModel = new TissueServiceModel() {{
            setId("13");
            setName("Neural tissue");
            setSize(1.48);
        }};
        when(tissueRepository.getOne(any())).thenReturn(tissue);
        when(modelMapper.map(tissueServiceModel, Tissue.class)).thenReturn(tissue);
        when(tissueRepository.save(tissue)).thenReturn(tissue);

        tissueServiceImpl.editTissue(tissueServiceModel);

        assertEquals("Muscular tissue", tissue.getName());
    }

    @Test
    public void findById_WhenHasTissue_returnTissue() {
        Tissue tissue = new Tissue() {{
            setId("147854");
            setName("mammal tissue");
        }};
        TissueServiceModel tissueServiceModel = new TissueServiceModel() {{
            setId("147854");
            setName("mammal tissue");
        }};
        when(this.tissueRepository.findById("1245")).thenReturn(java.util.Optional.of(tissue));
        when(modelMapper.map(tissue, TissueServiceModel.class)).thenReturn(tissueServiceModel);

        TissueServiceModel foundTissue = this.tissueServiceImpl.findById("1245");

        assertEquals("147854", foundTissue.getId());
        assertEquals("mammal tissue", foundTissue.getName());
    }
}
