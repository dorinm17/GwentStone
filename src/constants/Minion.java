package constants;

public enum Minion {
    SENTINEL("Sentinel"),
    BERSERKER("Berserker"),
    GOLIATH("Goliath"),
    WARDEN("Warden"),
    THE_RIPPER("The Ripper"),
    MIRAJ("Miraj"),
    THE_CURSED_ONE("The Cursed One"),
    DISCIPLE("Disciple");

    private final String name;

    Minion(final String name) {
        this.name = name;
    }
}
