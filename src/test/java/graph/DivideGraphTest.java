package graph;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DivideGraphTest {

    private DivideGraph divideGraph;

    @BeforeEach
    public void setup() {
        divideGraph = new DivideGraph();
    }

    @Test
    public void testReachableNodes() {
        int[][] edges = new int[][] {
                { 0, 1, 10 },
                { 0, 2, 1 },
                { 1, 2, 2 }
        };

        int result = divideGraph.reachableNodes(edges, 6, 3);
        assertEquals(13, result);
    }

    @Test
    public void testNonBipartite() {
        int[][] graph = new int[][] {
                { 1, 2, 3 },
                { 0, 2 },
                { 0, 1, 3 },
                { 0, 2 }
        };

        boolean result = divideGraph.isBipartite(graph);
        assertEquals(false, result);
    }

    @Test
    public void testBipartite() {
        int[][] graph = new int[][] {
                { 1, 3 },
                { 0, 2 },
                { 1, 3 },
                { 0, 2 }
        };

        boolean result = divideGraph.isBipartite(graph);
        assertEquals(true, result);
    }

    @Test
    public void testBipartiteUnConnected() {
        int[][] graph = new int[][] {
                { 1, 3 },
                { 0, 2 },
                { 1, 3 },
                { 0, 2 },
                { 5 },
                { 4 }
        };

        boolean result = divideGraph.isBipartite(graph);
        assertEquals(false, result);
    }
}
