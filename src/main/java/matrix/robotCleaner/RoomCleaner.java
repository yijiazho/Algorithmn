package matrix.robotCleaner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RoomCleaner {
    private static final int[][] DIRECTIONS = new int[][] { { 1, 0 }, { 0, -1 }, { -1, 0 }, { 0, 1 } };

    private Set<List<Integer>> visited = new HashSet<>();

    // right turn + 1, left turn -1
    private int currentDirection = 0;

    /**
     * Clean the room using the robot cleaner. The robot cleaner can move, turn
     * left, turn right and clean the current cell. The robot cleaner can only move
     * to the next cell if it is not blocked. The robot cleaner can only turn left
     * or right, and it will always face the direction of the next cell before
     * moving. The robot cleaner can only clean the current cell.
     * 
     * @param robotCleaner the robot cleaner
     */
    public void cleanRoom(RobotCleaner robotCleaner) {
        // use a non adjacent point as the previous point
        visited.add(List.of(0, 0));
        clean(robotCleaner, 0, 0, 999, 999);
    }

    /**
     * Clean the room recursively, and the robot will turn to the direction of the
     * next cell before moving.
     * 
     * @param robotCleaner the robot cleaner
     * @param x            current x coordinate
     * @param y            current y coordinate
     * @param originalX    previous x coordinate
     * @param originalY    previous y coordinate
     */
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

    /**
     * Move the robot from (x0, y0) to (x1, y1), if the move is valid, return true,
     * otherwise return false. The valid move is two adjacent cells, and the robot
     * can move to the next cell. The robot will turn to the direction of the next
     * cell before moving.
     * 
     * @param robotCleaner the robot cleaner
     * @param x0           current x coordinate
     * @param y0           current y coordinate
     * @param x1           next x coordinate
     * @param y1           next y coordinate
     * @return true if the move is valid, false otherwise
     */
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

    /**
     * Turn the robot to the target direction
     * 
     * @param robotCleaner the robot cleaner
     * @param target       the target direction
     */
    private void turn(RobotCleaner robotCleaner, int target) {
        if (target >= 0 && target < 4) {
            while (currentDirection != target) {
                robotCleaner.turnRight();
                currentDirection = (currentDirection + 1) % 4;
            }
        }
    }
}
