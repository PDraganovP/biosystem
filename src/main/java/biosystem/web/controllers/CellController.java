package biosystem.web.controllers;

import biosystem.domain.models.binding.CellBindingModel;
import biosystem.domain.models.service.CellServiceModel;
import biosystem.domain.models.view.CellViewModel;
import biosystem.services.CellService;
import biosystem.web.annotations.PageFooter;
import biosystem.web.annotations.PageNavbar;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/cells")
public class CellController extends BaseController {
    private final CellService cellService;
    private final ModelMapper modelMapper;

    @Autowired
    public CellController(CellService cellService, ModelMapper modelMapper) {
        this.cellService = cellService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/show")
    @PreAuthorize("isAuthenticated()")
    @PageFooter
    @PageNavbar
    public ModelAndView show(ModelAndView modelAndView) {

        List<CellServiceModel> cellServiceModelList = this.cellService.findAllOrderedByName();
        List<CellViewModel> cellViewModelList = cellServiceModelList.stream().map(cellServiceModel -> this.modelMapper
                .map(cellServiceModel, CellViewModel.class)).collect(Collectors.toList());
        modelAndView.addObject("cellViewModel", cellViewModelList);

        return super.view("cells/all-cells", modelAndView);
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PageFooter
    @PageNavbar
    public ModelAndView getAddCellPage(@ModelAttribute("cellBindingModel") CellBindingModel cellBindingModel,
                                       ModelAndView modelAndView) {
        return super.view("cells/add-cell", modelAndView);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addCell(ModelAndView modelAndView,
                                @Valid @ModelAttribute(name = "cellBindingModel") CellBindingModel cellBindingModel,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return super.view("cells/add-cell", modelAndView);
        }
        CellServiceModel cellServiceModel = this.modelMapper.map(cellBindingModel, CellServiceModel.class);
        CellServiceModel cellServiceModelWithId = this.cellService.saveCell(cellServiceModel);
        cellBindingModel.setId(cellServiceModelWithId.getId());

        if (cellServiceModelWithId == null) {
            return super.view("cells/add-cell", modelAndView);
        }

        return super.redirect("/cells/show");
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PageFooter
    @PageNavbar
    public ModelAndView getEditCellPage(@PathVariable("id") String id,
                                        @ModelAttribute("cellBindingModel") CellBindingModel cellBindingModel,
                                        ModelAndView modelAndView) {

        CellServiceModel cellServiceModel = this.cellService.findById(id);
        cellBindingModel = this.modelMapper.map(cellServiceModel, CellBindingModel.class);

        modelAndView.addObject("cellBindingModel", cellBindingModel);

        return super.view("cells/edit-cell", modelAndView);
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView editCell(@PathVariable("id") String id,
                                 @Valid @ModelAttribute("cellBindingModel") CellBindingModel cellBindingModel,
                                 BindingResult bindingResult,
                                 ModelAndView modelAndView) {

        if (bindingResult.hasErrors()) {
            return super.view("cells/edit-cell", modelAndView);
        }

        cellBindingModel.setId(id);
        CellServiceModel cellServiceModel = this.modelMapper.map(cellBindingModel, CellServiceModel.class);

        this.cellService.editCell(cellServiceModel);

        return super.redirect("/cells/show");
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView deleteCell(@PathVariable("id") String id, ModelAndView modelAndView) {
        boolean isDeleted = this.cellService.deleteCellById(id);

        if (!isDeleted) {

            return super.view("cells/all-cells", modelAndView);
        }

        return super.redirect("/cells/show");
    }

    @GetMapping("/compareCells")
    @PreAuthorize("isAuthenticated()")
    @PageFooter
    @PageNavbar
    public ModelAndView getCompareCellsPage(ModelAndView modelAndView) {
        List<CellViewModel> cellsOrderedByNameOne = this.findCellsOrderedByName();
        modelAndView.addObject("cellsOne", cellsOrderedByNameOne);
        List<CellViewModel> cellsOrderedByNameTwo = this.findCellsOrderedByName();
        modelAndView.addObject("cellsTwo", cellsOrderedByNameTwo);

        return super.view("cells/compare-cells", modelAndView);
    }

    private List<CellViewModel> findCellsOrderedByName() {
        List<CellServiceModel> cellServiceModelList = this.cellService.findAllOrderedByName();
        List<CellViewModel> cellViewModelList = cellServiceModelList.stream().map(cellServiceModel -> this.modelMapper
                .map(cellServiceModel, CellViewModel.class))
                .collect(Collectors.toList());
        return cellViewModelList;

    }
}
