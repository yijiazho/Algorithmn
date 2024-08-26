package dataStructure;

import java.util.ArrayList;
import java.util.List;

public class TicTacToe implements Game<TicTacToeMove, TicTacToeBoard> {

    private TicTacToeBoard board;
    private int currentPlayer;
    private int moveCount;
    private List<TicTacToeMove> moveHistory;
    private static final int TOTAL_PLAYERS = 2;

    public TicTacToe(TicTacToeBoard board) {
        this.board = board;
        this.currentPlayer = 1;
        this.moveCount = 0;
        this.moveHistory = new ArrayList<>();
    }


    @Override
    public void initialize() {
        int n = board.getSize();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board.setValue(i, j, 0);
            }
        }
        this.currentPlayer = 1;
        this.moveCount = 0;
        this.moveHistory.clear();
    }

    @Override
    public boolean makeMove(TicTacToeMove move) {
        int x = move.getX();
        int y = move.getY();
        if (x < 0 || x >= board.getSize() || y < 0 || y >= board.getSize()) {
            return false;
        }

        board.setValue(x, y, currentPlayer);
        moveHistory.add(move);
        moveCount++;
        return true;
    }

    @Override
    public boolean isGameOver() {
        return checkWin() || isDraw();
    }

    @Override
    public int getWinner() {
        if (checkWin()) {
            return currentPlayer;
        }
        return 0;
    }

    @Override
    public boolean isDraw() {
        return moveCount == board.getSize() * board.getSize() && !checkWin();
    }

    @Override
    public TicTacToeBoard display() {
        return board;
    }

    @Override
    public void switchPlayer() {
        currentPlayer = (currentPlayer == 1) ? 2 : 1;
    }

    @Override
    public List<TicTacToeMove> export() {
        return new ArrayList<>(moveHistory);
    }

    private boolean checkWin() {
        int n = board.getSize();
        for (int i = 0; i < TOTAL_PLAYERS; i++) {
            if (checkRow(i) || checkCol(i)) {
                return true;
            }
        }

        return checkDiagonal() || checkAntiDiagonal();
    }

    private boolean checkRow(int row) {
        for (int i = 0; i < board.getSize(); i++) {
            if (board.getValue(row, i) != currentPlayer) {
                return false;
            }
        }
        return true;
    }

    private boolean checkCol(int col) {
        for (int i = 0; i < board.getSize(); i++) {
            if (board.getValue(i, col) != currentPlayer) {
                return false;
            }
        }
        return true;
    }

    private boolean checkDiagonal() {
        for (int i = 0; i < board.getSize(); i++) {
            if (board.getValue(i, i) != currentPlayer) {
                return false;
            }
        }
        return true;
    }

    private boolean checkAntiDiagonal() {
        int n = board.getSize();
        for (int i = 0; i < n; i++) {
            if (board.getValue(i, n - i - 1) != currentPlayer) {
                return false;
            }
        }
        return true;
    }
}

// player makes a move at position x, y
class TicTacToeMove {
    private int x;
    private int y;

    public TicTacToeMove(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

// assume the board is n * n
class TicTacToeBoard {
    private int[][] board;

    public TicTacToeBoard(int n) {
        board = new int[n][n];
    }

    public int getValue(int i, int j) {
        return board[i][j];
    }

    public void setValue(int i, int j, int v) {
        board[i][j] = v;
    }

    public int getSize() {
        return board.length;
    }
}
