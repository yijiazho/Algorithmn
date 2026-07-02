package matrix.robotCleaner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RoomCleaner {
    private static final int[][] DIRECTIONS = new int[][] { { 1, 0 }, { 0, -1 }, { -1, 0 }, { 0, 1 } };

    private Set<List<Integer>> visited = new HashSet<>();

    // right turn + 1, left turn -1
    private int currentDirection = 0;

    public void cleanRoom(RobotCleaner robotCleaner) {
        // use a non adjacent point as the previous point
        visited.add(List.of(0, 0));
        clean(robotCleaner, 0, 0, 999, 999);
    }

    private void clean(RobotCleaner robotCleaner, int x, int y, int originalX, int originalY) {
        robotCleaner.clean();
        for (int[] dir : DIRECTIONS) {
            // try to move this direction
            int x1 = x + dir[0];
            int y1 = y + dir[1];
            if (!visited.add(List.of(x1, y1))) {
                if (move(robotCleaner, x, y, x1, y1)) {
                    clean(robotCleaner, x1, y1, x, y);
                }
            }
        }
        // back to original cell
        move(robotCleaner, x, y, originalX, originalY);
    }

    private boolean move(RobotCleaner robotCleaner, int x0, int y0, int x1, int y1) {
        if (x1 - x0 == 1 && y0 == y1) {
            // turn to direction 0
            turn(robotCleaner, 0);
        } else if (x0 == x1 && y1 == y0 - 1) {
            turn(robotCleaner, 1);
        } else if (x0 - x1 == 1 && y0 == y1) {
            turn(robotCleaner, 2);
        } else if (x0 == x1 && y1 == y0 + 1) {
            turn(robotCleaner, 3);
        } else {
            // not a valid move
            return false;
        }
        return robotCleaner.move();
    }

    private void turn(RobotCleaner robotCleaner, int target) {
        if (target >= 0 && target < 3) {
            while (currentDirection != target) {
                robotCleaner.turnRight();
                currentDirection = (currentDirection + 1) % 4;
            }
        }
    }
}
