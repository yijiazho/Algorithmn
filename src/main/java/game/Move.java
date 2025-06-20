package game;

public abstract class Move<P> {
    private final P player;

    public Move(P player) {
        this.player = player;
    }

    public P getPlayer() {
        return player;
    }
}
