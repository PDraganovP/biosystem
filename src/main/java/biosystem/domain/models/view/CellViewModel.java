package biosystem.domain.models.view;

import biosystem.domain.entities.Tissue;
import biosystem.domain.entities.enums.CellType;

import java.util.Set;

public class CellViewModel {
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

    public CellViewModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public String getStudiedBy() {
        return studiedBy;
    }

    public void setStudiedBy(String studiedBy) {
        this.studiedBy = studiedBy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public void setTissues(Set<Tissue> tissues) {
        this.tissues = tissues;
    }
}
