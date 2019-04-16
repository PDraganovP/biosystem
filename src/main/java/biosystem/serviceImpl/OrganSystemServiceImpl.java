package biosystem.serviceImpl;

import biosystem.domain.entities.OrganSystem;
import biosystem.domain.models.service.OrganSystemServiceModel;
import biosystem.repository.OrganSystemRepository;
import biosystem.services.OrganSystemService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrganSystemServiceImpl implements OrganSystemService {
    private final OrganSystemRepository organSystemRepository;
    private final ModelMapper modelMapper;

    public OrganSystemServiceImpl(OrganSystemRepository organSystemRepository, ModelMapper modelMapper) {
        this.organSystemRepository = organSystemRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public OrganSystemServiceModel saveOrganSystem(OrganSystemServiceModel organSystemServiceModel) {
        OrganSystem organSystem = this.modelMapper.map(organSystemServiceModel, OrganSystem.class);
        OrganSystem savedOrganSystem = this.organSystemRepository.saveAndFlush(organSystem);
        OrganSystemServiceModel mappedOrganSystemServiceModel = this.modelMapper
                .map(savedOrganSystem, OrganSystemServiceModel.class);

        return mappedOrganSystemServiceModel;
    }

    @Override
    public void editOrganSystem(OrganSystemServiceModel organSystemServiceModel) {
        String id = organSystemServiceModel.getId();
        OrganSystem organSystem = this.organSystemRepository.getOne(id);
        organSystem = this.modelMapper.map(organSystemServiceModel, OrganSystem.class);
        this.organSystemRepository.save(organSystem);
    }

    @Override
    public OrganSystemServiceModel findById(String id) {
        OrganSystem organSystem = this.organSystemRepository.findById(id).orElse(null);
        return organSystem == null ? null : this.modelMapper.map(organSystem, OrganSystemServiceModel.class);
    }

    @Override
    public boolean deleteOrganSystemById(String id) {
        try {
            this.organSystemRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<OrganSystemServiceModel> findAllOrderedByName() {
        List<OrganSystem> organSystems = this.organSystemRepository.findAllOrderByName();
        List<OrganSystemServiceModel> organSystemServiceModels = organSystems.stream()
                .map(organSystem -> this.modelMapper
                        .map(organSystem, OrganSystemServiceModel.class))
                .collect(Collectors.toList());

        return organSystemServiceModels;
    }
}
