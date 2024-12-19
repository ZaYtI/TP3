package Frame;

public enum Tools {
    PENCIL ("Crayon"),
    LINE ("Ligne"),
    RECTANGLE ("Rectangle"),
    CIRCLE ("Cercle"),
    CLEAR ("Effacer");

    private final String name;

    private Tools(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
