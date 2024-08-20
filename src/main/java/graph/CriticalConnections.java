package graph;

import java.util.*;

public class CriticalConnections {
    private int rank = 0;

    /**
     * Given a graph with n nodes, find a list of critical connections, which are
     * the edges that not in a circle. The graph is connected, no directed and no
     * weighted
     * @param n number of nodes
     * @param connections list of edges,
     * @return a list of critical edges
     */
    public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {

        // 1 build graph
        List<List<Integer>> graph = buildGraph(connections, n);
        // 2. traverse, find all edges in circle, and remove those

        // ranks, 0 for never visited, -1 for visited
        // starting node as 1, and each time touches new node + 1
        Set<List<Integer>> edgesInCycle = new HashSet<>();
        int[] ranks = new int[n];
        traverse(graph, edgesInCycle, ranks, 0, -1);

        List<List<Integer>> criticalEdges = new ArrayList<>();
        for (List<Integer> connection: connections) {
            Collections.sort(connection);
            if (!edgesInCycle.contains(connection)) {
                criticalEdges.add(connection);
            }
        }

        return criticalEdges;
    }

    /**
     * Rebuild a graph from a list of connections to a list of neighbours
     * @param connections
     * @param n
     * @return
     */
    private List<List<Integer>> buildGraph(List<List<Integer>> connections, int n) {
        List<List<Integer>> graph = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (List<Integer> connection: connections) {
            int from = connection.get(0);
            int to = connection.get(1);

            graph.get(from).add(to);
            graph.get(to).add(from);
        }
        return graph;
    }

    /** traverse the graph with a rank index that increases each time touching a
     * new node and return the smallest rank value of all neighbours
     *
     * @param graph represented by a list, index is number of nodes, value is
     *              a list of neighbours
     * @param edgesInCycle a set of edges that in cycle, sorted so that smaller
     *                     index on the left side
     * @param ranks an array of ranks, representing the order of touching each
     *              nodes, initialized at 0 which is not touched
     * @param cur index of current node
     * @param prev index of the previous node
     * @return smallest rank value of all neighbours
     */
    private int traverse(List<List<Integer>> graph, Set<List<Integer>> edgesInCycle,
                          int[] ranks, int cur, int prev) {
        if (ranks[cur] != 0) {
            // could be visiting or visited
            return ranks[cur];
        }

        rank++;
        ranks[cur] = rank;
        int min = rank;

        for (int next: graph.get(cur)) {
            if (next == prev) {
                // skip if coming back the prev node
                continue;
            }

            int nextRank = traverse(graph, edgesInCycle, ranks, next, cur);

            if (nextRank <= ranks[cur]) {
                // dedup and add
                List<Integer> list = new ArrayList<>();
                list.add(cur);
                list.add(next);
                Collections.sort(list);
                edgesInCycle.add(list);

                min = Math.min(min, nextRank);
            }
        }
        return min;
    }
}
