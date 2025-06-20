package game.tictactoe;

import game.Move;
import game.Player;

public class TicTacToeMove extends Move<Player> {
    public final int row;
    public final int col;

    public TicTacToeMove(int row, int col, Player player) {
        super(player);
        this.row = row;
        this.col = col;
    }

    @Override
    public String toString() {
        return "(" + row + ", " + col + ")";
    }
}
