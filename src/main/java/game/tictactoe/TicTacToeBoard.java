package game.tictactoe;

import game.Board;

public class TicTacToeBoard implements Board {
    private final char[][] grid;

    public TicTacToeBoard() {
        this.grid = new char[3][3];
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                grid[i][j] = ' ';
    }

    private TicTacToeBoard(char[][] grid) {
        this.grid = grid;
    }

    public char getCell(int row, int col) {
        return grid[row][col];
    }

    public void setCell(int row, int col, char symbol) {
        grid[row][col] = symbol;
    }

    public boolean isFull() {
        for (char[] row : grid)
            for (char cell : row)
                if (cell == ' ')
                    return false;
        return true;
    }

    @Override
    public Board copy() {
        char[][] newGrid = new char[3][3];
        for (int i = 0; i < 3; i++)
            System.arraycopy(this.grid[i], 0, newGrid[i], 0, 3);
        return new TicTacToeBoard(newGrid);
    }

    @Override
    public void clear() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                grid[i][j] = ' ';
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (char[] row : grid) {
            for (char cell : row)
                sb.append(cell == ' ' ? '.' : cell).append(' ');
            sb.append('\n');
        }
        return sb.toString();
    }
}
