package graph;

import java.util.ArrayList;
import java.util.List;

public class MinimumVertexCover {

    /**
     * Find the minimum vertex cover of a graph represented by edges and number of
     * vertices n. A vertex cover is a set of vertices such that every edge in the
     * graph is incident to at least one vertex in the set.
     * 
     * @param edges a list of edges, where each edge is represented as a list of two
     *              integers [u, v] indicating an undirected edge between vertices u
     *              and v
     * @param n     the number of vertices in the graph
     * @return a list of integers representing the vertices in the minimum vertex
     *         cover
     */
    public List<Integer> findMinimumVertexCover(List<List<Integer>> edges, int n) {
        // Build the graph
        Vertex[] vertices = new Vertex[n];
        for (int i = 0; i < n; i++) {
            vertices[i] = new Vertex(i);
        }

        for (List<Integer> edge : edges) {
            int u = edge.get(0);
            int v = edge.get(1);
            vertices[u].neighbours.add(vertices[v]);
            vertices[v].neighbours.add(vertices[u]);
        }

        // Implement the minimum vertex cover
        boolean[] selected = new boolean[n];
        boolean[] best = new boolean[n];
        int[] bestSize = new int[] { n + 1 };

        search(vertices, selected, 0, best, bestSize);

        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (best[i]) {
                result.add(i);
            }
        }
        return result;
    }

    private void search(Vertex[] vertices, boolean[] selected, int selectedCount, boolean[] best, int[] bestSize) {
        if (selectedCount >= bestSize[0]) {
            return;
        }

        int[] edge = findUncoveredEdge(vertices, selected);
        if (edge == null) {
            bestSize[0] = selectedCount;
            System.arraycopy(selected, 0, best, 0, selected.length);
            return;
        }

        selected[edge[0]] = true;
        search(vertices, selected, selectedCount + 1, best, bestSize);
        selected[edge[0]] = false;

        if (edge[0] != edge[1]) {
            selected[edge[1]] = true;
            search(vertices, selected, selectedCount + 1, best, bestSize);
            selected[edge[1]] = false;
        }
    }

    private int[] findUncoveredEdge(Vertex[] vertices, boolean[] selected) {
        for (Vertex vertex : vertices) {
            for (Vertex neighbour : vertex.neighbours) {
                if (!selected[vertex.id] && !selected[neighbour.id]) {
                    return new int[] { vertex.id, neighbour.id };
                }
            }
        }
        return null;
    }

    class Vertex {
        int id;
        List<Vertex> neighbours;

        public Vertex(int id) {
            this.id = id;
            this.neighbours = new ArrayList<>();
        }
    }
}
