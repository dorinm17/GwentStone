package constants;

public enum MinionFrontRow {
    GOLIATH("Goliath"),
    WARDEN("Warden"),
    THE_RIPPER("The Ripper"),
    MIRAJ("Miraj");

    private final String name;

    MinionFrontRow(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
