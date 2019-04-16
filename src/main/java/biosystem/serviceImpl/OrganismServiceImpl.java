package biosystem.serviceImpl;

import biosystem.domain.entities.Organism;
import biosystem.domain.models.service.OrganismServiceModel;
import biosystem.repository.OrganismRepository;
import biosystem.services.OrganismService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrganismServiceImpl implements OrganismService {
    private final OrganismRepository organismRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public OrganismServiceImpl(OrganismRepository organismRepository, ModelMapper modelMapper) {
        this.organismRepository = organismRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public OrganismServiceModel saveOrganism(OrganismServiceModel organismServiceModel) {
        Organism organism = this.modelMapper.map(organismServiceModel, Organism.class);
        Organism savedOrganism = this.organismRepository.saveAndFlush(organism);
        OrganismServiceModel mappedOrganismServiceModel = this.modelMapper
                .map(savedOrganism, OrganismServiceModel.class);

        return mappedOrganismServiceModel;
    }

    @Override
    public void editOrganism(OrganismServiceModel organismServiceModel) {
        String id = organismServiceModel.getId();
        Organism organism = this.organismRepository.getOne(id);
        organism = this.modelMapper.map(organismServiceModel, Organism.class);

        this.organismRepository.save(organism);
    }

    @Override
    public OrganismServiceModel findById(String id) {
        Organism organism = this.organismRepository.findById(id).orElse(null);
        return organism == null ? null : this.modelMapper.map(organism, OrganismServiceModel.class);
    }

    @Override
    public boolean deleteOrganismById(String id) {
        try {
            this.organismRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<OrganismServiceModel> findAllOrderedByName() {
        List<Organism> organisms = this.organismRepository.findAllOrderByName();
        List<OrganismServiceModel> organismServiceModels = organisms.stream()
                .map(organism -> this.modelMapper
                        .map(organism, OrganismServiceModel.class))
                .collect(Collectors.toList());

        return organismServiceModels;
    }

}
