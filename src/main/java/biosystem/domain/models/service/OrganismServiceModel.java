package biosystem.domain.models.service;

import biosystem.domain.entities.OrganSystem;
import biosystem.domain.entities.enums.OrganismType;

import java.util.Set;

public class OrganismServiceModel {
    private String id;
    private String speciesName;
    private String organismHabitat;
    private String basicFood;
    private OrganismType organismType;
    private Set<OrganSystem> organSystems;

    public OrganismServiceModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSpeciesName() {
        return speciesName;
    }

    public void setSpeciesName(String speciesName) {
        this.speciesName = speciesName;
    }

    public String getOrganismHabitat() {
        return organismHabitat;
    }

    public void setOrganismHabitat(String organismHabitat) {
        this.organismHabitat = organismHabitat;
    }

    public String getBasicFood() {
        return basicFood;
    }

    public void setBasicFood(String basicFood) {
        this.basicFood = basicFood;
    }

    public OrganismType getOrganismType() {
        return organismType;
    }

    public void setOrganismType(OrganismType organismType) {
        this.organismType = organismType;
    }

    public Set<OrganSystem> getOrganSystems() {
        return organSystems;
    }

    public void setOrganSystems(Set<OrganSystem> organSystems) {
        this.organSystems = organSystems;
    }
}
