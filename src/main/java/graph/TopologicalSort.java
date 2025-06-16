package graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TopologicalSort {
    enum State {
        VISITING,
        VISITED
    }

    /**
     * Validate if a graph contains cycle. The graph is connected, no directed, no
     * weighted
     * and is represented by a list of neighbours at each index
     * 
     * @param graph index is the index of node, value is a list of neighbours
     * @param n     total number of vertex
     * @return if it contains cycle
     */
    public boolean graphValidation(List<List<Integer>> graph, int n) {
        State[] states = new State[n];

        // if connected, one traverse at any node works
        return containsCycle(graph, states, 0, -1);
    }

    /**
     * Do a topological sort of given graph, connected, non-weighted, and directed
     * 
     * @param graph a list of list of integers, whose key is the index of node,
     *              And the value is the list of indicies of its neighbours
     * @param n     number of nodes
     * @return if the graph contains cycle, return null,
     *         otherwise, return ONE possible topological sort order
     */
    public List<Integer> topologicalSortByTraverse(List<List<Integer>> graph, int n) {
        List<Integer> sortedNodes = new LinkedList<>();
        State[] states = new State[n];
        boolean containsCycle = topologicalTraverse(graph, sortedNodes, states, 0);
        return containsCycle ? null : sortedNodes;
    }

    public List<Integer> topologicalSortByIndegree(List<List<Integer>> graph, int n) {
        int[] indegree = new int[n];

        for (List<Integer> neighbours : graph) {
            for (int neighbour : neighbours) {
                indegree[neighbour]++;
            }
        }

        List<Integer> sortedNodes = new ArrayList<>();
        Queue<Integer> zeroIndegreeIndex = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (indegree[i] == 0) {
                zeroIndegreeIndex.offer(i);
            }
        }

        while (!zeroIndegreeIndex.isEmpty()) {
            int from = zeroIndegreeIndex.poll();
            sortedNodes.add(from);

            if (graph.size() >= from + 1) {
                List<Integer> neighbours = graph.get(from);
                for (int neighbour : neighbours) {
                    indegree[neighbour]--;
                    if (indegree[neighbour] == 0) {
                        zeroIndegreeIndex.offer(neighbour);
                    }
                }
            }
        }

        return sortedNodes.size() == n ? sortedNodes : null;
    }

    private boolean topologicalTraverse(List<List<Integer>> graph, List<Integer> sortedNodes, State[] states, int cur) {
        // already checked, no cycle
        if (states[cur] == State.VISITED) {
            return false;
        }

        // currently traversing
        if (states[cur] == State.VISITING) {
            return true;
        }

        states[cur] = State.VISITING;
        // traverse neighbours
        if (graph.size() >= cur + 1) {
            for (int next : graph.get(cur)) {
                if (topologicalTraverse(graph, sortedNodes, states, next)) {
                    return true;
                }
            }
        }
        // add to head of the list if topological sort

        // if no neighbours contain cycle, there's no cycle in this node
        states[cur] = State.VISITED;
        sortedNodes.add(0, cur);
        return false;
    }

    private boolean containsCycle(List<List<Integer>> graph, State[] states, int cur, int prev) {
        // already checked, no cycle
        if (states[cur] == State.VISITED) {
            return false;
        }

        // currently traversing
        if (states[cur] == State.VISITING) {
            return true;
        }

        states[cur] = State.VISITING;
        // traverse neighbours
        for (int next : graph.get(cur)) {
            if (next == prev) {
                continue;
            }

            if (containsCycle(graph, states, next, cur)) {
                return true;
            }
        }
        // add to head of the list if topological sort

        // if no neighbours contain cycle, there's no cycle in this node
        states[cur] = State.VISITED;
        return false;
    }
}
