package game.go;

import java.util.Optional;

import game.Board;

public class GoBoard implements Board {
    private static final int[][] DIRECTIONS = new int[][] { { 1, 0 }, { 0, -1 }, { -1, 0 }, { 0, 1 } };
    private final Piece[][] grid = new Piece[8][8];

    public Piece getCell(int row, int col) {
        return grid[row][col];
    }

    public void setCell(int row, int col, Piece piece) {
        grid[row][col] = piece;
    }

    public Optional<Piece> hasPiece(int row, int col) {
        if (grid[row][col] != null) {
            return Optional.of(grid[row][col]);
        } else {
            return Optional.empty();
        }
    }

    public void capture(int row, int col) {

    }

    @Override
    public Board copy() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'copy'");
    }

    @Override
    public void clear() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'clear'");
    }

}
