package biosystem.domain.entities.enums;

public enum OrganismType {
    ANIMAL("Animal"),PLANT("Plant"),FUNGI("Fungi"),MONERA("Monera"), PROTISTS("Protists");

    private String type;

    OrganismType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
