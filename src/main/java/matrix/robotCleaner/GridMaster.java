package matrix.robotCleaner;

public interface GridMaster {
    // Directions: U, D, L, R
    boolean canMove(char direction);

    // Try to move the robot in the given direction. If the cell in that direction
    // is blocked, the robot stays in the current cell.
    void move(char direction);

    // Return true if the current cell is the target cell, otherwise return false.
    boolean isTarget();
}
