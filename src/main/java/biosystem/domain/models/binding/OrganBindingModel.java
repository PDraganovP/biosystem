package biosystem.domain.models.binding;

import biosystem.domain.entities.OrganSystem;
import biosystem.domain.entities.Tissue;
import biosystem.domain.entities.enums.OrganType;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

import static biosystem.AnnotationConstants.*;

public class OrganBindingModel {
    private String id;
    private Double size;
    private String shape;
    private String studiedBy;
    private String name;
    private String organFunction;
    private OrganType organType;
    private Set<Tissue> tissues;
    private Set<OrganSystem> organSystems;

    public OrganBindingModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Min(value = 0, message = FIELD_CAN_NOT_BE_NEGATIVE)
    @Max(value = 1000000,message = FIELD_CAN_NOT_BE_GREATER_THAN_MILLION)
    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }
    @Size(max=100,message = FIELD_MAX_SYMBOLS_LENGTH)
    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }
    @Size(max=100,message = FIELD_MAX_SYMBOLS_LENGTH)
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
    @Size(max=100,message = FIELD_MAX_SYMBOLS_LENGTH)
    public String getOrganFunction() {
        return organFunction;
    }

    public void setOrganFunction(String organFunction) {
        this.organFunction = organFunction;
    }

    @NotNull(message = FIELD_CAN_NOT_BE_NULL)
    public OrganType getOrganType() {
        return organType;
    }

    public void setOrganType(OrganType organType) {
        this.organType = organType;
    }

    public Set<Tissue> getTissues() {
        return tissues;
    }

    public void setTissues(Set<Tissue> tissues) {
        this.tissues = tissues;
    }

    public Set<OrganSystem> getOrganSystems() {
        return organSystems;
    }

    public void setOrganSystems(Set<OrganSystem> organSystems) {
        this.organSystems = organSystems;

    }

}
