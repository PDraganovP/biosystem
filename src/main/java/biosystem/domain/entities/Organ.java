package biosystem.domain.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "organs")
public class Organ extends BaseEntity {
    private String name;
    private String organFunction;
//   private Set<Tissue> tissues;
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

  //  @ManyToMany
  //  public Set<Tissue> getTissues() {
  //      return tissues;
  //  }
//
  //  public void setTissues(Set<Tissue> tissues) {
  //      this.tissues = tissues;
  //  }

    @ManyToMany
    @JoinTable(name = "organs_organ_systems",
            joinColumns = @JoinColumn(
                    name = "organ_id",
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "organ_system_id",
                    referencedColumnName = "id"))
    public Set<OrganSystem> getOrganSystems() {
        return organSystems;
    }

    public void setOrganSystems(Set<OrganSystem> organSystems) {
        this.organSystems = organSystems;
    }
}
