package biosystem.repository;

import biosystem.domain.entities.Organ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganRepository extends JpaRepository<Organ,String> {

    @Query("SELECT o FROM Organ AS o ORDER BY o.name")
    List<Organ> findAllOrderByName();
}
