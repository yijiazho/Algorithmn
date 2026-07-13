package game;

public interface Board<Piece> {
    Board<Piece> copy(); // For immutability or undo support

    void clear();

}
