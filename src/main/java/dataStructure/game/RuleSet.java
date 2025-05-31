package dataStructure.game;

import java.util.List;
import java.util.Optional;

public interface RuleSet<M, B, P> {

    boolean isMoveValid(M move, B board, P player);

    void applyMove(M move, B board, P player);

    Optional<P> checkWinner(B board, List<P> players);

    boolean isGameOver(B board, List<P> players);

    List<M> generateAvailableMoves(B board, P player);

    boolean supportsDraw();
}
