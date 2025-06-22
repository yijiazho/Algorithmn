package game.sudoku;

import game.Move;
import game.Player;

public class SudokuMove extends Move<Player> {
    private final int row;
    private final int col;
    private final int value;

    public SudokuMove(Player player, int row, int col, int value) {
        super(player);
        this.row = row;
        this.col = col;
        this.value = value;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getValue() {
        return value;
    }
}
