package biosystem.serviceImpl;

import biosystem.domain.entities.Cell;
import biosystem.domain.models.service.CellServiceModel;
import biosystem.repository.CellRepository;
import biosystem.services.CellService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CellServiceImpl implements CellService {
    private final CellRepository cellRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CellServiceImpl(CellRepository cellRepository, ModelMapper modelMapper) {
        this.cellRepository = cellRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CellServiceModel saveCell(CellServiceModel cellServiceModel) {
        Cell cell = this.modelMapper.map(cellServiceModel, Cell.class);
        Cell savedCell = this.cellRepository.saveAndFlush(cell);
        CellServiceModel mappedCellServiceModel = this.modelMapper
                .map(savedCell, CellServiceModel.class);

        return mappedCellServiceModel;
    }

    @Override
    public void editCell(CellServiceModel cellServiceModel) {
        String id = cellServiceModel.getId();
        Cell cell = this.cellRepository.getOne(id);
        cell = this.modelMapper.map(cellServiceModel, Cell.class);
        this.cellRepository.save(cell);
    }

    @Override
    public CellServiceModel findById(String id) {
        Cell cell = this.cellRepository.findById(id).orElse(null);
        return cell == null ? null : this.modelMapper.map(cell, CellServiceModel.class);
    }

    @Override
    public boolean deleteCellById(String id) {
        try {
            this.cellRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<CellServiceModel> findAllOrderedByName() {
        List<Cell> cells = this.cellRepository.findAllOrderByName();
        List<CellServiceModel> cellServiceModels = cells.stream()
                .map(cell -> this.modelMapper
                        .map(cell, CellServiceModel.class))
                .collect(Collectors.toList());

        return cellServiceModels;
    }

}
