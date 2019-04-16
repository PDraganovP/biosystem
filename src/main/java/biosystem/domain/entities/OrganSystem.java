package biosystem.domain.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "organ_systems")
public class OrganSystem extends BaseEntity {
    private String name;
    private String organSystemFunction;
 //   private Set<Organ> organs;
    private Set<Organism> organisms;

    public OrganSystem() {
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "organ_system_function")
    public String getOrganSystemFunction() {
        return organSystemFunction;
    }

    public void setOrganSystemFunction(String function) {
        this.organSystemFunction = function;
    }

  //  @ManyToMany
  //  public Set<Organ> getOrgans() {
  //      return organs;
  //  }
//
  //  public void setOrgans(Set<Organ> organs) {
  //      this.organs = organs;
  //  }

    @ManyToMany
    @JoinTable(name = "organ_systems_organisms",
            joinColumns = @JoinColumn(
                    name = "organ_system_id"
                    , referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "organism_id"
                    , referencedColumnName = "id"))
    public Set<Organism> getOrganisms() {
        return organisms;
    }

    public void setOrganisms(Set<Organism> organisms) {
        this.organisms = organisms;
    }
}
