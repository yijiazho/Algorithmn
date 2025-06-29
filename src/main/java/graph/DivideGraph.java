package graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class DivideGraph {

    /**
     * Given an original graph, undirected with n nodes from 0 to n - 1, subdivide
     * each edge into a chain of nodes. Find out the nodes reachable from starting
     * position 0 after subdivide within maxMoves
     * 
     * @param edges    edge array, each element is [node1, node2, count], where
     *                 node1 and node2 are the connected nodes, and the count means
     *                 the new nodes we are going to insert after subdivide. For
     *                 exampel for an edge [0, 1, 2], after subdivision we will
     *                 insert 2 new nodes between node0 and node1, 0 -- x1 -- x2 --
     *                 1
     * @param maxMoves number of maxMoves.
     * @param n        total Nodes in original graph
     * @return the total number of nodes reachale after subdivision
     */
    public int reachableNodes(int[][] edges, int maxMoves, int n) {
        // try to use a queue, where each element has (nextNode, count, remainingStep)
        List<List<DivideNode>> graph = buildGraph(edges, n);
        PriorityQueue<DivideNode> pq = new PriorityQueue<>((dn1, dn2) -> {
            // sort by total distance from node 0
            int totalDistance1 = maxMoves - dn1.remainingStep + dn1.divideCount;
            int totalDistance2 = maxMoves - dn2.remainingStep + dn2.divideCount;
            return totalDistance1 - totalDistance2;
        });

        Set<Integer> visitedOriginalNodes = new HashSet<>();
        // keep (smaller, larger, k)
        Set<List<Integer>> visitedDividedNodes = new HashSet<>();

        pq.offer(new DivideNode(0, 0, maxMoves));
        int reachableNodes = 1;
        visitedOriginalNodes.add(0);

        while (!pq.isEmpty()) {
            DivideNode node = pq.poll();
            int remainingStep = node.remainingStep;
            int from = node.node;
            for (DivideNode next : graph.get(from)) {
                int nextNode = next.node;
                int nextCount = next.divideCount;

                if (remainingStep >= nextCount + 1 && visitedOriginalNodes.add(nextNode)) {
                    // if there is enough step to move to this node
                    if (visitedOriginalNodes.add(nextNode)) {
                        pq.offer(new DivideNode(nextNode, nextCount, remainingStep - nextCount - 1));
                        reachableNodes += nextCount + 1;
                    } else {
                        int smaller;
                        int larger;
                        if (from < nextNode) {
                            smaller = from;
                            larger = nextNode;
                            for (int i = 1; i <= remainingStep; i++) {
                                if (visitedDividedNodes.add(List.of(smaller, larger, i))) {
                                    reachableNodes++;
                                }
                            }
                        } else {
                            smaller = nextNode;
                            larger = from;
                            for (int i = nextCount; i >= nextCount - remainingStep + 1; i--) {
                                if (visitedDividedNodes.add(List.of(smaller, larger, i))) {
                                    reachableNodes++;
                                }
                            }
                        }
                    }
                } else {
                    // do not offer, add those divided node to Set
                    int smaller;
                    int larger;
                    if (from < nextNode) {
                        smaller = from;
                        larger = nextNode;
                        for (int i = 1; i <= remainingStep; i++) {
                            if (visitedDividedNodes.add(List.of(smaller, larger, i))) {
                                reachableNodes++;
                            }
                        }
                    } else {
                        smaller = nextNode;
                        larger = from;
                        for (int i = nextCount; i >= nextCount - remainingStep + 1; i--) {
                            if (visitedDividedNodes.add(List.of(smaller, larger, i))) {
                                reachableNodes++;
                            }
                        }
                    }

                }
            }
        }

        return reachableNodes;

    }

    private List<List<DivideNode>> buildGraph(int[][] edges, int n) {
        List<List<DivideNode>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int[] edge : edges) {
            int from = edge[0];
            int to = edge[1];
            int count = edge[2];

            graph.get(from).add(new DivideNode(to, count, -1));
            graph.get(to).add(new DivideNode(from, count, -1));
        }
        return graph;
    }

    private class DivideNode {
        int node;
        int divideCount;
        int remainingStep;

        public DivideNode(int node, int divideCount, int remainingStep) {
            this.node = node;
            this.divideCount = divideCount;
            this.remainingStep = remainingStep;
        }
    }

    /**
     * Find out if an undirected graph with n nodes form 0 to n - 1 are bipartite or
     * not. Bipartite means the nodes can be split into two set, A and B, while each
     * edge connects a node in A and a node in B
     * 
     * @param graph graph[i] is an array of adjacent nodes to node i
     * @return boolean
     */
    public boolean isBipartite(int[][] graph) {
        int n = graph.length;
        int[] colors = new int[n];

        if (!paint(graph, colors, 0, 1)) {
            return false;
        }

        for (int i = 0; i < n; i++) {
            if (colors[i] == 0) {
                return false;
            }
        }

        return true;
    }

    private boolean paint(int[][] graph, int[] colors, int cur, int color) {
        if (colors[cur] != 0) {
            return colors[cur] == color;
        }
        colors[cur] = color;
        for (int next : graph[cur]) {

            boolean result = paint(graph, colors, next, -color);
            if (!result) {
                return false;
            }
        }
        return true;
    }
}
