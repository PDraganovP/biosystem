package biosystem.domain.models.view;

import biosystem.domain.entities.OrganSystem;
import biosystem.domain.entities.Tissue;
import biosystem.domain.entities.enums.OrganType;

import java.util.Set;

public class ShowOrganViewModel {
    private String id;
    private Double size;
    private String shape;
    private String studiedBy;
    private String name;
    private String organFunction;
    private OrganType organType;
   /* private Set<Tissue> tissues;
    private Set<OrganSystem> organSystems;*/

    public ShowOrganViewModel() {
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

  /*  public Set<Tissue> getTissues() {
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
    }*/
}
