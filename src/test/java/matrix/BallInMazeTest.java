package matrix;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BallInMazeTest {

    private BallInMaze ballInMaze;

    @BeforeEach
    public void setup() {
        ballInMaze = new BallInMaze();
    }

    @Test
    public void testHasPathAndShortestDistance() {
        int[][] maze = {
                { 0, 0, 0, 0, 0 },
                { 1, 1, 0, 0, 1 },
                { 0, 0, 0, 0, 0 },
                { 0, 1, 0, 0, 1 },
                { 0, 1, 0, 0, 0 }
        };

        int[] start = { 4, 3 };
        int[] destination = { 0, 1 };

        assertTrue(ballInMaze.hasPath(maze, start, destination));
        assertEquals("lul", ballInMaze.findShortestPath(maze, start, destination));
    }

    @Test
    public void testHasPathAndShortestDistanceNoPath() {
        int[][] maze = {
                { 0, 0, 0, 0, 0 },
                { 1, 1, 0, 0, 1 },
                { 0, 1, 0, 0, 0 },
                { 0, 1, 0, 0, 1 },
                { 0, 1, 0, 0, 0 }
        };

        int[] start = { 4, 3 };
        int[] destination = { 2, 0 };

        assertTrue(!ballInMaze.hasPath(maze, start, destination));
        assertEquals("impossible", ballInMaze.findShortestPath(maze, start, destination));
    }
}
