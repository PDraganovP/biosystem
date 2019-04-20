package biosystem.repository;

import biosystem.domain.entities.Organism;
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
public class OrganismRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private OrganismRepository organismRepository;

    @Test
    public void testfindAllOrderByName() {
        Organism organismOne = new Organism();
        organismOne.setSpeciesName("Horse");

        Organism organismTwo = new Organism();
        organismTwo.setSpeciesName("Lama");

        Organism organismThree = new Organism();
        organismThree.setSpeciesName("Deer");

        entityManager.persist(organismOne);
        entityManager.persist(organismTwo);
        entityManager.persist(organismThree);

        List<Organism> organismsOrderByName = organismRepository.findAllOrderByName();
        String organismName = organismsOrderByName.get(0).getSpeciesName();
        assertEquals("Deer", organismName);
    }
}
