package game.chess;

import game.Move;
import game.Player;

public class ChessMove extends Move<Player> {
    public final int fromRow, fromCol;
    public final int toRow, toCol;

    public ChessMove(int fromRow, int fromCol, int toRow, int toCol, Player player) {
        super(player);
        this.fromRow = fromRow;
        this.fromCol = fromCol;
        this.toRow = toRow;
        this.toCol = toCol;
    }

    @Override
    public String toString() {
        return "(" + fromRow + "," + fromCol + ") -> (" + toRow + "," + toCol + ")";
    }
}
