package biosystem.repository;

import biosystem.domain.entities.Tissue;
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
public class TissueRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private TissueRepository tissueRepository;

    @Test
    public void testfindAllOrderByName() {
        Tissue tissueOne = new Tissue();
        tissueOne.setName("Muscle tissue");

        Tissue tissueTwo = new Tissue();
        tissueTwo.setName("Neural tissue");

        Tissue tissueThree = new Tissue();
        tissueThree.setName("Epithelial");

        entityManager.persist(tissueOne);
        entityManager.persist(tissueTwo);
        entityManager.persist(tissueThree);

        List<Tissue> tissuesOrderByName = tissueRepository.findAllOrderByName();
        String tissueName = tissuesOrderByName.get(0).getName();
        assertEquals("Epithelial", tissueName);
    }
}
