package constants;

public enum Hero {
    LORD_ROYCE("Lord Royce"),
    EMPRESS_THORINA("Empress Thorina"),
    KING_MUDFACE("King Mudface"),
    GENERAL_KOCIORAW("General Kocioraw");

    private final String name;

    Hero(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
