package dataStructure.game.tictactoe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import game.Player;
import game.tictactoe.TicTacToeBoard;
import game.tictactoe.TicTacToeMove;
import game.tictactoe.TicTacToeRuleSet;

public class TicTacToeRuleSetTest {

    private TicTacToeRuleSet ruleSet;
    private TicTacToeBoard board;
    private Player player1;
    private Player player2;

    @BeforeEach
    public void setup() {
        board = new TicTacToeBoard();
        ruleSet = new TicTacToeRuleSet();
        player1 = new Player("Player1", 'o');
        player2 = new Player("Player2", 'x');
    }

    @Test
    public void testFirstMove() {
        TicTacToeMove move = new TicTacToeMove(1, 1, player1);
        assertTrue(ruleSet.isMoveValid(move, board));
        ruleSet.applyMove(move, board);
        assertEquals('o', board.getCell(1, 1));
    }

    @Test
    public void testInvalidMove() {
        TicTacToeMove firstMove = new TicTacToeMove(0, 0, player1);
        TicTacToeMove secondMove = new TicTacToeMove(0, 0, player2);
        ruleSet.applyMove(firstMove, board);
        assertFalse(ruleSet.isMoveValid(secondMove, board));
    }

    @Test
    public void testFindWinner() {
        TicTacToeMove firstMove = new TicTacToeMove(0, 0, player1);
        TicTacToeMove secondMove = new TicTacToeMove(0, 1, player1);
        TicTacToeMove thirdMove = new TicTacToeMove(0, 2, player1);

        ruleSet.applyMove(firstMove, board);
        ruleSet.applyMove(secondMove, board);
        ruleSet.applyMove(thirdMove, board);
        Optional<Player> potentialWinner = ruleSet.checkWinner(board, List.of(player1, player2));
        assertTrue(potentialWinner.isPresent());
        assertEquals(player1, potentialWinner.get());
    }

    /**
     * o x x
     * x o o
     * o o x
     */
    @Test
    public void testGameDraw() {
        ruleSet.applyMove(new TicTacToeMove(0, 0, player1), board);
        ruleSet.applyMove(new TicTacToeMove(0, 2, player2), board);
        ruleSet.applyMove(new TicTacToeMove(0, 1, player1), board);
        ruleSet.applyMove(new TicTacToeMove(1, 0, player2), board);
        ruleSet.applyMove(new TicTacToeMove(1, 1, player1), board);
        ruleSet.applyMove(new TicTacToeMove(2, 1, player2), board);
        ruleSet.applyMove(new TicTacToeMove(1, 2, player1), board);
        ruleSet.applyMove(new TicTacToeMove(2, 2, player2), board);
        ruleSet.applyMove(new TicTacToeMove(2, 0, player1), board);

        assertTrue(ruleSet.isDraw(board, List.of(player1, player2)));
    }
}
