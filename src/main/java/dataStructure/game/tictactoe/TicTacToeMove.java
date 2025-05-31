package dataStructure.game.tictactoe;

import dataStructure.game.Move;
import dataStructure.game.Player;


public class TicTacToeMove implements Move<Player> {
    public final int row;
    public final int col;
    private final Player player;

    public TicTacToeMove(int row, int col, Player player) {
        this.row = row;
        this.col = col;
        this.player = player;
    }

    @Override
    public String toString() {
        return "(" + row + ", " + col + ")";
    }
}
