package matrix;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HittingBrickTest {
    
    private HittingBrick hittingBrick;

    @BeforeEach
    public void setup() {
        hittingBrick = new HittingBrick();
    }

    @Test
    public void testHittingBrickSingleShot() {
        int[][] grid = {{1, 0 , 0, 0}, {1, 1, 1, 1}};
        int[][] hits = {{1, 0}};
        int[] result = hittingBrick.hitBricks(grid, hits);
        int[] expected = {3};
        assertArrayEquals(expected, result);
    }

    @Test
    public void testHittingBrickMultipleShotsInOrder() {
        int[][] grid = {{1, 0, 0, 0}, {1, 1, 1, 1}};
        int[][] hits = {{1, 0}, {1, 1}, {1, 2}};
        int[] result = hittingBrick.hitBricks(grid, hits);
        int[] expected = {3, 0, 0};
        assertArrayEquals(expected, result);
    }

    @Test
    public void testHittingBrickMultipleShotsReverseOrder() {
        int[][] grid = {{1, 0, 0, 0}, {1, 1, 1, 1}};
        int[][] hits = {{1, 2}, {1, 1}, {1, 0}};
        int[] result = hittingBrick.hitBricks(grid, hits);
        int[] expected = {1, 0, 0};
        assertArrayEquals(expected, result);
    }

    @Test
    public void testHittingBrickMultipleShotsConnectedToSinglePoint() {
        int[][] grid = {{1, 0, 0, 0}, {1, 1, 1, 1}, {1, 1, 0, 0}};
        int[][] hits = {{1, 2}, {1, 0}};
        int[] result = hittingBrick.hitBricks(grid, hits);
        int[] expected = {1, 3};
        assertArrayEquals(expected, result);
    }

    @Test
    public void testHittingBrickMultipleShotsConnectedToMultiplePoints() {
        int[][] grid = {{1, 0, 0, 1}, {1, 1, 1, 1}, {1, 1, 0, 0}};
        int[][] hits = {{0, 0}, {0, 3}};
        int[] result = hittingBrick.hitBricks(grid, hits);
        int[] expected = {0, 6};
        assertArrayEquals(expected, result);
    }    
    
    @Test
    public void testHittingBrickMultipleShotsAtEmptySpace() {
        int[][] grid = {{1, 0, 0, 0}, {1, 1, 1, 1}, {1, 1, 0, 0}};
        int[][] hits = {{2, 2}, {1, 2}, {1, 0}};
        int[] result = hittingBrick.hitBricks(grid, hits);
        int[] expected = {0, 1, 3};
        assertArrayEquals(expected, result);
    }    
}
