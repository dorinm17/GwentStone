package constants;

public enum MinionBackRow {
    SENTINEL("Sentinel"),
    BERSERKER("Berserker"),
    THE_CURSED_ONE("The Cursed One"),
    DISCIPLE("Disciple");

    private final String name;

    MinionBackRow(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
