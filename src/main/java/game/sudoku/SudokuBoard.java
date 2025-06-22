package game.sudoku;

import game.Board;

public class SudokuBoard implements Board {

    private final int[][] board;
    private final int[][] initialBoard;
    public static final int REGULAR_SIZE = 9;
    public static final int SUB_MATRIX_SIZE = 3;

    public SudokuBoard(int[][] board) {
        if (board == null || board.length != REGULAR_SIZE || board[0].length != REGULAR_SIZE) {
            throw new IllegalArgumentException("The input matrix should be 9 x 9 in size for standard Sudoku game.");
        }
        this.board = board;
        initialBoard = board;
    }

    public int getBoardValue(int row, int col) {
        return board[row][col];
    }

    public void setBoardValue(int row, int col, int value) {
        if (board[row][col] != 0) {
            board[row][col] = value;
        } else {
            throw new IllegalArgumentException("The value at position should be 0, but is " + board[row][col]);
        }
    }

    @Override
    public Board copy() {
        throw new UnsupportedOperationException("Unimplemented method 'copy'");
    }

    @Override
    public void clear() {
        for (int i = 0; i < REGULAR_SIZE; i++) {
            for (int j = 0; j < REGULAR_SIZE; j++) {
                board[i][j] = initialBoard[i][j];
            }
        }
    }

}
