package game.go;

import game.Move;
import game.Player;

public class GoMove extends Move<Player> {
    private int row;
    private int col;

    public GoMove(Player player) {
        super(player);
    }

}
