package graph;

import java.util.Arrays;

public class MinimumSpanningTree {

    /**
     * Find the weight of minimum spanning tree of a graph given the number of
     * vertices n and an array of undirected edges with weights. You can only build
     * edges from the array. If cannot form MST, return -1.
     * 
     * @param n     total number of vertices in the graph, positve integer
     * @param edges an array of undirected edges with weights, each element is
     *              [vertex1, vertex2, weight]
     * @return the weight of minimum spanning tree, or -1 if cannot form MST
     */
    public int findMinimumSpanningTree(int n, int[][] edges) {
        if (edges.length < n - 1) {
            return -1;
        }

        Arrays.sort(edges, (a, b) -> a[2] - b[2]);
        UnionFind uf = new UnionFind(n);
        int totalWeight = 0;
        int totalEdges = 0;
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            if (uf.find(u, v)) {
                continue;
            }

            uf.union(u, v);
            totalWeight += edge[2];
            totalEdges++;
            if (totalEdges == n - 1) {
                break;
            }
        }

        return totalEdges == n - 1 ? totalWeight : -1;
    }
}
