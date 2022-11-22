package constants;

public enum Values {
    PLAYER_ONE(1),
    PLAYER_TWO(2),
    HERO_HEALTH(30),
    INITIAL_MANA(1),
    MAX_MANA_ROUND(10),
    ROWS(4),
    COLUMNS(5),
    PLAYER_TWO_FRONT_ROW(1),
    PLAYER_TWO_BACK_ROW(0),
    PLAYER_ONE_FRONT_ROW(2),
    PLAYER_ONE_BACK_ROW(3),
    GODS_PLAN(2),
    WEAK_KNEES(2),
    FIRESTORM(1),
    EARTH_BORN(1),
    BLOOD_THIRST(1);

    private final int value;

    Values(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
