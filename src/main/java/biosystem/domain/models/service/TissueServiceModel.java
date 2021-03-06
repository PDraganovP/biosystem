package biosystem.domain.models.service;

import biosystem.domain.entities.Cell;
import biosystem.domain.entities.Organ;
import biosystem.domain.entities.enums.TissueType;

import java.util.Set;

public class TissueServiceModel {
    private String id;
    private Double size;
    private String shape;
    private String studiedBy;
    private String name;
    private TissueType tissueType;
    private String description;
    private Set<Cell> cells;
    private Set<Organ> organs;

    public TissueServiceModel() {
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

    public TissueType getTissueType() {
        return tissueType;
    }

    public void setTissueType(TissueType tissueType) {
        this.tissueType = tissueType;
    }

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
