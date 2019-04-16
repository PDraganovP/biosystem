package biosystem.repository;

import biosystem.domain.entities.Cell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CellRepository extends JpaRepository<Cell,String> {

    @Query("SELECT c FROM Cell AS c ORDER BY c.name")
    List<Cell> findAllOrderByName();
}
