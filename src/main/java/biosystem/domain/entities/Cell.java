package biosystem.domain.entities;

import biosystem.domain.entities.enums.CellType;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "cells")
public class Cell extends BasicFeature {
    private String name;
    private CellType cellType;
    private boolean isThereDNA;
    private boolean isThereMitochondria;
    private boolean isThereRibosomes;
    private Set<Tissue> tissues;

    public Cell() {
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Enumerated(value = EnumType.STRING)
    @Column(name = "cell_type")
    public CellType getCellType() {
        return cellType;
    }

    public void setCellType(CellType cellType) {
        this.cellType = cellType;
    }

    @Column(name = "is_there_dna")
    public boolean isThereDNA() {
        return isThereDNA;
    }

    public void setThereDNA(boolean thereDNA) {
        isThereDNA = thereDNA;
    }

    @Column(name = "is_there_mitochondria")
    public boolean isThereMitochondria() {
        return isThereMitochondria;
    }

    public void setThereMitochondria(boolean thereMitochondria) {
        isThereMitochondria = thereMitochondria;
    }

    @Column(name = "is_there_ribosomes")
    public boolean isThereRibosomes() {
        return isThereRibosomes;
    }

    public void setThereRibosomes(boolean thereRibosomes) {
        isThereRibosomes = thereRibosomes;
    }

   @ManyToMany(mappedBy ="cells")
   public Set<Tissue> getTissues() {
        return tissues;
    }

    public void setTissues(Set<Tissue> tissues) {
        this.tissues = tissues;
    }
}
