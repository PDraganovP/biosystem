package biosystem.services;

import biosystem.domain.models.service.OrganServiceModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrganService {

      OrganServiceModel saveOrgan(OrganServiceModel organServiceModel);

    void editOrgan(OrganServiceModel organServiceModel);

   OrganServiceModel findById(String id);

    boolean deleteOrganById(String id);

    List<OrganServiceModel> findAllOrderedByName();
}
