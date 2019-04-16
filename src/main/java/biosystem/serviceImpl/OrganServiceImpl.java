package biosystem.serviceImpl;

import biosystem.domain.entities.Organ;
import biosystem.domain.models.service.OrganServiceModel;
import biosystem.repository.OrganRepository;
import biosystem.services.OrganService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrganServiceImpl implements OrganService {
    private final OrganRepository organRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public OrganServiceImpl(OrganRepository organRepository, ModelMapper modelMapper) {
        this.organRepository = organRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public OrganServiceModel saveOrgan(OrganServiceModel organServiceModel) {
        Organ organ = this.modelMapper.map(organServiceModel, Organ.class);
        Organ savedOrgan = this.organRepository.saveAndFlush(organ);
        OrganServiceModel mappedOrganServiceModel = this.modelMapper
                .map(savedOrgan, OrganServiceModel.class);

        return mappedOrganServiceModel;
    }

    @Override
    public void editOrgan(OrganServiceModel organServiceModel) {
        String id = organServiceModel.getId();
        Organ organ = this.organRepository.getOne(id);
        organ = this.modelMapper.map(organServiceModel, Organ.class);

        this.organRepository.save(organ);
    }

    @Override
    public OrganServiceModel findById(String id) {
        Organ organ = this.organRepository.findById(id).orElse(null);
        return organ == null ? null : this.modelMapper.map(organ, OrganServiceModel.class);
    }

    @Override
    public boolean deleteOrganById(String id) {
        try {
            this.organRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<OrganServiceModel> findAllOrderedByName() {
        List<Organ> organs = this.organRepository.findAllOrderByName();
        List<OrganServiceModel> organServiceModels = organs.stream()
                .map(organ -> this.modelMapper
                        .map(organ, OrganServiceModel.class))
                .collect(Collectors.toList());

        return organServiceModels;
    }
}
