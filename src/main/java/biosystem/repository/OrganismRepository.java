package biosystem.repository;

import biosystem.domain.entities.Organism;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganismRepository extends JpaRepository<Organism,String> {

    @Query("SELECT o FROM Organism AS o ORDER BY o.speciesName")
    List<Organism> findAllOrderByName();
}
