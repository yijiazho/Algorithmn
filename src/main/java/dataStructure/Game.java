package dataStructure;

import java.util.List;

public interface Game<Move, Board> {

    void initialize();

    boolean makeMove(Move move);

    boolean isGameOver();

    int getWinner();

    boolean isDraw();

    Board display();

    void switchPlayer();

    List<Move> export();
}
