package linear;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public class ArrayOperation {

    public int minimumPairRemoval(int[] nums) {
        // inspired by the union find, each time we need
        // 1. find the pair with min sum
        // 2. union the pairs
        // 3. detect if already sorted

        int n = nums.length;
        long[] prefixSum = new long[n + 1];
        for (int i = 0; i < n; i++) {
            prefixSum[i + 1] = prefixSum[i] + nums[i];
        }

        // initialize uf
        int[] parent = new int[n];
        int[] size = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            size[i] = 1;
        }

        // use a treeset to find edges with min sum
        // keep track of 2 way mapping, from node to edges and edge to nodes
        // use prefixsum to union
        // keep number of descending edges
        TreeSet<Edge> treeSet = new TreeSet<>((edge1, edge2) -> {
            long sum1 = edge1.leftValue + edge1.rightValue;
            long sum2 = edge2.leftValue + edge2.rightValue;
            if (sum1 != sum2) {
                return sum1 < sum2 ? -1: 1;
            } else {
                return edge1.left - edge2.left;
            }
        });

        // only keep the root as key
        Map<Integer, Edge> nodeToLeftEdges = new HashMap<>();
        Map<Integer, Edge> nodeToRightEdges = new HashMap<>();
        int numberOfDescendingEdges = 0;
        for (int i = 0; i < n - 1; i++) {
            Edge edge = new Edge(i, i + 1, nums[i], nums[i + 1]);
            nodeToRightEdges.put(i, edge);
            nodeToLeftEdges.put(i + 1, edge);
            treeSet.add(edge);
            if (nums[i] > nums[i + 1]) {
                numberOfDescendingEdges++;
            }
        }

        int count = 0;
        while (numberOfDescendingEdges > 0) {
            // union the 2 nodes of the edges
            // remove 3 edges, and add 2 edges back
            Edge edge = treeSet.first();
            // to remove the edge we will union the both end of the edge so it becomes one
            // single node
            union(edge.left, edge.right, parent, size);

            Edge leftEdge = nodeToLeftEdges.get(edge.left);
            Edge rightEdge = nodeToRightEdges.get(edge.right);
            numberOfDescendingEdges = updateEdgeAndUpdateUnsortedEdges(treeSet, nodeToLeftEdges, nodeToRightEdges,
                    parent, edge, numberOfDescendingEdges, true);
            numberOfDescendingEdges = updateEdgeAndUpdateUnsortedEdges(treeSet, nodeToLeftEdges, nodeToRightEdges,
                    parent, leftEdge, numberOfDescendingEdges, true);
            numberOfDescendingEdges = updateEdgeAndUpdateUnsortedEdges(treeSet, nodeToLeftEdges, nodeToRightEdges,
                    parent, rightEdge, numberOfDescendingEdges, true);

            // need to resort, use the treeset instead
            // each new edge's boundary is the root of the merged index
            Edge newLeftEdge = leftEdge == null ? null
                    : new Edge(root(leftEdge.left, parent), root(edge.right, parent), 
                    leftEdge.leftValue, (int)(prefixSum[edge.right + 1] - prefixSum[edge.left]));
            Edge newRightEdge = rightEdge == null ? null
                    : new Edge(root(edge.left, parent), root(rightEdge.right, parent), 
                    (int)(prefixSum[edge.right + 1] - prefixSum[edge.left]), rightEdge.rightValue);
            numberOfDescendingEdges = updateEdgeAndUpdateUnsortedEdges(treeSet, nodeToLeftEdges, nodeToRightEdges,
                    parent, newLeftEdge, numberOfDescendingEdges, false);
            numberOfDescendingEdges = updateEdgeAndUpdateUnsortedEdges(treeSet, nodeToLeftEdges, nodeToRightEdges,
                    parent, newRightEdge, numberOfDescendingEdges, false);

            count++;
        }

        return count;
    }

    private int updateEdgeAndUpdateUnsortedEdges(TreeSet<Edge> treeSet, Map<Integer, Edge> nodeToLeftEdges,
            Map<Integer, Edge> nodeToRightEdges, int[] parent, Edge edge, int number, boolean remove) {
        if (edge == null) {
            return number;
        }

        if (remove) {
            treeSet.remove(edge);
            nodeToLeftEdges.remove(edge.right);
            nodeToRightEdges.remove(edge.left);
            if (!edge.sorted) {
                return number - 1;
            }
        } else {
            treeSet.add(edge);
            nodeToLeftEdges.put(root(edge.right, parent), edge);
            nodeToRightEdges.put(root(edge.left, parent), edge);
            if (!edge.sorted) {
                return number + 1;
            }
        }
        return number;
    }

    private int root(int p, int[] parent) {
        int cur = p;
        while (parent[parent[cur]] != cur) {
            parent[cur] = parent[parent[cur]];
            cur = parent[cur];
        }
        parent[p] = cur;
        return cur;
    }

    private void union(int p, int q, int[] parent, int[] size) {
        int rootP = root(p, parent);
        int rootQ = root(q, parent);
        if (size[rootP] > size[rootQ]) {
            size[rootP] += size[rootQ];
            parent[rootQ] = rootP;
        } else {
            size[rootQ] += size[rootP];
            parent[rootP] = rootQ;
        }
    }
}

class Edge {
    int left;
    int right;
    int leftValue;
    int rightValue;
    boolean sorted;

    public Edge(int left, int right, int leftValue, int rightValue) {
        this.left = left;
        this.right = right;
        this.leftValue = leftValue;
        this.rightValue = rightValue;
        this.sorted = leftValue <= rightValue;
    }

    @Override
    public int hashCode() {
        return left * 31 + right;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Edge) {
            Edge that = (Edge) o;
            return this.left == that.left && this.right == that.right;
        } else {
            return false;
        }
    }
}
