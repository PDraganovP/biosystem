package biosystem.web.controllers;

import biosystem.domain.models.binding.TissueBindingModel;
import biosystem.domain.models.service.CellServiceModel;
import biosystem.domain.models.service.TissueServiceModel;
import biosystem.domain.models.view.CellViewModel;
import biosystem.domain.models.view.TissueViewModel;
import biosystem.services.CellService;
import biosystem.services.TissueService;
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
@RequestMapping("/tissues")
public class TissueController extends BaseController {
    private final TissueService tissueService;
    private final CellService cellService;
    private final ModelMapper modelMapper;

    @Autowired
    public TissueController(TissueService tissueService, CellService cellService, ModelMapper modelMapper) {
        this.tissueService = tissueService;
        this.cellService = cellService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/show")
    @PreAuthorize("isAuthenticated()")
    // @PageFooter
    // @PageNavbar
    public ModelAndView show(ModelAndView modelAndView) {

        List<TissueServiceModel> tissueServiceModelList = this.tissueService.findAllOrderedByName();
        List<TissueViewModel> tissueViewModelList = tissueServiceModelList.stream().map(tissueServiceModel -> this.modelMapper
                .map(tissueServiceModel, TissueViewModel.class)).collect(Collectors.toList());
        modelAndView.addObject("tissueViewModel", tissueViewModelList);

        return super.view("tissues/all-tissues", modelAndView);
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    //@PageFooter
    //@PageNavbar
    public ModelAndView getAddTissuePage(@ModelAttribute("tissueBindingModel") TissueBindingModel tissueBindingModel,
                                         ModelAndView modelAndView) {

        List<CellViewModel> cellsOrderedByName = this.findCellsOrderedByName();
        modelAndView.addObject("cellsModels", cellsOrderedByName);

        return super.view("tissues/add-tissue", modelAndView);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addTissue(ModelAndView modelAndView,
                                  @Valid @ModelAttribute(name = "tissueBindingModel") TissueBindingModel tissueBindingModel,
                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return super.view("tissues/add-tissue", modelAndView);
        }
        TissueServiceModel tissueServiceModel = this.modelMapper.map(tissueBindingModel, TissueServiceModel.class);
        TissueServiceModel tissueServiceModelWithId = this.tissueService.saveTissue(tissueServiceModel);
        tissueBindingModel.setId(tissueServiceModelWithId.getId());

        if (tissueServiceModelWithId == null) {
            return super.view("tissues/add-tissue", modelAndView);
        }

        return super.redirect("/tissues/show");
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    // @PageFooter
    // @PageNavbar
    public ModelAndView getEditTissuePage(@PathVariable("id") String id,
                                          @ModelAttribute("tissueBindingModel") TissueBindingModel tissueBindingModel,
                                          ModelAndView modelAndView) {

        List<CellViewModel> cellsOrderedByName = this.findCellsOrderedByName();
        modelAndView.addObject("cellsModels", cellsOrderedByName);

        TissueServiceModel tissueServiceModel = this.tissueService.findById(id);
        tissueBindingModel = this.modelMapper.map(tissueServiceModel, TissueBindingModel.class);

        modelAndView.addObject("tissueBindingModel", tissueBindingModel);

        return super.view("tissues/edit-tissue", modelAndView);
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView editTissue(@PathVariable("id") String id,
                                   @Valid @ModelAttribute("tissueBindingModel") TissueBindingModel tissueBindingModel,
                                   BindingResult bindingResult,
                                   ModelAndView modelAndView) {

        if (bindingResult.hasErrors()) {
            return super.view("tissues/edit-tissue", modelAndView);
        }

        tissueBindingModel.setId(id);
        TissueServiceModel tissueServiceModel = this.modelMapper.map(tissueBindingModel, TissueServiceModel.class);

        this.tissueService.editTissue(tissueServiceModel);

        return super.redirect("/tissues/show");
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView deleteTissue(@PathVariable("id") String id, ModelAndView modelAndView) {
        boolean isDeleted = this.tissueService.deleteTissueById(id);

        if (!isDeleted) {

            return super.view("tissues/all-tissues", modelAndView);
        }

        return super.redirect("/tissues/show");
    }

    private List<CellViewModel> findCellsOrderedByName() {
        List<CellServiceModel> cellServiceModelList = this.cellService.findAllOrderedByName();
        List<CellViewModel> cellViewModelList = cellServiceModelList.stream().map(cellServiceModel -> this.modelMapper
                .map(cellServiceModel, CellViewModel.class))
                .collect(Collectors.toList());
        return cellViewModelList;

    }


}
