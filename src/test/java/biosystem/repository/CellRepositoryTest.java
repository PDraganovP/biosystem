package biosystem.repository;

import biosystem.domain.entities.Cell;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CellRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private CellRepository cellRepository;

    @Test
    public void testfindAllOrderByName() {
        Cell cellOne = new Cell();
        cellOne.setName("Bacterial cell");

        Cell cellTwo = new Cell();
        cellTwo.setName("Brain cell");

        Cell cellThree = new Cell();
        cellThree.setName("Plant cell");

        entityManager.persist(cellOne);
        entityManager.persist(cellTwo);
        entityManager.persist(cellThree);

        List<Cell> cellsOrderByName = cellRepository.findAllOrderByName();
        String cellName = cellsOrderByName.get(0).getName();
        assertEquals("Bacterial cell", cellName);
    }
}
