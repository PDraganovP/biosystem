package biosystem.repository;

import biosystem.domain.entities.OrganSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganSystemRepository extends JpaRepository<OrganSystem,String> {

    @Query("SELECT os FROM OrganSystem AS os ORDER BY os.name")
    List<OrganSystem> findAllOrderByName();
}
