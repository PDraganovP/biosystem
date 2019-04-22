package biosystem.domain.models.binding;

import biosystem.domain.entities.Organ;
import biosystem.domain.entities.Organism;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.Set;

import static biosystem.AnnotationConstants.*;

public class OrganSystemBindingModel {
    private String id;
    private Double size;
    private String shape;
    private String studiedBy;
    private String name;
    private String organSystemFunction;
    private String originOfOrganSystem;
    private Set<Organ> organs;
    private Set<Organism> organisms;


    public OrganSystemBindingModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Min(value = 0, message = FIELD_CAN_NOT_BE_NEGATIVE)
    @Max(value = 1000000, message = FIELD_CAN_NOT_BE_GREATER_THAN_MILLION)
    public Double getSize() {
        return size;
    }


    public void setSize(Double size) {
        this.size = size;
    }

    @Size(max = 100, message = FIELD_MAX_SYMBOLS_LENGTH)
    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    @Size(max = 100, message = FIELD_MAX_SYMBOLS_LENGTH)
    public String getStudiedBy() {
        return studiedBy;
    }

    public void setStudiedBy(String studiedBy) {
        this.studiedBy = studiedBy;
    }

    @Size(min = 3, max = 50, message = FIELD_NAME_BOUNDARIES)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Size(max = 100, message = FIELD_MAX_SYMBOLS_LENGTH)
    public String getOrganSystemFunction() {
        return organSystemFunction;
    }

    public void setOrganSystemFunction(String organSystemFunction) {
        this.organSystemFunction = organSystemFunction;
    }

    public String getOriginOfOrganSystem() {
        return originOfOrganSystem;
    }

    public void setOriginOfOrganSystem(String originOfOrganSystem) {
        this.originOfOrganSystem = originOfOrganSystem;
    }

    public Set<Organ> getOrgans() {
        return organs;
    }

    public void setOrgans(Set<Organ> organs) {
        this.organs = organs;
    }

    public Set<Organism> getOrganisms() {
        return organisms;
    }

    public void setOrganisms(Set<Organism> organisms) {
        this.organisms = organisms;
    }
}
