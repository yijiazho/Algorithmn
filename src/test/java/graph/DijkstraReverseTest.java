package graph;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DijkstraReverseTest {

    private DijkstraReverse dijkstraReverse;

    @BeforeEach
    public void setup() {
        dijkstraReverse = new DijkstraReverse();
    }

    @Test
    public void testDijkstraReverse() {
        // create a graph that is valid
        int[][] edges = new int[][] {
                { 0, 1, 1 },
                { 0, 2, 2 },
                { 0, 3, 9 },
                { 1, 3, -1 },
                { 2, 3, 5 }
        };

        int[][] result = dijkstraReverse.modifiedGraphEdges(4, edges, 0, 3, 4);
        int[][] expected = new int[][] {
                { 0, 1, 1 },
                { 0, 2, 2 },
                { 0, 3, 9 },
                { 1, 3, 3 },
                { 2, 3, 5 }
        };
        assertArrayEquals(expected, result);
    }

    @Test
    public void testDijkstraReverseNoPath() {
        // create a graph that the distance is already smaller than the target
        int[][] edges = new int[][] {
                { 0, 1, 1 },
                { 0, 2, 2 },
                { 0, 3, 3 },
                { 1, 3, -1 },
                { 2, 3, 5 }
        };

        int[][] result = dijkstraReverse.modifiedGraphEdges(4, edges, 0, 3, 4);
        int[][] expected = new int[0][0];
        assertArrayEquals(expected, result);
    }
}
