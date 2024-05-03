import java.util.*;

// Problem 1: Find min dist from start point, solve with dijkstra
// Problem 2: add a new edge (u, v) on the graph, and update the min distance array?
// Solve by updating graph and do another dijkstra, with updated input
// {u, dist[u]}, {v, dist[v]} in pq.
// Why not {start, 0}? because any node on before u, v is already optimized,

public class DijkstraReverse {
    static class Edge {
        int to, weight;

        public Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    public int[][] modifiedGraphEdges(int n, int[][] edges, int source, int destination, int target) {
        // initialize
        Map<Integer, List<Edge>> graph = buildGraph(edges);
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[source] = 0;

        // {index, dist[index]}
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        pq.offer(new int[]{source, 0});
        dijkstra(graph, dist, destination, pq);

        // corner cases
        if (dist[destination] < target) {
            // the dist is smaller even without the unknown edges
            return new int[0][0];
        } else if (dist[destination] == target) {
            return fill(edges);
        }

        // for each unknown edge, try to set weight to 1, and add to graph
        // if it successfully updated the dist[dest] < target,
        // we found the target, and set its weight to the diff
        for (int[] edge: edges) {
            if (edge[2] == -1) {
                edge[2] = 1;
                int from = edge[0];
                int to = edge[1];

                List<Edge> fromEdges = graph.getOrDefault(from, new ArrayList<>());
                List<Edge> toEdges = graph.getOrDefault(to, new ArrayList<>());
                fromEdges.add(new Edge(to, 1));
                toEdges.add(new Edge(from, 1));
                graph.put(from, fromEdges);
                graph.put(to, toEdges);

                pq.clear();
                pq.offer(new int[]{edge[0], dist[edge[0]]});
                pq.offer(new int[]{edge[1], dist[edge[1]]});

                dijkstra(graph, dist, destination, pq);
                if (dist[destination] <= target) {
                    // update to the diff
                    edge[2] += target- dist[destination];
                    return fill(edges);
                }
            }
        }

        // if we update all edges to 1, and still not able to find one
        return new int[0][0];
    }

    private Map<Integer, List<Edge>> buildGraph(int[][] edges) {
        Map<Integer, List<Edge>> graph = new HashMap<>();

        for (int[] edge: edges) {
            int from = edge[0];
            int to = edge[1];
            int weight = edge[2];
            if (weight > 0) {
                List<Edge> fromEdges = graph.getOrDefault(from, new ArrayList<>());
                fromEdges.add(new Edge(to, weight));
                graph.put(from, fromEdges);

                List<Edge> toEdges = graph.getOrDefault(to, new ArrayList<>());
                toEdges.add(new Edge(from, weight));
                graph.put(to, toEdges);
            }
        }
        return graph;
    }

    private void dijkstra(Map<Integer, List<Edge>> graph, int[] dist, int destination, PriorityQueue<int[]> pq) {
        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            if (cur[0] == destination) {
                return;
            }
            if (graph.containsKey(cur[0])) {
                for (Edge edge: graph.get(cur[0])) {
                    if (dist[edge.to] > edge.weight + dist[cur[0]]) {
                        dist[edge.to] = edge.weight + dist[cur[0]];
                        pq.offer(new int[]{edge.to, dist[edge.to]});
                    }
                }
            }
        }
    }

    private int[][] fill(int[][] edges) {
        for (int[] edge: edges) {
            if (edge[2] == -1) {
                edge[2] = 1000000000;
            }
        }
        return edges;
    }

    public static void main(String[] args) {
        int[][] edges = new int[][] {{4,1,-1},{2,0,-1},{0,3,-1},{4,3,-1}};
        DijkstraReverse instance = new DijkstraReverse();
        int[][] res = instance.modifiedGraphEdges(5, edges, 0, 1, 5);
    }
}
