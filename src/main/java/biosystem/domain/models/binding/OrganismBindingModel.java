package biosystem.domain.models.binding;

import biosystem.domain.entities.OrganSystem;
import biosystem.domain.entities.enums.OrganismType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

import static biosystem.AnnotationConstants.*;

public class OrganismBindingModel {
    private String id;
    private String speciesName;
    private String organismHabitat;
    private String basicFood;
    private OrganismType organismType;
    private Set<OrganSystem> organSystems;

    public OrganismBindingModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Size(min = 3, max = 50, message = FIELD_NAME_BOUNDARIES)
    public String getSpeciesName() {
        return speciesName;
    }

    public void setSpeciesName(String speciesName) {
        this.speciesName = speciesName;
    }

    @Size(max = 100, message = FIELD_MAX_SYMBOLS_LENGTH)
    public String getOrganismHabitat() {
        return organismHabitat;
    }

    public void setOrganismHabitat(String organismHabitat) {
        this.organismHabitat = organismHabitat;
    }

    @Size(max = 100, message = FIELD_MAX_SYMBOLS_LENGTH)
    public String getBasicFood() {
        return basicFood;
    }

    public void setBasicFood(String basicFood) {
        this.basicFood = basicFood;
    }

    @NotNull(message = FIELD_CAN_NOT_BE_NULL)
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
