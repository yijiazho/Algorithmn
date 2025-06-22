package game;

import java.util.List;
import java.util.Optional;

public interface RuleSet<B, M, P> {

    boolean isMoveValid(M move, B board);

    void applyMove(M move, B board);

    Optional<P> checkWinner(B board, List<P> players);

    boolean isGameOver(B board, List<P> players);

    List<M> generateAvailableMoves(B board, P player);

    boolean supportsDraw();

    boolean isDraw(B board, List<P> players);
}
