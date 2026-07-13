package game;

public abstract class Piece {

    private final String color;

    public Piece(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
