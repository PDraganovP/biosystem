package biosystem.web.controllers;

import biosystem.BioSystemApplication;
import biosystem.domain.entities.Cell;
import biosystem.domain.entities.enums.CellType;
import biosystem.repository.CellRepository;
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
import static org.springframework.test.util.AssertionErrors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BioSystemApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CellControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private CellRepository cellRepository;

    @Before
    public void setUp() {
        cellRepository.deleteAll();
        Cell firstCell = new Cell();
        firstCell.setName("muscular cell");
        firstCell.setCellType(CellType.EUKARYOTE);
        firstCell.setThereDNA(false);

        Cell secondCell = new Cell();
        secondCell.setName("animal cell");
        secondCell.setCellType(CellType.EUKARYOTE);
        firstCell.setThereDNA(true);

        this.cellRepository.saveAndFlush(firstCell);
        this.cellRepository.saveAndFlush(secondCell);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testShow() throws Exception {
        this.mvc.perform(get("/cells/show")).andDo(print()).andExpect(status().isOk())
                .andExpect(view().name("cells/all-cells"));
    }

    @Test
    @WithMockUser(roles = "MODERATOR")
    public void testGetAddCellPage() throws Exception {
        this.mvc.perform(get("/cells/add")).andDo(print()).andExpect(status().isOk())
                .andExpect(view().name("cells/add-cell"));
    }

    @Test
    @WithMockUser(roles = "MODERATOR")
    public void testGetEditCellPage() throws Exception {
        this.mvc.perform(get("/cells/edit")).andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void testGetCompareCellsPage() throws Exception {
        this.mvc.perform(get("/cells/compareCells")).andDo(print()).andExpect(status().isOk())
                .andExpect(view().name("cells/compare-cells"));
    }

    @Test
    @WithMockUser(roles = "MODERATOR")
    public void testAddCell() throws Exception {
        this.mvc.perform(post("/cells/add")
                .param("name", "Neural cell")
                .param("cellType", "EUKARYOTE"));

        this.mvc.perform(post("/cells/add")
                .param("name", "Bacterial cell")
                .param("cellType", "PROKARYOTE"));

        List<Cell> cells = cellRepository.findAll();
        String name = cells.get(2).getName();
        int size = cellRepository.findAll().size();

        assertEquals(4, size);
        assertEquals("Neural cell", name);
    }

    @Test
    @WithMockUser(roles = "MODERATOR")
    public void testEditCell() throws Exception {
        Cell firstCell = this.cellRepository.findAll().get(0);
        Cell secondCell = this.cellRepository.findAll().get(1);

        this.mvc
                .perform(post("/cells/edit/" + firstCell.getId())
                        .param("name", "MUSCULAR CELL")
                        .param("cellType", "EUKARYOTE")
                        .param("thereDNA", "on"));

        this.mvc
                .perform(post("/cells/edit/" + secondCell.getId())
                        .param("name", "Animal cell")
                        .param("cellType", "EUKARYOTE")
                        .param("thereDNA", "off")
                );

        Cell firstEditedCell = this.cellRepository.findById(firstCell.getId()).orElse(null);
        Cell secondEditedCell = this.cellRepository.findById(secondCell.getId()).orElse(null);

        assertEquals("MUSCULAR CELL", firstEditedCell.getName());
        assertTrue(firstEditedCell.isThereDNA());

        assertEquals("Animal cell", secondEditedCell.getName());
        assertFalse(secondEditedCell.isThereDNA());
    }

    @Test
    @WithMockUser(roles = "MODERATOR")
    public void testDeleteCell() throws Exception {
        Cell firstCell = this.cellRepository.findAll().get(0);
        Cell secondCell = this.cellRepository.findAll().get(1);

        this.mvc
                .perform(
                        post("/cells/delete/" + firstCell.getId())
                );
        assertEquals(1, this.cellRepository.count());
    }
}
