package constants;

public enum CardOutput {
    MANA("mana"),
    ATTACK_DAMAGE("attackDamage"),
    HEALTH("health"),
    DESCRIPTION("description"),
    COLORS("colors"),
    NAME("name");

    private final String output;

    CardOutput(String output) {
        this.output = output;
    }

    public String getOutput() {
        return output;
    }
}
