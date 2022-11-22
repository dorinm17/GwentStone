package constants;

public enum Error {
    ENVIRONMENT_NOT_ALLOWED("Cannot place environment card on table."),
    NOT_ENOUGH_MANA_MINION("Not enough mana to place card on table."),
    NO_PLACING_FULL_ROW("Cannot place card on table since row is full."),
    NOT_ENEMY("Attacked card does not belong to the enemy."),
    ATTACKER_ALREADY_ATTACKED("Attacker card has already attacked this turn."),
    FROZEN("Attacker card is frozen."),
    NOT_TANK("Attacked card is not of type 'Tank'."),
    NOT_CURRENT_PLAYER("Attacked card does not belong to the current player."),
    HERO_ALREADY_ATTACKED("Hero has already attacked this turn."),
    NOT_ENEMY_ROW_HERO("Selected row does not belong to the enemy."),
    NOT_CURRENT_PLAYER_ROW("Selected row does not belong to the current player."),
    NOT_ENVIRONMENT("Chosen card is not of type environment."),
    NOT_ENOUGH_MANA_ENVIRONMENT("Not enough mana to use environment card."),
    NOT_ENOUGH_MANA_HERO("Not enough mana to use hero's ability."),
    NOT_ENEMY_ROW_ENVIRONMENT("Chosen row does not belong to the enemy."),
    NO_STEALING_FULL_ROW("Cannot steal enemy card since the player's row is full."),
    NO_CARD("No card available at that position.");

    private final String message;

    Error(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
