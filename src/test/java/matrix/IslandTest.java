package matrix;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IslandTest {

    private Island island;

    @BeforeEach
    public void setup() {
        island = new Island();
    }

    @Test
    public void testLargeIslandDifferentRoots() {
        int[][] grid = new int[][] {
                { 0, 1, 0 },
                { 1, 0, 1 },
                { 0, 1, 0 }
        };

        int result = island.largestIsland(grid);
        assertEquals(5, result);
    }

    @Test
    public void testLargeIsland() {
        int[][] grid = new int[][] {
                { 0, 0, 0, 0, 0, 0, 0 },
                { 0, 1, 1, 1, 1, 0, 0 },
                { 0, 1, 0, 0, 1, 0, 0 },
                { 1, 0, 1, 0, 1, 0, 0 },
                { 0, 1, 0, 0, 1, 0, 0 },
                { 0, 1, 0, 0, 1, 0, 0 },
                { 0, 1, 1, 1, 1, 0, 0 }
        };

        int result = island.largestIsland(grid);
        assertEquals(18, result);
    }

    @Test
    public void testCountSubIslands() {
        int[][] grid1 = new int[][] {
                { 1, 1, 1, 0, 0 },
                { 0, 1, 1, 1, 1 },
                { 0, 0, 0, 0, 0 },
                { 1, 0, 0, 0, 0 },
                { 1, 1, 0, 1, 1 }
        };
        int[][] grid2 = new int[][] {
                { 1, 1, 1, 0, 0 },
                { 0, 0, 1, 1, 1 },
                { 0, 1, 0, 0, 0 },
                { 1, 0, 1, 1, 0 },
                { 0, 1, 0, 1, 0 }
        };

        int result = island.countSubIslands(grid1, grid2);
        assertEquals(3, result);
    }

    @Test
    public void testCountMultipleSubIslands() {
        int[][] grid1 = new int[][] {
                { 1, 0, 1, 0, 1 },
                { 1, 1, 1, 1, 1 },
                { 0, 0, 0, 0, 0 },
                { 1, 1, 1, 1, 1 },
                { 1, 0, 1, 0, 1 }
        };
        int[][] grid2 = new int[][] {
                { 0, 0, 0, 0, 0 },
                { 1, 1, 1, 1, 1 },
                { 0, 1, 0, 1, 0 },
                { 0, 1, 0, 1, 0 },
                { 1, 0, 0, 0, 1 }
        };

        int result = island.countSubIslands(grid1, grid2);
        assertEquals(2, result);
    }
}
