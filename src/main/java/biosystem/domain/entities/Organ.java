package biosystem.domain.entities;

import biosystem.domain.entities.enums.OrganType;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "organs")
public class Organ extends BasicFeature {
    private String name;
    private String organFunction;
    private OrganType organType;
    private Set<Tissue> tissues;
    private Set<OrganSystem> organSystems;

    public Organ() {
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "organ_function")
    public String getOrganFunction() {
        return organFunction;
    }

    public void setOrganFunction(String function) {
        this.organFunction = function;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "organ_type")
    public OrganType getOrganType() {
        return organType;
    }

    public void setOrganType(OrganType organType) {
        this.organType = organType;
    }

    @ManyToMany
    @JoinTable(name = "organs_tissues",
            joinColumns = @JoinColumn(
                    name = "organ_id",
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "tissue_id",
                    referencedColumnName = "id")
    )
    public Set<Tissue> getTissues() {
        return tissues;
    }

    public void setTissues(Set<Tissue> tissues) {
        this.tissues = tissues;
    }

    @ManyToMany(mappedBy = "organs")
    public Set<OrganSystem> getOrganSystems() {
        return organSystems;
    }

    public void setOrganSystems(Set<OrganSystem> organSystems) {
        this.organSystems = organSystems;
    }
}
