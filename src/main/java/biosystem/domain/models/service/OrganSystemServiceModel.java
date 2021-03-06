package biosystem.domain.models.service;

import biosystem.domain.entities.Organ;
import biosystem.domain.entities.Organism;

import java.util.Set;

public class OrganSystemServiceModel {
    private String id;
    private Double size;
    private String shape;
    private String studiedBy;
    private String name;
    private String organSystemFunction;
    private String originOfOrganSystem;
    private Set<Organ> organs;
    private Set<Organism> organisms;

    public OrganSystemServiceModel() {
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
