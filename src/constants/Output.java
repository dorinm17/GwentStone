package constants;

public enum Output {
    COMMAND("command"),
    PLAYER_IDX("playerIdx"),
    HAND_IDX("handIdx"),
    CARD_ATTACKER("cardAttacker"),
    CARD_ATTACKED("cardAttacked"),
    AFFECTED_ROW("affectedRow"),
    X("x"),
    Y("y"),
    OUTPUT("output"),
    ERROR("error");

    private final String output;

    Output(final String output) {
        this.output = output;
    }

    public String getOutput() {
        return output;
    }
}
