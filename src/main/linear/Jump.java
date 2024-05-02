package utility;

import java.util.*;

public class Jump {

    class Node {
        int index;
        int value;
        List<Node> children;

        public Node(int index, int value) {
            this.index = index;
            this.value = value;
            this.children = new ArrayList<>();
        }
    }

    public int maxJumps(int[] arr, int d) {
        // build a graph in O(N^2)
        // find the deepest root height

        int l = arr.length;
        Map<Integer, Node> map = new HashMap<>();
        for (int i = 0; i < l; i++) {
            Node node = map.getOrDefault(i, new Node(i, arr[i]));
            map.put(i, node);

            for (int left = i - 1; left >= 0; left--) {
                if (arr[left] >= arr[i]) break;

                Node leftNode = map.getOrDefault(left, new Node(left, arr[left]));
                map.put(left, leftNode);
                node.children.add(leftNode);
            }

            for (int right = i + 1; right < l; right++) {
                if (arr[right] >= arr[i]) break;

                Node rightNode = map.getOrDefault(right, new Node(right, arr[right]));
                map.put(right, rightNode);
                node.children.add(rightNode);
            }
        }

        int[] cache = new int[l];
        Arrays.fill(cache, -1);
        int max = 1;
        for (int i = 0; i < l; i++) {
            Node n = map.get(i);
            max = Math.max(max, findHeight(n, cache));
        }

        return max;
    }

    private int findHeight(Node root, int[] cache) {
        if (root.children.isEmpty()) {
            return 1;
        }

        if (cache[root.index] != -1) {
            return cache[root.index];
        }

        int max = 0;
        for (Node child: root.children) {
            max = Math.max(max, findHeight(child, cache));
        }

        cache[root.index] = max + 1;
        return max + 1;
    }

    public long minCost(int[] nums, int[] costs) {
        int l = nums.length;
        if (l == 1) {
            return costs[0];
        }

        // build a graph, key is index
        // value is all jumpable indices
        Map<Integer, List<Integer>> graph = new HashMap<>();

        for (int i = 0; i < l; i++) {
            List<Integer> list = new ArrayList<>();
            // n[i] <= n[j]
            for (int j = i + 1; j < l; j++) {
                if (nums[i] <= nums[j]) {
                    list.add(j);
                    if (j != i + 1) break;
                }

            }

            // n[i] > n[j]
            for (int j = i + 1; j < l; j++) {
                if (nums[i] > nums[j]) {
                    list.add(j);
                    if(j != i + 1) break;
                }
            }
        }


        // create minCost[] to keep min cost
        // use Dijkstra to update cost
        long[] minCost = new long[l];
        Arrays.fill(minCost, Long.MAX_VALUE);
        minCost[0] = 0L;
        // key = {index, cost}
        PriorityQueue<List<Integer>> pq = new PriorityQueue<>((l1, l2) -> minCost[l1.get(1)] < minCost[l2.get(1)] ? -1: 1);

        while (!pq.isEmpty()) {
            List<Integer> cur = pq.poll();
            if (cur.get(0) == l - 1){
                return cur.get(1);
            }

            if (minCost[cur.get(0)] > cur.get(1)) {
                minCost[cur.get(0)] = cur.get(1);
            }

            for (int next: graph.get(cur.get(0))) {
                List<Integer> list = List.of(next, cur.get(1) + costs[next]);
                pq.offer(list);
            }
        }

        return -1;
    }

    public static final void main(String[] args) {
        Jump instance = new Jump();
        int[] nums = new int[] {3,2,4,4,1};
        int[] costs = new int[] {3,7,6,4,2};

        long res = instance.minCost(nums, costs);
       
    }
}
