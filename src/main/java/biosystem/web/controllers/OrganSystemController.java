package biosystem.web.controllers;

import biosystem.domain.models.binding.OrganSystemBindingModel;
import biosystem.domain.models.service.OrganServiceModel;
import biosystem.domain.models.service.OrganSystemServiceModel;
import biosystem.domain.models.view.OrganSystemViewModel;
import biosystem.domain.models.view.OrganViewModel;
import biosystem.services.OrganService;
import biosystem.services.OrganSystemService;
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
@RequestMapping("/organSystems")
public class OrganSystemController extends BaseController {
    private final OrganSystemService organSystemService;
    private final OrganService organService;
    private final ModelMapper modelMapper;

    @Autowired
    public OrganSystemController(OrganSystemService organSystemService, OrganService organService, ModelMapper modelMapper) {
        this.organSystemService = organSystemService;
        this.organService = organService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/show")
    @PreAuthorize("isAuthenticated()")
    @PageFooter
    @PageNavbar
    public ModelAndView show(ModelAndView modelAndView) {

        List<OrganSystemServiceModel> organSystemServiceModelList = this.organSystemService.findAllOrderedByName();
        List<OrganSystemViewModel> organSystemViewModelList = organSystemServiceModelList.stream().map(organSystemServiceModel -> this.modelMapper
                .map(organSystemServiceModel, OrganSystemViewModel.class)).collect(Collectors.toList());
        modelAndView.addObject("organSystemViewModel", organSystemViewModelList);

        return super.view("organSystems/all-organSystems", modelAndView);
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PageFooter
    @PageNavbar
    public ModelAndView getAddOrganSystemPage(@ModelAttribute("organSystemBindingModel") OrganSystemBindingModel organSystemBindingModel,
                                              ModelAndView modelAndView) {

        List<OrganViewModel> organsOrderedByName = this.findOrgansOrderedByName();
        modelAndView.addObject("organsModels", organsOrderedByName);

        return super.view("organSystems/add-organSystem", modelAndView);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addOrganSystem(ModelAndView modelAndView,
                                       @Valid @ModelAttribute(name = "organSystemBindingModel") OrganSystemBindingModel organSystemBindingModel,
                                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<OrganViewModel> organsOrderedByName = this.findOrgansOrderedByName();
            modelAndView.addObject("organsModels", organsOrderedByName);
            return super.view("organSystems/add-organSystem", modelAndView);
        }
        OrganSystemServiceModel organSystemServiceModel = this.modelMapper.map(organSystemBindingModel, OrganSystemServiceModel.class);
        OrganSystemServiceModel organSystemServiceModelWithId = this.organSystemService.saveOrganSystem(organSystemServiceModel);
        organSystemBindingModel.setId(organSystemServiceModelWithId.getId());

        if (organSystemServiceModelWithId == null) {
            return super.view("organSystems/add-organSystem", modelAndView);
        }

        return super.redirect("/organSystems/show");
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PageFooter
    @PageNavbar
    public ModelAndView getEditOrganSystemPage(@PathVariable("id") String id,
                                               @ModelAttribute("organSystemBindingModel") OrganSystemBindingModel organSystemBindingModel,
                                               ModelAndView modelAndView) {

        List<OrganViewModel> organsOrderedByName = this.findOrgansOrderedByName();
        modelAndView.addObject("organsModels", organsOrderedByName);

        OrganSystemServiceModel organSystemServiceModel = this.organSystemService.findById(id);
        organSystemBindingModel = this.modelMapper.map(organSystemServiceModel, OrganSystemBindingModel.class);

        modelAndView.addObject("organSystemBindingModel", organSystemBindingModel);

        return super.view("organSystems/edit-organSystem", modelAndView);
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView editOrganSystem(@PathVariable("id") String id,
                                        @Valid @ModelAttribute("organSystemBindingModel") OrganSystemBindingModel organSystemBindingModel,
                                        BindingResult bindingResult,
                                        ModelAndView modelAndView) {

        if (bindingResult.hasErrors()) {
            List<OrganViewModel> organsOrderedByName = this.findOrgansOrderedByName();
            modelAndView.addObject("organsModels", organsOrderedByName);
            return super.view("organSystems/edit-organSystem", modelAndView);
        }

        organSystemBindingModel.setId(id);
        OrganSystemServiceModel organSystemServiceModel = this.modelMapper.map(organSystemBindingModel, OrganSystemServiceModel.class);

        this.organSystemService.editOrganSystem(organSystemServiceModel);

        return super.redirect("/organSystems/show");
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView deleteOrganSystem(@PathVariable("id") String id, ModelAndView modelAndView) {
        boolean isDeleted = this.organSystemService.deleteOrganSystemById(id);

        if (!isDeleted) {

            return super.view("organSystems/all-organSystems", modelAndView);
        }

        return super.redirect("/organSystems/show");
    }

    private List<OrganViewModel> findOrgansOrderedByName() {
        List<OrganServiceModel> organServiceModelList = this.organService.findAllOrderedByName();
        List<OrganViewModel> organViewModelList = organServiceModelList.stream().map(organServiceModel -> this.modelMapper
                .map(organServiceModel, OrganViewModel.class))
                .collect(Collectors.toList());
        return organViewModelList;

    }
}
