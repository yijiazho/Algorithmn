package matrix;

import java.util.LinkedList;
import java.util.Queue;

public class BallInMaze {
    private static final int[][] DIRECTIONS = new int[][] {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

    /**
     * For a maze with 0 and 1s, 0 for space and 1 for walls,
     * if the ball can start from position start and end at
     * position destination, given the ball only stops after
     * reaching a wall
     * @param maze int[][] for maze
     * @param start position for start, int[2]
     * @param destination position for destination, int[2]
     * @return if the ball can stops at the ending position
     */
    public boolean hasPath(int[][] maze, int[] start, int[] destination) {
        // do a search, each time push the ball in 4 directions,
        // and find the next position, which the the 0 before 1

        Queue<int[]> positionQueue = new LinkedList<>();
        positionQueue.offer(start);

        boolean[][] visited = new boolean[maze.length][maze[0].length];
        visited[start[0]][start[1]] = true;

        while (!positionQueue.isEmpty()) {
            int[] position = positionQueue.poll();

            if (position[0] == destination[0] && position[1] == destination[1]) {
                return true;
            }

            for (int[] dir: DIRECTIONS) {
                int[] nextPosition = rollInDirection(maze, position, dir);
                if (nextPosition != null && !visited[nextPosition[0]][nextPosition[1]]) {
                    visited[nextPosition[0]][nextPosition[1]] = true;
                    positionQueue.offer(nextPosition);
                }
            }
        }
        // traverse all possible positions, but none same
        return false;
    }

    /**
     * return the stopping position for a ball in the maze,
     * starting at position start, with initial velocity at
     * direction, if no wall is ahead return null
     * @param maze int[m][n] with only 0 and 1
     * @param start start position, int[2]
     * @param direction velocity direction, int[2]
     * @return the ending position, or null if rolls out of maze
     */
    private int[] rollInDirection(int[][] maze, int[] start, int[] direction) {
        int m = maze.length;
        int n = maze[0].length;
        int i = start[0];
        int j = start[1];

        while (i >= 0 && i <m && j >= 0 && j < n) {
            // the previous position after the wall is the destination
            if (maze[i][j] == 1) {
                return new int[] {i - direction[0], j - direction[1]};
            }

            i += direction[0];
            j += direction[1];
        }
        // out of boundary, still no wall
        return null;
    }


    public static final void main(String[] args) {
        BallInMaze ballInMaze = new BallInMaze();
        int[][] maze = new int[][]
                {
                        {0, 0, 1, 0, 0},
                        {0, 0, 0, 0, 1},
                        {1, 0, 0, 0, 0},
                        {1, 1, 0, 0, 0}
                };
    }
}
