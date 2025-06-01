package dataStructure.game;

public interface Board {
    Board copy(); // For immutability or undo support

    void clear();


}
