package constants;

public enum Command {
    END_PLAYER_TURN("endPlayerTurn"),
    PLACE_CARD("placeCard"),
    CARD_USES_ATTACK("cardUsesAttack"),
    CARD_USES_ABILITY("cardUsesAbility"),
    USE_ATTACK_HERO("useAttackHero"),
    USE_HERO_ABILITY("useHeroAbility"),
    USE_ENVIRONMENT_CARD("useEnvironmentCard"),
    GET_CARDS_IN_HAND("getCardsInHand"),
    GET_PLAYER_DECK("getPlayerDeck"),
    GET_CARDS_ON_TABLE("getCardsOnTable"),
    GET_PLAYER_TURN("getPlayerTurn"),
    GET_CARD_AT_POSITION("getCardAtPosition"),
    GET_PLAYER_MANA("getPlayerMana"),
    GET_ENVIRONMENT_CARDS_IN_HAND("getEnvironmentCardsInHand"),
    GET_FROZEN_CARDS_ON_TABLE("getFrozenCardsOnTable"),
    GET_TOTAL_GAMES_PLAYED("getTotalGamesPlayed"),
    GET_PLAYER_ONE_WINS("getPlayerOneWins"),
    GET_PLAYER_TWO_WINS("getPlayerTwoWins");

    private final String command;

    Command(final String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
