package game.tictactoe;

import game.Board;

public class TicTacToeBoard implements Board<TicTacToePiece> {
    private final TicTacToePiece[][] grid;

    public TicTacToeBoard() {
        this.grid = new TicTacToePiece[3][3];
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                grid[i][j] = new TicTacToePiece(' ');
    }

    private TicTacToeBoard(TicTacToePiece[][] grid) {
        this.grid = grid;
    }

    public TicTacToePiece getCell(int row, int col) {
        return grid[row][col];
    }

    public boolean isCellEmpty(int row, int col) {
        return grid[row][col].getSymbol() == ' ';
    }

    public void setCell(int row, int col, char symbol) {
        grid[row][col] = new TicTacToePiece(symbol);
    }

    public boolean isFull() {
        for (TicTacToePiece[] row : grid)
            for (TicTacToePiece cell : row)
                if (cell.getSymbol() == ' ')
                    return false;
        return true;
    }

    @Override
    public Board<TicTacToePiece> copy() {
        TicTacToePiece[][] newGrid = new TicTacToePiece[3][3];
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                newGrid[i][j] = new TicTacToePiece(this.grid[i][j].getSymbol());
        return new TicTacToeBoard(newGrid);
    }

    @Override
    public void clear() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                grid[i][j] = new TicTacToePiece(' ');
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (TicTacToePiece[] row : grid) {
            for (TicTacToePiece cell : row)
                sb.append(cell.getSymbol() == ' ' ? '.' : cell.getSymbol()).append(' ');
            sb.append('\n');
        }
        return sb.toString();
    }
}
