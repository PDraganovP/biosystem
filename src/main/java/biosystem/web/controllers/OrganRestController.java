package biosystem.web.controllers;

import biosystem.domain.entities.Organ;
import biosystem.domain.models.service.OrganSystemServiceModel;
import biosystem.domain.models.view.OrganSystemViewModel;
import biosystem.domain.models.view.ShowOrganViewModel;
import biosystem.services.OrganSystemService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class OrganRestController {
    private final OrganSystemService organSystemService;
    private final ModelMapper modelMapper;

    @Autowired
    public OrganRestController(OrganSystemService organSystemService, ModelMapper modelMapper) {
        this.organSystemService = organSystemService;
        this.modelMapper = modelMapper;
    }

    @PostMapping(value = "/show-organs")
    public ResponseEntity<?> showOrgansTable(@RequestBody String id) {
        String data = id;
        OrganSystemServiceModel organSystemServiceModel = this.organSystemService.findById(id);
        OrganSystemViewModel mappedOrganSystemViewModel = this.modelMapper
                .map(organSystemServiceModel, OrganSystemViewModel.class);
        Set<Organ> organs = mappedOrganSystemViewModel.getOrgans();
        List<ShowOrganViewModel> showOrganViewModelList = organs.stream().map(organ -> this.modelMapper
                .map(organ, ShowOrganViewModel.class)).collect(Collectors.toList());
        if (data == null || data.equalsIgnoreCase("")) {

            return new ResponseEntity<>(new Exception("Something went wrong!"), HttpStatus.OK);
        }

        return new ResponseEntity<>(showOrganViewModelList, HttpStatus.OK);
    }
}
