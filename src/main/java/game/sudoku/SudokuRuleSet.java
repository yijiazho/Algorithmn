package game.sudoku;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import game.Player;
import game.RuleSet;

public class SudokuRuleSet implements RuleSet<SudokuBoard, SudokuMove, Player> {

    @Override
    public boolean isMoveValid(SudokuMove move, SudokuBoard board) {
        return board.getBoardValue(move.getRow(), move.getCol()) != 0 && validateMoveInRow(move, board)
                && validateMoveInCol(move, board) && validateMoveInSubMatrix(move, board);
    }

    @Override
    public void applyMove(SudokuMove move, SudokuBoard board) {
        board.setBoardValue(move.getRow(), move.getCol(), move.getValue());
    }

    public void undoMove(SudokuMove move, SudokuBoard board) {
        board.setBoardValue(move.getRow(), move.getCol(), 0);
    }

    @Override
    public Optional<Player> checkWinner(SudokuBoard board, List<Player> players) {
        throw new UnsupportedOperationException("There is no winner in Soduko game");
    }

    @Override
    public boolean isGameOver(SudokuBoard board, List<Player> players) {
        return generateAvailableMoves(board, players.get(0)).isEmpty();
    }

    @Override
    public List<SudokuMove> generateAvailableMoves(SudokuBoard board, Player player) {
        List<SudokuMove> availableMoves = new ArrayList<>();
        for (int i = 0; i < SudokuBoard.REGULAR_SIZE; i++) {
            for (int j = 0; j < SudokuBoard.REGULAR_SIZE; j++) {
                for (int value = 1; value <= SudokuBoard.REGULAR_SIZE; value++) {
                    SudokuMove move = new SudokuMove(player, i, j, value);
                    if (isMoveValid(move, board)) {
                        availableMoves.add(move);
                    }
                }
            }
        }
        return availableMoves;
    }

    @Override
    public boolean supportsDraw() {
        throw new UnsupportedOperationException("Soduko does not support draw");
    }

    @Override
    public boolean isDraw(SudokuBoard board, List<Player> players) {
        throw new UnsupportedOperationException("No draw in SodukuGame");
    }

    public boolean isBoardSolvable(SudokuBoard board) {
        return isBoardSolvable(board, new HashSet<>());
    }

    private boolean isBoardSolvable(SudokuBoard board, Set<SudokuBoard> invalidBoardSet) {
        boolean isBoardFull = true;
        for (int i = 0; i < SudokuBoard.REGULAR_SIZE; i++) {
            for (int j = 0; j < SudokuBoard.REGULAR_SIZE; j++) {
                if (board.getBoardValue(i, j) == 0) {
                    isBoardFull = false;
                    // try all possible moves
                    for (int value = 1; value <= SudokuBoard.REGULAR_SIZE; value++) {

                        SudokuMove move = new SudokuMove(null, i, j, value);
                        if (isMoveValid(move, board)) {
                            applyMove(move, board);
                            if (!invalidBoardSet.contains(board)) {
                                boolean result = isBoardSolvable(board);
                                if (result) {
                                    return true;
                                } else {
                                    invalidBoardSet.add(board);
                                }
                            }
                            undoMove(move, board);
                        }
                    }
                }
            }
        }

        return isBoardFull;
    }

    private boolean validateMoveInRow(SudokuMove move, SudokuBoard board) {
        for (int j = 0; j < SudokuBoard.REGULAR_SIZE; j++) {
            if (j == move.getCol()) {
                continue;
            }
            if (board.getBoardValue(move.getRow(), j) == move.getValue()) {
                return false;
            }
        }
        return true;
    }

    private boolean validateMoveInCol(SudokuMove move, SudokuBoard board) {
        for (int i = 0; i < SudokuBoard.REGULAR_SIZE; i++) {
            if (i == move.getRow()) {
                continue;
            }
            if (board.getBoardValue(i, move.getCol()) == move.getValue()) {
                return false;
            }
        }
        return true;
    }

    private boolean validateMoveInSubMatrix(SudokuMove move, SudokuBoard board) {
        int baseRow = move.getRow() / SudokuBoard.SUB_MATRIX_SIZE;
        int baseCol = move.getCol() / SudokuBoard.SUB_MATRIX_SIZE;

        for (int i = 0; i < SudokuBoard.SUB_MATRIX_SIZE; i++) {
            for (int j = 0; j < SudokuBoard.SUB_MATRIX_SIZE; j++) {
                if (board.getBoardValue(i + baseRow, j + baseCol) == move.getValue()) {
                    return false;
                }
            }
        }
        return true;
    }
}
