package constants;

public enum Environment {
    FIRESTORM("Firestorm"),
    WINTERFELL("Winterfell"),
    HEART_HOUND("Heart Hound");

    private final String name;

    Environment(String name) {
        this.name = name;
    }
}
