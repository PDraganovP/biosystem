package biosystem.web.controllers;

import biosystem.domain.models.binding.OrganismBindingModel;
import biosystem.domain.models.service.OrganSystemServiceModel;
import biosystem.domain.models.service.OrganismServiceModel;
import biosystem.domain.models.view.OrganSystemViewModel;
import biosystem.domain.models.view.OrganismViewModel;
import biosystem.services.OrganSystemService;
import biosystem.services.OrganismService;
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
@RequestMapping("/organisms")
public class OrganismController extends BaseController {
    private final OrganismService organismService;
    private final OrganSystemService organSystemService;
    private final ModelMapper modelMapper;

    @Autowired
    public OrganismController(OrganismService organismService, OrganSystemService organSystemService, ModelMapper modelMapper) {
        this.organismService = organismService;
        this.organSystemService = organSystemService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/show")
    @PreAuthorize("isAuthenticated()")
    //@PageFooter
    //@PageNavbar
    public ModelAndView show(ModelAndView modelAndView) {

        List<OrganismServiceModel> organismServiceModelList = this.organismService.findAllOrderedByName();
        List<OrganismViewModel> organismViewModelList = organismServiceModelList.stream().map(organismServiceModel -> this.modelMapper
                .map(organismServiceModel, OrganismViewModel.class)).collect(Collectors.toList());
        modelAndView.addObject("organismViewModel", organismViewModelList);

        return super.view("organisms/all-organisms", modelAndView);
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    //@PageFooter
    //@PageNavbar
    public ModelAndView getAddOrganismPage(@ModelAttribute("organismBindingModel") OrganismBindingModel organismBindingModel,
                                           ModelAndView modelAndView) {

        List<OrganSystemViewModel> organSystemsOrderedByName = this.findOrganSystemsOrderedByName();
        modelAndView.addObject("organSystemsModels", organSystemsOrderedByName);

        return super.view("organisms/add-organism", modelAndView);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addOrganism(ModelAndView modelAndView,
                                    @Valid @ModelAttribute(name = "organismBindingModel") OrganismBindingModel organismBindingModel,
                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return super.view("organisms/add-organism", modelAndView);
        }
        OrganismServiceModel organismServiceModel = this.modelMapper.map(organismBindingModel, OrganismServiceModel.class);
        OrganismServiceModel organismServiceModelWithId = this.organismService.saveOrganism(organismServiceModel);
        organismBindingModel.setId(organismServiceModelWithId.getId());

        if (organismServiceModelWithId == null) {
            return super.view("organisms/add-organism", modelAndView);
        }

        return super.redirect("/organisms/show");
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    // @PageFooter
    // @PageNavbar
    public ModelAndView getEditOrganismPage(@PathVariable("id") String id,
                                            @ModelAttribute("organismBindingModel") OrganismBindingModel organismBindingModel,
                                            ModelAndView modelAndView) {

        List<OrganSystemViewModel> organSystemsOrderedByName = this.findOrganSystemsOrderedByName();
        modelAndView.addObject("organSystemsModels", organSystemsOrderedByName);

        OrganismServiceModel organismServiceModel = this.organismService.findById(id);
        organismBindingModel = this.modelMapper.map(organismServiceModel, OrganismBindingModel.class);

        modelAndView.addObject("organismBindingModel", organismBindingModel);

        return super.view("organisms/edit-organism", modelAndView);
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView editOrganism(@PathVariable("id") String id,
                                     @Valid @ModelAttribute("organismBindingModel") OrganismBindingModel organismBindingModel,
                                     BindingResult bindingResult,
                                     ModelAndView modelAndView) {

        if (bindingResult.hasErrors()) {
            return super.view("organisms/edit-organism", modelAndView);
        }

        organismBindingModel.setId(id);
        OrganismServiceModel organismServiceModel = this.modelMapper.map(organismBindingModel, OrganismServiceModel.class);

        this.organismService.editOrganism(organismServiceModel);

        return super.redirect("/organisms/show");
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView deleteOrganism(@PathVariable("id") String id, ModelAndView modelAndView) {
        boolean isDeleted = this.organismService.deleteOrganismById(id);

        if (!isDeleted) {

            return super.view("organisms/all-organisms", modelAndView);
        }

        return super.redirect("/organisms/show");
    }

    private List<OrganSystemViewModel> findOrganSystemsOrderedByName() {
        List<OrganSystemServiceModel> organSystemServiceModelList = this.organSystemService.findAllOrderedByName();
        List<OrganSystemViewModel> organSystemViewModelList = organSystemServiceModelList.stream().map(organSystemServiceModel -> this.modelMapper
                .map(organSystemServiceModel, OrganSystemViewModel.class))
                .collect(Collectors.toList());
        return organSystemViewModelList;

    }
}
