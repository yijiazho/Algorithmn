package dataStructure.game.chess;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import game.Player;
import game.chess.ChessBoard;
import game.chess.ChessMove;
import game.chess.ChessRuleSet;

public class ChessRuleSetTest {

    private ChessRuleSet ruleSet;
    private ChessBoard board;
    private Player white;
    private Player black;
    private List<Player> players;

    @BeforeEach
    public void setup() {
        ruleSet = new ChessRuleSet();
        board = new ChessBoard();
        white = new Player("White", 'W');
        black = new Player("Black", 'B');
        players = List.of(white, black);
    }

    @Test
    public void testInitialPawnMoveValid() {
        ChessMove move = new ChessMove(6, 0, 5, 0, white);
        assertTrue(ruleSet.isMoveValid(move, board, white));
    }

    @Test
    public void testInvalidMoveToOccupiedBySameColor() {
        ChessMove move = new ChessMove(7, 0, 7, 1, white);
        assertFalse(ruleSet.isMoveValid(move, board, white));
    }

    @Test
    public void testKnightMoveValid() {
        ChessMove move = new ChessMove(7, 1, 5, 2, white);
        assertTrue(ruleSet.isMoveValid(move, board, white));
    }

    @Test
    public void testCaptureMoveValid() {
        // manually remove the black pawn
        board.setCell(1, 0, null);
        ChessMove move = new ChessMove(0, 0, 6, 0, black);
        assertTrue(ruleSet.isMoveValid(move, board, black));
    }

    @Test
    public void testGenerateMovesForWhite() {
        List<ChessMove> moves = ruleSet.generateAvailableMoves(board, white);
        assertFalse(moves.isEmpty());
    }

    @Test
    public void testCheckWinnerAfterKingCaptured() {
        board.setCell(0, 4, null);
        Optional<Player> winner = ruleSet.checkWinner(board, players);
        assertTrue(winner.isPresent());
        assertEquals(white, winner.get());
    }
}
