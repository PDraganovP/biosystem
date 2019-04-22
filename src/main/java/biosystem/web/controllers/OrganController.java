package biosystem.web.controllers;

import biosystem.domain.models.binding.OrganBindingModel;
import biosystem.domain.models.service.OrganServiceModel;
import biosystem.domain.models.service.TissueServiceModel;
import biosystem.domain.models.view.OrganViewModel;
import biosystem.domain.models.view.TissueViewModel;
import biosystem.services.OrganService;
import biosystem.services.TissueService;
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
@RequestMapping("/organs")
public class OrganController extends BaseController {
    private final OrganService organService;
    private final TissueService tissueService;
    private final ModelMapper modelMapper;

    @Autowired
    public OrganController(OrganService organService, TissueService tissueService, ModelMapper modelMapper) {
        this.organService = organService;
        this.tissueService = tissueService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/show")
    @PreAuthorize("isAuthenticated()")
    @PageFooter
    @PageNavbar
    public ModelAndView show(ModelAndView modelAndView) {

        List<OrganServiceModel> organServiceModelList = this.organService.findAllOrderedByName();
        List<OrganViewModel> organViewModelList = organServiceModelList.stream().map(organServiceModel -> this.modelMapper
                .map(organServiceModel, OrganViewModel.class)).collect(Collectors.toList());
        modelAndView.addObject("organViewModel", organViewModelList);

        return super.view("organs/all-organs", modelAndView);
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PageFooter
    @PageNavbar
    public ModelAndView getAddOrganPage(@ModelAttribute("organBindingModel") OrganBindingModel organBindingModel,
                                        ModelAndView modelAndView) {

        List<TissueViewModel> tissuesOrderedByName = this.findTissuesOrderedByName();
        modelAndView.addObject("tissuesModels", tissuesOrderedByName);

        return super.view("organs/add-organ", modelAndView);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView addOrgan(ModelAndView modelAndView,
                                 @Valid @ModelAttribute(name = "organBindingModel") OrganBindingModel organBindingModel,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<TissueViewModel> tissuesOrderedByName = this.findTissuesOrderedByName();
            modelAndView.addObject("tissuesModels", tissuesOrderedByName);
            return super.view("organs/add-organ", modelAndView);
        }
        OrganServiceModel organServiceModel = this.modelMapper.map(organBindingModel, OrganServiceModel.class);
        OrganServiceModel organServiceModelWithId = this.organService.saveOrgan(organServiceModel);
        organBindingModel.setId(organServiceModelWithId.getId());

        if (organServiceModelWithId == null) {
            return super.view("organs/add-organ", modelAndView);
        }

        return super.redirect("/organs/show");
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PageFooter
    @PageNavbar
    public ModelAndView getEditOrganPage(@PathVariable("id") String id,
                                         @ModelAttribute("organBindingModel") OrganBindingModel organBindingModel,
                                         ModelAndView modelAndView) {

        List<TissueViewModel> tissuesOrderedByName = this.findTissuesOrderedByName();
        modelAndView.addObject("tissuesModels", tissuesOrderedByName);

        OrganServiceModel organServiceModel = this.organService.findById(id);
        organBindingModel = this.modelMapper.map(organServiceModel, OrganBindingModel.class);

        modelAndView.addObject("organBindingModel", organBindingModel);

        return super.view("organs/edit-organ", modelAndView);
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView editOrgan(@PathVariable("id") String id,
                                  @Valid @ModelAttribute("organBindingModel") OrganBindingModel organBindingModel,
                                  BindingResult bindingResult,
                                  ModelAndView modelAndView) {

        if (bindingResult.hasErrors()) {
            List<TissueViewModel> tissuesOrderedByName = this.findTissuesOrderedByName();
            modelAndView.addObject("tissuesModels", tissuesOrderedByName);
            return super.view("organs/edit-organ", modelAndView);
        }

        organBindingModel.setId(id);
        OrganServiceModel organServiceModel = this.modelMapper.map(organBindingModel, OrganServiceModel.class);

        this.organService.editOrgan(organServiceModel);

        return super.redirect("/organs/show");
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView deleteOrgan(@PathVariable("id") String id, ModelAndView modelAndView) {
        boolean isDeleted = this.organService.deleteOrganById(id);

        if (!isDeleted) {

            return super.view("organs/all-organs", modelAndView);
        }

        return super.redirect("/organs/show");
    }

    private List<TissueViewModel> findTissuesOrderedByName() {
        List<TissueServiceModel> tissueServiceModelList = this.tissueService.findAllOrderedByName();
        List<TissueViewModel> tissueViewModelList = tissueServiceModelList.stream().map(tissueServiceModel -> this.modelMapper
                .map(tissueServiceModel, TissueViewModel.class))
                .collect(Collectors.toList());
        return tissueViewModelList;

    }

    @GetMapping("/compareOrgans")
    @PreAuthorize("isAuthenticated()")
    @PageFooter
    @PageNavbar
    public ModelAndView getCompareOrgansPage(ModelAndView modelAndView) {
        List<OrganViewModel> organsOrderedByNameOne = this.findOrgansOrderedByName();
        modelAndView.addObject("organsOne", organsOrderedByNameOne);
        List<OrganViewModel> organsOrderedByNameTwo = this.findOrgansOrderedByName();
        modelAndView.addObject("organsTwo", organsOrderedByNameTwo);

        return super.view("organs/compare-organs", modelAndView);
    }

    private List<OrganViewModel> findOrgansOrderedByName() {
        List<OrganServiceModel> organServiceModelList = this.organService.findAllOrderedByName();
        List<OrganViewModel> organViewModelList = organServiceModelList.stream().map(organServiceModel -> this.modelMapper
                .map(organServiceModel, OrganViewModel.class))
                .collect(Collectors.toList());
        return organViewModelList;

    }
}
