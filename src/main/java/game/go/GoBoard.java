package game.go;

import java.util.Optional;

import game.Board;

public class GoBoard implements Board<GoPiece> {
    private static final int[][] DIRECTIONS = new int[][] { { 1, 0 }, { 0, -1 }, { -1, 0 }, { 0, 1 } };
    private final GoPiece[][] grid = new GoPiece[8][8];

    public GoPiece getCell(int row, int col) {
        return grid[row][col];
    }

    public void setCell(int row, int col, GoPiece piece) {
        grid[row][col] = piece;
    }

    public Optional<GoPiece> hasPiece(int row, int col) {
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
