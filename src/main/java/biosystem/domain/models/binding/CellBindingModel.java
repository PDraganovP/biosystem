package biosystem.domain.models.binding;

import biosystem.domain.entities.Tissue;
import biosystem.domain.entities.enums.CellType;

import static biosystem.AnnotationConstants.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

public class CellBindingModel {
    private String id;
    private Double size;
    private String shape;
    private String studiedBy;
    private String name;
    private CellType cellType;
    private boolean isThereDNA;
    private boolean isThereMitochondria;
    private boolean isThereRibosomes;
    private Set<Tissue> tissues;

    public CellBindingModel() {
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
    public CellType getCellType() {
        return cellType;
    }

    public void setCellType(CellType cellType) {
        this.cellType = cellType;
    }

    public boolean isThereDNA() {
        return isThereDNA;
    }

    public void setThereDNA(boolean thereDNA) {
        isThereDNA = thereDNA;
    }

    public boolean isThereMitochondria() {
        return isThereMitochondria;
    }

    public void setThereMitochondria(boolean thereMitochondria) {
        isThereMitochondria = thereMitochondria;
    }

    public boolean isThereRibosomes() {
        return isThereRibosomes;
    }

    public void setThereRibosomes(boolean thereRibosomes) {
        isThereRibosomes = thereRibosomes;
    }

    public Set<Tissue> getTissues() {
        return tissues;
    }
}
