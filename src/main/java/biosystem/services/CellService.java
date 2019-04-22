package biosystem.services;

import biosystem.domain.models.service.CellServiceModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CellService {

    CellServiceModel saveCell(CellServiceModel cellServiceModel);

    boolean editCell(CellServiceModel cellServiceModel);

    CellServiceModel findById(String id);

    boolean deleteCellById(String id);

    List<CellServiceModel> findAllOrderedByName();
}
