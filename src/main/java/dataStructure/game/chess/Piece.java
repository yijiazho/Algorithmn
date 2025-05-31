package dataStructure.game.chess;

public class Piece {
    public enum Type { KING, QUEEN, ROOK, BISHOP, KNIGHT, PAWN }
    public final Type type;
    public final char color; // 'W' or 'B'

    public Piece(Type type, char color) {
        this.type = type;
        this.color = color;
    }

    @Override
    public String toString() {
        return color + "-" + type.name().charAt(0);
    }
}
