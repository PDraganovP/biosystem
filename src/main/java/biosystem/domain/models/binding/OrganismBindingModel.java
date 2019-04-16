package biosystem.domain.models.binding;

import biosystem.domain.entities.enums.OrganismType;

public class OrganismBindingModel {
    private String id;
    private String speciesName;
    private String organismHabitat;
    private String basicFood;
    private OrganismType organismType;

    public OrganismBindingModel() {
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
}
