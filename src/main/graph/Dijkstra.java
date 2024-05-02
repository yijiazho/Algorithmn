package utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class Dijkstra {
    class Path {
        int from;
        int to;
        int weight;

        public Path(int _from, int _to, int _weight) {
            from = _from;
            to = _to;
            weight = _weight;
        }
    }

    class Node{
        int n;
        int dist;

        public Node(int _n, int _dist) {
            n = _n;
            dist = _dist;
        }
    }

    public int dijkstra(List<List<Path>> graph, int n, int dest) {
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        PriorityQueue<Node> pq = new PriorityQueue<>((n1, n2) -> n1.dist - n2.dist);

        pq.offer(new Node(0, 0));
        dist[0] = 0;
        while (!pq.isEmpty()) {
            Node cur = pq.poll();

            for (Path path: graph.get(cur.n)) {
                int next = path.to;
                if (dist[next] > dist[cur.n] + path.weight) {
                    dist[next] = dist[cur.n] + path.weight;
                    pq.offer(new Node(next, dist[cur.n] + path.weight));
                }
            }
        }
        return dist[dest];
    }

    private List<List<Path>> buildGraph(int n, int[][] edges) {
        List<List<Path>> res = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            res.add(new ArrayList<>());
        }
        for (int[] edge: edges) {
            Path path = new Path(edge[0], edge[1], edge[2]);
            res.get(edge[0]).add(path);
        }

        return res;
    }


}
