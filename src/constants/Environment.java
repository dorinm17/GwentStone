package constants;

public enum Environment {
    FIRESTORM("Firestorm"),
    WINTERFELL("Winterfell"),
    HEART_HOUND("Heart Hound");

    private final String name;

    Environment(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
