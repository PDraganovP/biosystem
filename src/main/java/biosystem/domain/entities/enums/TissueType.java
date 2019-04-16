package biosystem.domain.entities.enums;

public enum TissueType {
    ANIMAL_TISSUE("Animal tissue"),PLANT_TISSUE("Plant tissue");

    private String type;

    TissueType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
