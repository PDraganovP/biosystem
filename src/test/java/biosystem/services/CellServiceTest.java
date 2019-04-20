package biosystem.services;

import biosystem.domain.entities.Cell;
import biosystem.domain.models.service.CellServiceModel;
import biosystem.repository.CellRepository;
import biosystem.serviceImpl.CellServiceImpl;
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
public class CellServiceTest {
    @InjectMocks
    private CellServiceImpl cellServiceImpl;
    @Mock
    private CellRepository cellRepository;
    @Mock
    private ModelMapper modelMapper;

    private List<Cell> cells;

    private List<CellServiceModel> cellServiceModels;

    @Before
    public void setupTest() {
        cells = new ArrayList<>();
        cellServiceModels = new ArrayList<>();
        when(cellRepository.findAllOrderByName()).thenReturn(cells);
    }

    @Test
    public void findAllOrderedByName_whenHasCell_returnCells() {
        List<Cell> cellsList = List.of(
                new Cell() {{
                    setId("124");
                    setName("Bacterial cell");
                }},
                new Cell() {{
                    setId("1478");
                    setName("Plant cell");
                }}
        );
        cells.addAll(cellsList);

        List<CellServiceModel> cellServiceModelList = List.of(
                new CellServiceModel() {{
                    setId("124");
                    setName("Bacterial cell");
                }},
                new CellServiceModel() {{
                    setId("1478");
                    setName("Plant cell");
                }}
        );
        cellServiceModels.addAll(cellServiceModelList);

        for (int i = 0; i < cells.size(); i++) {
            when(modelMapper.map(cells.get(i), CellServiceModel.class)).thenReturn(cellServiceModels.get(i));
        }

        List<CellServiceModel> cellsOrderedByName = this.cellServiceImpl.findAllOrderedByName();
        int size = cellServiceImpl.findAllOrderedByName().size();

        assertEquals(2, size);
        assertEquals("Plant cell", cellsOrderedByName.get(1).getName());
    }

    @Test
    public void findAllOrderedByName_whenNoCell_returnEmpty() {

        for (int i = 0; i < cells.size(); i++) {
            when(modelMapper.map(cells.get(i), CellServiceModel.class)).thenReturn(cellServiceModels.get(i));
        }

        List<CellServiceModel> cellsOrderedByName = this.cellServiceImpl.findAllOrderedByName();

        assertTrue(cellsOrderedByName.isEmpty());
    }

    @Test
    public void saveCell_whenHasCell_saveCell() {
        Cell cell = new Cell() {{
            setId("159");
            setName("Animal cell");
        }};

        CellServiceModel cellServiceModesl = new CellServiceModel() {{
            setId("159");
            setName("Animal cell");
        }};

        when(modelMapper.map(cellServiceModesl, Cell.class)).thenReturn(cell);
        when(cellRepository.saveAndFlush(cell)).thenReturn(cell);
        when(modelMapper.map(cell, CellServiceModel.class)).thenReturn(cellServiceModesl);

        CellServiceModel cellServiceModel = this.cellServiceImpl.saveCell(cellServiceModesl);

        assertEquals("159", cellServiceModel.getId());
        assertEquals(11, cellServiceModel.getName().length());
        assertEquals("Animal cell", cellServiceModel.getName());
    }

    @Test
    public void editCell_WhenHasCell_editCell() {
        Cell cell = new Cell() {{
            setId("13");
            setName("Fungi cell");
            setThereDNA(true);
        }};
        CellServiceModel cellServiceModel = new CellServiceModel() {{
            setId("13");
            setName("Bacterial cell");
            setThereDNA(true);
        }};
        when(cellRepository.getOne(any())).thenReturn(cell);
        when(modelMapper.map(cellServiceModel, Cell.class)).thenReturn(cell);
        when(cellRepository.save(cell)).thenReturn(cell);

        cellServiceImpl.editCell(cellServiceModel);

        assertEquals("Fungi cell", cell.getName());
    }

    @Test
    public void findById_WhenHasCell_returnCell() {

        Cell cell = new Cell() {{
            setId("1245");
            setName("muscular cell");
            setThereDNA(true);
            setThereRibosomes(false);
        }};
        CellServiceModel cellServiceModel = new CellServiceModel() {{
            setId("1245");
            setName("muscular cell");
            setThereDNA(true);
            setThereRibosomes(false);
        }};
        when(this.cellRepository.findById("1245")).thenReturn(java.util.Optional.of(cell));
        when(modelMapper.map(cell, CellServiceModel.class)).thenReturn(cellServiceModel);

        CellServiceModel foundCell = this.cellServiceImpl.findById("1245");
        assertEquals("1245", foundCell.getId());
        assertEquals("muscular cell", foundCell.getName());
        assertTrue(foundCell.isThereDNA());
        assertFalse(foundCell.isThereRibosomes());
    }
}

