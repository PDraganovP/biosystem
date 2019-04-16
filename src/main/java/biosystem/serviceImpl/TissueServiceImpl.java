package biosystem.serviceImpl;

import biosystem.domain.entities.Tissue;
import biosystem.domain.models.service.TissueServiceModel;
import biosystem.repository.TissueRepository;
import biosystem.services.TissueService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TissueServiceImpl implements TissueService {
    private final TissueRepository tissueRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public TissueServiceImpl(TissueRepository tissueRepository, ModelMapper modelMapper) {
        this.tissueRepository = tissueRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public TissueServiceModel saveTissue(TissueServiceModel tissueServiceModel) {
        Tissue tissue = this.modelMapper.map(tissueServiceModel, Tissue.class);
        Tissue savedTissue = this.tissueRepository.saveAndFlush(tissue);
        TissueServiceModel mappedTissueServiceModel = this.modelMapper
                .map(savedTissue, TissueServiceModel.class);

        return mappedTissueServiceModel;
    }

    @Override
    public void editTissue(TissueServiceModel tissueServiceModel) {
        String id = tissueServiceModel.getId();
        Tissue tissue = this.tissueRepository.getOne(id);
        tissue = modelMapper.map(tissue, Tissue.class);

        this.tissueRepository.save(tissue);
    }

    @Override
    public TissueServiceModel findById(String id) {
        Tissue tissue = this.tissueRepository.findById(id).orElse(null);
        return tissue == null ? null : this.modelMapper.map(tissue, TissueServiceModel.class);
    }

    @Override
    public boolean deleteTissueById(String id) {
        try {
            this.tissueRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<TissueServiceModel> findAllOrderedByName() {
        List<Tissue> tissues = this.tissueRepository.findAllOrderByName();
        List<TissueServiceModel> tissueServiceModels = tissues.stream()
                .map(tissue -> this.modelMapper
                        .map(tissue, TissueServiceModel.class))
                .collect(Collectors.toList());

        return tissueServiceModels;
    }
}
