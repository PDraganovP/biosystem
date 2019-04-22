package biosystem.domain.entities;

import biosystem.domain.entities.enums.OrganismType;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "organisms")
public class Organism extends BaseEntity {
    private String speciesName;
    private String organismHabitat;
    private String basicFood;
    private OrganismType organismType;
    private Set<OrganSystem> organSystems;

    public Organism() {
    }

    @Column(name = "species_name", nullable = false)
    public String getSpeciesName() {
        return speciesName;
    }

    public void setSpeciesName(String speciesName) {
        this.speciesName = speciesName;
    }

    @Column(name = "organism_habitat")
    public String getOrganismHabitat() {
        return organismHabitat;
    }

    public void setOrganismHabitat(String organismHabitat) {
        this.organismHabitat = organismHabitat;
    }

    @Column(name = "basic_food")
    public String getBasicFood() {
        return basicFood;
    }

    public void setBasicFood(String basicFood) {
        this.basicFood = basicFood;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "organism_type")
    public OrganismType getOrganismType() {
        return organismType;
    }

    public void setOrganismType(OrganismType organismType) {
        this.organismType = organismType;
    }

    @ManyToMany
    @JoinTable(name = "organisms_organ_systems",
            joinColumns = @JoinColumn(
                    name = "organism_id"
                    , referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "organ_system_id"
                    , referencedColumnName = "id")
    )
    public Set<OrganSystem> getOrganSystems() {
        return organSystems;
    }

    public void setOrganSystems(Set<OrganSystem> organSystems) {
        this.organSystems = organSystems;
    }
}
