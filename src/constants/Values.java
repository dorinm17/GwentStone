package constants;

public enum Values {
    PLAYER_ONE(1),
    PLAYER_TWO(2),
    HERO_HEALTH(30);

    private final int value;

    Values(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
