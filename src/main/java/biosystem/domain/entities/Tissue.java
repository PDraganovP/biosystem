package biosystem.domain.entities;

import biosystem.domain.entities.enums.TissueType;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "tissue")
public class Tissue extends BasicFeature {
    private String name;
    private TissueType tissueType;
    private String description;
    private Set<Cell> cells;
    private Set<Organ> organs;

    public Tissue() {
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "tissue_type")
    public TissueType getTissueType() {
        return tissueType;
    }

    public void setTissueType(TissueType tissueType) {
        this.tissueType = tissueType;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToMany
    @JoinTable(name = "tissues_cells",
            joinColumns = @JoinColumn(
                    name = "tissue_id",
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "cell_id",
                    referencedColumnName = "id"))
    public Set<Cell> getCells() {
        return cells;
    }

    public void setCells(Set<Cell> cells) {
        this.cells = cells;
    }

    @ManyToMany(mappedBy = "tissues")
    public Set<Organ> getOrgans() {
        return organs;
    }

    public void setOrgans(Set<Organ> organs) {
        this.organs = organs;
    }
}
