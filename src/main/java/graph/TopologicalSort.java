package graph;


import java.util.List;

public class TopologicalSort {
    enum State {
        OPEN,
        VISITING,
        VISITED
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
        for (int next: graph.get(cur)) {
            if (next == prev) {
                continue;
            }

            if (containsCycle(graph, states, next, cur)) {
                return true;
            }
        }

        // if no neighbours contain cycle, there's no cycle in this node
        states[cur] = State.VISITED;
        return false;
    }

    /**
     * Validate if a graph contains cycle. The graph is connected, no directed, no weighted
     * and is represented by a list of neighbours at each index
     * @param graph index is the index of node, value is a list of neighbours
     * @param n total number of vertex
     * @return if it contains cycle
     */
    public boolean graphValidation(List<List<Integer>> graph, int n) {
        State[] states = new State[n];
        for (int i = 0; i < n; i++) {
            states[i] = State.OPEN;
        }

        // if connected, one traverse at any node works
        return containsCycle(graph, states, 0, -1);
    }
}
