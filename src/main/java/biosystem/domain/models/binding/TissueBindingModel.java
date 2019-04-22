package biosystem.domain.models.binding;

import biosystem.domain.entities.Cell;
import biosystem.domain.entities.Organ;
import biosystem.domain.entities.enums.TissueType;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

import static biosystem.AnnotationConstants.*;

public class TissueBindingModel {
    private String id;
    private Double size;
    private String shape;
    private String studiedBy;
    private String name;
    private TissueType tissueType;
    private String description;
    private Set<Cell> cells;
    private Set<Organ> organs;

    public TissueBindingModel() {
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

    @NotNull(message = FIELD_CAN_NOT_BE_NULL)
    public TissueType getTissueType() {
        return tissueType;
    }

    public void setTissueType(TissueType tissueType) {
        this.tissueType = tissueType;
    }

    @Size(max = 100, message = FIELD_MAX_SYMBOLS_LENGTH)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Cell> getCells() {
        return cells;
    }

    public void setCells(Set<Cell> cells) {
        this.cells = cells;
    }

    public Set<Organ> getOrgans() {
        return organs;
    }

    public void setOrgans(Set<Organ> organs) {
        this.organs = organs;
    }

}
