package biosystem.repository;

import biosystem.domain.entities.Tissue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TissueRepository extends JpaRepository<Tissue,String> {

    @Query("SELECT t FROM Tissue AS t ORDER BY t.name")
    List<Tissue> findAllOrderByName();
}
