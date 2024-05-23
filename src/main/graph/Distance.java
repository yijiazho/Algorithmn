package graph;

import java.util.ArrayList;
import java.util.List;

public class Distance {
    // assume it's connected and undirected
    public int[] sumOfDistancesInTree(int n, int[][] edges) {
        int[] res = new int[n];
        if (n == 1) {
            return res;
        }

        List<List<Integer>> graph = buildGraph(edges, n);

        // use each point as start point
        for (int i = 0; i < n; i++) {
            res[i] = getDist(graph, i, -1)[1];
        }

        return res;
    }

    private List<List<Integer>> buildGraph(int[][] edges, int n) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int[] edge: edges) {
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }
        return graph;
    }

    // res[0] = number of child nodes
    // res[1] = current sum
    private int[] getDist(List<List<Integer>> graph, int root, int from) {
        int sum = 0;
        int count = 1;
        for (int next: graph.get(root)) {
            if (next == from) {
                continue;
            }
            int[] nextDist = getDist(graph, next, root);
            count += nextDist[0];
            sum += nextDist[1];
        }
        // the edge of [root, from] is calculated count times
        sum = sum + count;
        return new int[] {count, sum};
    }

    public static final void main(String[] args) {
        int[][] edges = new int[][]{{0, 1}, {0, 2}, {2, 3}, {2, 4}, {2, 5}};
        Distance distance = new Distance();

        int[] res = distance.sumOfDistancesInTree(6, edges);
    }
}
