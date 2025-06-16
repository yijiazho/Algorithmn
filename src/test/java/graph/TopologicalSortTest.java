package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TopologicalSortTest {
    private TopologicalSort topologicalSort;

    @BeforeEach
    public void setup() {
        topologicalSort = new TopologicalSort();
    }

    @Test
    public void testTopologicalSort() {
        List<List<Integer>> graph = buildGraph();
        List<Integer> sortedNodes = topologicalSort.topologicalSortByTraverse(graph, 5);
        List<Integer> sortedNodes2 = topologicalSort.topologicalSortByIndegree(graph, 5);

        assertTrue(verifyTopologicalSorted(graph, sortedNodes));
        assertTrue(verifyTopologicalSorted(graph, sortedNodes2));
    }

    @Test
    public void testTopologicalSortCycle() {
        List<List<Integer>> graph = buildGraphWithCycle();
        List<Integer> sortedNodes = topologicalSort.topologicalSortByTraverse(graph, 3);
        List<Integer> sortedNodes2 = topologicalSort.topologicalSortByIndegree(graph, 3);

        assertNull(sortedNodes);
        assertNull(sortedNodes2);
    }

    private List<List<Integer>> buildGraph() {
        List<List<Integer>> graph = new ArrayList<>();

        // 0 -> 1, 0 -> 2
        graph.add(List.of(1, 2));
        // 1 -> 4
        graph.add(List.of(4));
        // 2 -> 3, 2 -> 4
        graph.add(List.of(3, 4));
        return graph;
    }

    private List<List<Integer>> buildGraphWithCycle() {
        List<List<Integer>> graph = new ArrayList<>();

        // 0 -> 1
        graph.add(List.of(1));
        // 1 -> 2
        graph.add(List.of(2));
        // 2 -> 0
        graph.add(List.of(0));
        return graph;
    }

    private boolean verifyTopologicalSorted(List<List<Integer>> graph, List<Integer> sortedNodes) {
        Map<Integer, Integer> nodeToIndex = new HashMap<>();
        for (int i = 0; i < sortedNodes.size(); i++) {
            nodeToIndex.put(sortedNodes.get(i), i);
        }

        // build all edges, and verify each from node is before each to node
        for (int i = 0; i < graph.size(); i++) {
            List<Integer> neighbours = graph.get(i);
            for (int neighbour : neighbours) {
                if (nodeToIndex.get(i) >= nodeToIndex.get(neighbour)) {
                    return false;
                }
            }
        }
        return true;
    }
}
