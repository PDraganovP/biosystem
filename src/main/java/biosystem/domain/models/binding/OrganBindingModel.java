package biosystem.domain.models.binding;

import biosystem.domain.entities.OrganSystem;
import biosystem.domain.entities.enums.OrganType;

import java.util.Set;

public class OrganBindingModel {
    private String id;
    private Double size;
    private String shape;
    private String studiedBy;
    private String name;
    private String organFunction;
    private OrganType organType;
    private Set<OrganSystem> organSystems;

    public OrganBindingModel() {
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

    public String getOrganFunction() {
        return organFunction;
    }

    public void setOrganFunction(String organFunction) {
        this.organFunction = organFunction;
    }

    public OrganType getOrganType() {
        return organType;
    }

    public void setOrganType(OrganType organType) {
        this.organType = organType;
    }

    public Set<OrganSystem> getOrganSystems() {
        return organSystems;
    }

    public void setOrganSystems(Set<OrganSystem> organSystems) {
        this.organSystems = organSystems;
    }
}
