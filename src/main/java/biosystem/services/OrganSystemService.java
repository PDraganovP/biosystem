package biosystem.services;

import biosystem.domain.models.service.OrganSystemServiceModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrganSystemService {

    OrganSystemServiceModel saveOrganSystem(OrganSystemServiceModel organSystemServiceModel);

    void editOrganSystem(OrganSystemServiceModel organSystemServiceModel);

   OrganSystemServiceModel findById(String id);

    boolean deleteOrganSystemById(String id);

    List<OrganSystemServiceModel> findAllOrderedByName();
}
