package dataStructure.game.chess;

import com.fasterxml.jackson.databind.ObjectMapper;
import dataStructure.game.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ChessGame implements Game<ChessBoard, ChessMove, Player> {
    private static final String GAME_TYPE = "Standard Chess";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private ChessRuleSet ruleSet;
    private ChessBoard board;
    private List<Player> players;
    private List<ChessMove> moveList;
    private int turnCount;
    private Player currentPlayer;

    public ChessGame(ChessRuleSet ruleSet, ChessBoard board, List<Player> players) {
        this.ruleSet = ruleSet;
        this.board = board;
        this.players = players;
        this.moveList = new ArrayList<>();
        this.turnCount = 0;
        decideFirstPlayer();
    }

    private void decideFirstPlayer() {
        currentPlayer = players.get(0); // Assume player[0] is white ('W')
    }

    private Player getNextPlayer() {
        int index = players.indexOf(currentPlayer);
        return players.get((index + 1) % players.size());
    }

    private Player getPreviousPlayer() {
        int index = players.indexOf(currentPlayer);
        return players.get((index - 1 + players.size()) % players.size());
    }

    @Override
    public ChessBoard getBoard() {
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
        }
        if (!isGameOver()) {
            return GameStatus.IN_PROGRESS;
        }
        return getWinner().isPresent() ? GameStatus.WINNER_FOUND : GameStatus.DRAW;
    }

    @Override
    public List<ChessMove> getAvailableMoves() {
        return ruleSet.generateAvailableMoves(board, currentPlayer);
    }

    @Override
    public void makeMove(ChessMove move) {
        if (!ruleSet.isMoveValid(move, board, currentPlayer)) {
            throw new IllegalArgumentException("Invalid move.");
        }

        ruleSet.applyMove(move, board, currentPlayer);
        moveList.add(move);
        currentPlayer = getNextPlayer();
        turnCount++;
    }

    @Override
    public void undoLastMove() {
        if (moveList.isEmpty()) return;

        moveList.remove(moveList.size() - 1);
        rebuildBoardFromHistory();
        currentPlayer = getPreviousPlayer();
        turnCount--;
    }

    private void rebuildBoardFromHistory() {
        board.clear();
        int index = 0;
        for (ChessMove move : moveList) {
            Player player = players.get(index % players.size());
            ruleSet.applyMove(move, board, player);
            index++;
        }
    }

    @Override
    public void reset() {
        board.clear();
        moveList.clear();
        turnCount = 0;
        decideFirstPlayer();
    }

    @Override
    public int getTurnCount() {
        return turnCount;
    }

    @Override
    public List<ChessMove> getMoveHistory() {
        return moveList;
    }

    @Override
    public RuleSet<ChessMove, ChessBoard, Player> getRuleSet() {
        return ruleSet;
    }

    @Override
    public String getGameType() {
        return GAME_TYPE;
    }

    @Override
    public String exportToJson() {
        return "";
    }

    @Override
    public void importFromJson(String data) {

    }
}
