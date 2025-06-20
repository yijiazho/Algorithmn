package game;

import java.util.List;
import java.util.Optional;

public interface Game<B, M, P> extends Exportable {
    B getBoard();

    List<P> getPlayers();

    P getCurrentPlayer();

    boolean isGameOver();

    Optional<P> getWinner();

    GameStatus getGameStatus();

    List<M> getAvailableMoves();

    void makeMove(M move);

    void undoLastMove();

    void reset();

    int getTurnCount();

    List<M> getMoveHistory();

    RuleSet<B, M, P> getRuleSet();

    String getGameType();
}
