package game.tictactoe;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;

import game.Game;
import game.GameStatus;
import game.Player;
import game.RuleSet;

public class TicTacToeGame implements Game<TicTacToeBoard, TicTacToeMove, Player> {
    private static final String STANDARD_TYPE = "Standard TicTacToe";
    private static final String CUSTOM_TYPE = "Custom TicTacToe";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private TicTacToeRuleSet ruleSet;
    private TicTacToeBoard board;
    private List<Player> players;
    private List<TicTacToeMove> moveList;
    private int turnCount;

    private Player currentPlayer = null;

    public TicTacToeGame(TicTacToeRuleSet ruleSet, TicTacToeBoard board, List<Player> players) {
        this.ruleSet = ruleSet;
        this.board = board;
        this.players = players;
        moveList = new ArrayList<>();
    }

    public void initialize() {
        board.clear();
        decideFirstPlayer();
        moveList.clear();
        turnCount = 0;
    }

    @Override
    public TicTacToeBoard getBoard() {
        return board;
    }

    @Override
    public List<Player> getPlayers() {
        return players;
    }

    @Override
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    @Override
    public boolean isGameOver() {
        return ruleSet.isGameOver(board, players);
    }

    @Override
    public Optional<Player> getWinner() {
        return ruleSet.checkWinner(board, players);
    }

    @Override
    public GameStatus getGameStatus() {
        if (turnCount == 0) {
            return GameStatus.READY;
        } else if (!ruleSet.isGameOver(board, players)) {
            return GameStatus.IN_PROGRESS;
        } else {
            return ruleSet.checkWinner(board, players).isEmpty() ? GameStatus.DRAW : GameStatus.WINNER_FOUND;
        }
    }

    @Override
    public List<TicTacToeMove> getAvailableMoves() {
        return ruleSet.generateAvailableMoves(board, currentPlayer);
    }

    @Override
    public void makeMove(TicTacToeMove move) {
        ruleSet.applyMove(move, board, currentPlayer);
        currentPlayer = getNextPlayer();
        moveList.add(move);
        turnCount++;
    }

    @Override
    public void undoLastMove() {
        if (moveList.isEmpty()) {
            return;
        }

        moveList.remove(moveList.size() - 1);
        rebuildBoardFromMoves();
        currentPlayer = getPreviousPlayer();
        turnCount--;
    }

    @Override
    public void reset() {
        initialize();
    }

    @Override
    public int getTurnCount() {
        return turnCount;
    }

    @Override
    public List<TicTacToeMove> getMoveHistory() {
        return moveList;
    }

    @Override
    public RuleSet<TicTacToeBoard, TicTacToeMove, Player> getRuleSet() {
        return ruleSet;
    }

    @Override
    public String getGameType() {
        // TODO: Standard ruleset vs custom ruleset
        return STANDARD_TYPE;
    }

    @Override
    public String exportToJson() {
        try {
            return objectMapper.writeValueAsString(moveList);
        } catch (Exception e) {
            throw new RuntimeException("Failed to export game to JSON", e);
        }
    }

    @Override
    public void importFromJson(String data) {
        try {
            List<TicTacToeMove> importedMoves = objectMapper.readValue(data,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, TicTacToeMove.class));
            initialize();

            moveList.addAll(importedMoves);
            rebuildBoardFromMoves();
        } catch (Exception e) {
            throw new RuntimeException("Failed to import game from JSON", e);
        }
    }

    private void decideFirstPlayer() {
        currentPlayer = players.get(0);
    }

    private Player getNextPlayer() {
        return players.get((players.indexOf(currentPlayer) + 1) % players.size());
    }

    private Player getPreviousPlayer() {
        return players.get((players.indexOf(currentPlayer) - 1 + players.size()) % players.size());
    }

    private void rebuildBoardFromMoves() {
        initialize();
        for (TicTacToeMove move : moveList) {
            ruleSet.applyMove(move, board, currentPlayer);
            currentPlayer = getNextPlayer();
            turnCount++;
        }
    }
}
