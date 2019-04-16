package biosystem.domain.entities.enums;

public enum CellType {
    PROKARYOTE("Prokaryote"), EUKARYOTE("Eukaryote");

    private String type;

    CellType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
