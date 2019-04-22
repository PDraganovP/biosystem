package biosystem.services;

import biosystem.domain.models.service.OrganismServiceModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrganismService {

    OrganismServiceModel saveOrganism(OrganismServiceModel organismServiceModel);

    boolean editOrganism(OrganismServiceModel organismServiceModel);

    OrganismServiceModel findById(String id);

    boolean deleteOrganismById(String id);

    List<OrganismServiceModel> findAllOrderedByName();
}
