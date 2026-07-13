package game.tictactoe;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import game.Player;
import game.RuleSet;

public class TicTacToeRuleSet implements RuleSet<TicTacToeBoard, TicTacToeMove, Player> {

    @Override
    public boolean isMoveValid(TicTacToeMove move, TicTacToeBoard board) {
        return move.row >= 0 && move.row < 3 &&
                move.col >= 0 && move.col < 3 &&
                board.isCellEmpty(move.row, move.col);
    }

    @Override
    public void applyMove(TicTacToeMove move, TicTacToeBoard board) {
        board.setCell(move.row, move.col, move.getPlayer().getSymbol());
    }

    @Override
    public Optional<Player> checkWinner(TicTacToeBoard board, List<Player> players) {
        for (Player player : players) {
            char symbol = player.getSymbol();
            if (hasThreeInARow(board, symbol)) {
                return Optional.of(player);
            }
        }
        return Optional.empty();
    }

    private boolean hasThreeInARow(TicTacToeBoard board, char symbol) {
        for (int i = 0; i < 3; i++) {
            // Rows
            if (board.getCell(i, 0).getSymbol() == symbol &&
                    board.getCell(i, 1).getSymbol() == symbol &&
                    board.getCell(i, 2).getSymbol() == symbol)
                return true;

            // Columns
            if (board.getCell(0, i).getSymbol() == symbol &&
                    board.getCell(1, i).getSymbol() == symbol &&
                    board.getCell(2, i).getSymbol() == symbol)
                return true;
        }

        // Diagonals
        return (board.getCell(0, 0).getSymbol() == symbol &&
                board.getCell(1, 1).getSymbol() == symbol &&
                board.getCell(2, 2).getSymbol() == symbol) ||
                (board.getCell(0, 2).getSymbol() == symbol &&
                        board.getCell(1, 1).getSymbol() == symbol &&
                        board.getCell(2, 0).getSymbol() == symbol);
    }

    @Override
    public boolean isGameOver(TicTacToeBoard board, List<Player> players) {
        return checkWinner(board, players).isPresent() || board.isFull();
    }

    @Override
    public List<TicTacToeMove> generateAvailableMoves(TicTacToeBoard board, Player player) {
        List<TicTacToeMove> moves = new ArrayList<>();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board.isCellEmpty(row, col)) {
                    moves.add(new TicTacToeMove(row, col, player));
                }
            }
        }
        return moves;
    }

    @Override
    public boolean supportsDraw() {
        return true;
    }

    @Override
    public boolean isDraw(TicTacToeBoard board, List<Player> players) {
        return !checkWinner(board, players).isPresent() && board.isFull();
    }
}
