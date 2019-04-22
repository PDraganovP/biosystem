package biosystem.services;

import biosystem.domain.models.service.TissueServiceModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TissueService {

    TissueServiceModel saveTissue(TissueServiceModel tissueServiceModel);

    boolean editTissue(TissueServiceModel tissueServiceModel);

    TissueServiceModel findById(String id);

    boolean deleteTissueById(String id);

    List<TissueServiceModel> findAllOrderedByName();
}
