package dataStructure.game.chess;

import dataStructure.game.GameStatus;
import dataStructure.game.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChessGameTest {
    private ChessGame game;
    private Player white;
    private Player black;

    @BeforeEach
    public void setup() {
        white = new Player("White", 'W');
        black = new Player("Black", 'B');
        ChessBoard board = new ChessBoard();
        ChessRuleSet ruleSet = new ChessRuleSet();
        game = new ChessGame(ruleSet, board, List.of(white, black));
    }

    @Test
    public void testInitialGameStatus() {
        assertEquals(GameStatus.READY, game.getGameStatus());
        assertEquals(white, game.getCurrentPlayer());
    }

    @Test
    public void testMakeValidMove() {
        ChessMove move = new ChessMove(6, 0, 5, 0, white); // white pawn
        game.makeMove(move);
        assertEquals(black, game.getCurrentPlayer());
        assertEquals(1, game.getTurnCount());
    }

    @Test
    public void testUndoMove() {
        ChessMove move = new ChessMove(6, 0, 5, 0, white); // white pawn
        game.makeMove(move);
        game.undoLastMove();

        assertEquals(0, game.getTurnCount());
        assertEquals(white, game.getCurrentPlayer());
        assertTrue(game.getMoveHistory().isEmpty());
    }

    @Test
    public void testGameOverWhenKingMissing() {
        // simulate capture
        game.getBoard().setCell(0, 4, null); // black king removed
        assertTrue(game.isGameOver());
        assertEquals(GameStatus.WINNER_FOUND, game.getGameStatus());
        assertTrue(game.getWinner().isPresent());
        assertEquals(white, game.getWinner().get());
    }

    @Test
    public void testReset() {
        game.makeMove(new ChessMove(6, 0, 5, 0, black));
        game.reset();

        assertEquals(0, game.getTurnCount());
        assertTrue(game.getMoveHistory().isEmpty());
        assertEquals(GameStatus.READY, game.getGameStatus());
    }
}
