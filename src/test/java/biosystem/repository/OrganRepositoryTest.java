package biosystem.repository;

import biosystem.domain.entities.Organ;
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
public class OrganRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private OrganRepository organRepository;

    @Test
    public void testfindAllOrderByName() {
        Organ organOne = new Organ();
        organOne.setName("Hearth");

        Organ organTwo = new Organ();
        organTwo.setName("Brain");

        Organ organThree = new Organ();
        organThree.setName("Eyes");

        entityManager.persist(organOne);
        entityManager.persist(organTwo);
        entityManager.persist(organThree);

        List<Organ> organsOrderByName = organRepository.findAllOrderByName();
        String organName = organsOrderByName.get(0).getName();
        assertEquals("Brain", organName);
    }
}
