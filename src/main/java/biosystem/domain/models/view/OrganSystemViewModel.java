package biosystem.domain.models.view;

import biosystem.domain.entities.Organism;

import java.util.Set;

public class OrganSystemViewModel {
    private String id;
    private Double size;
    private String shape;
    private String studiedBy;
    private String name;
    private String organSystemFunction;
    private String originOfOrganSystem;
    private Set<Organism> organisms;

    public OrganSystemViewModel() {
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

    public Set<Organism> getOrganisms() {
        return organisms;
    }

    public void setOrganisms(Set<Organism> organisms) {
        this.organisms = organisms;
    }
}
