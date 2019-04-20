package biosystem.repository;

import biosystem.domain.entities.OrganSystem;
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
public class OrganSystemRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private OrganSystemRepository organSystemRepository;

    @Test
    public void testfindAllOrderByName() {
        OrganSystem organSystemOne = new OrganSystem();
        organSystemOne.setName("Respiratory system");

        OrganSystem organSystemTwo = new OrganSystem();
        organSystemTwo.setName("Nervous system");

        OrganSystem organSystemThree = new OrganSystem();
        organSystemThree.setName("Endocrine system");

        entityManager.persist(organSystemOne);
        entityManager.persist(organSystemTwo);
        entityManager.persist(organSystemThree);

        List<OrganSystem> organSystemsOrderByName = organSystemRepository.findAllOrderByName();
        String organSystemName = organSystemsOrderByName.get(0).getName();
        assertEquals("Endocrine system", organSystemName);
    }
}
