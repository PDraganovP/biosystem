package biosystem.domain.entities.enums;

public enum OrganType {
    INTERNAL("Internal"),EXTERNAL("External");

    private String type;

    OrganType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
