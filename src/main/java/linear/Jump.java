package linear;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class Jump {

    class Node {
        int index;
        List<Node> children;

        public Node(int index) {
            this.index = index;
            this.children = new ArrayList<>();
        }
    }

    /**
     * For an integer array nums representing the heights of each index. Each time
     * you can jump from index i to index j if abs(i - j) <= d, nums[i] > nums[j]
     * and for all indicies k between i and j, nums[i] > nums[k]. Starting from any
     * index, find the maximum number of jumps.
     * 
     * @param nums the input array
     * @param d    the maximum jump distance
     * @return the maximum number of jumps
     */
    public int maxJumps(int[] nums, int d) {
        int l = nums.length;

        // build a graph
        List<Node> nodes = new ArrayList<>();
        for (int i = 0; i < l; i++) {
            Node node = new Node(i);
            nodes.add(node);
        }

        // for each index, we need to find the largest larger or equal number on the
        // left side, and the smallest larger on the right side.
        // int[] closestLargerLeft = new int[l];
        // int[] closestLargerRight = new int[l];

        Deque<Integer> leftSideCandidates = new ArrayDeque<>();
        Deque<Integer> rightSideCandidates = new ArrayDeque<>();

        for (int i = 0; i < l; i++) {
            while (!leftSideCandidates.isEmpty() && nums[leftSideCandidates.peek()] <= nums[i]) {
                int index = leftSideCandidates.pop();
                // add the removed index as a child of the current index if distance is within d
                if (i - index <= d && nums[i] > nums[index]) {
                    nodes.get(i).children.add(nodes.get(index));
                }
            }
            if (leftSideCandidates.isEmpty()) {
                // closestLargerLeft[i] = -1;
            } else {
                int index = leftSideCandidates.peek();
                // closestLargerLeft[i] = index;
                // add the current index as a child of the closest larger left index if distance
                // is within
                if (i - index <= d) {
                    nodes.get(index).children.add(nodes.get(i));
                }
            }
            leftSideCandidates.push(i);
        }

        for (int i = l - 1; i >= 0; i--) {
            while (!rightSideCandidates.isEmpty() && nums[rightSideCandidates.peek()] <= nums[i]) {
                int index = rightSideCandidates.pop();
                if (index - i <= d && nums[i] > nums[index]) {
                    nodes.get(i).children.add(nodes.get(index));
                }
            }
            if (rightSideCandidates.isEmpty()) {
                // closestLargerRight[i] = -1;
            } else {
                int index = rightSideCandidates.peek();
                // closestLargerRight[i] = index;
                if (index - i <= d) {
                    nodes.get(index).children.add(nodes.get(i));
                }
            }
            rightSideCandidates.push(i);
        }

        // find the deepest root height
        int[] cache = new int[l];
        Arrays.fill(cache, -1);
        int max = 1;
        for (int i = 0; i < l; i++) {
            Node n = nodes.get(i);
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
        for (Node child : root.children) {
            max = Math.max(max, findHeight(child, cache));
        }

        cache[root.index] = max + 1;
        return max + 1;
    }

    /**
     * Starting from index 0, you can jump to any index j such that j - i <= d. The
     * goal is to maximize the sum of all the index we jump to, including index 0 as
     * starting point and index n - 1 as the ending point.
     * 
     * @param nums the array of integers representing the values at each index
     * @param d    the maximum distance you can jump from the current index
     * @return the maximum sum achievable by jumping according to the rules
     */
    public int maxJumpPathSum(int[] nums, int d) {
        int n = nums.length;

        // keep track of the maximum sum we can achieve ending at each index
        int[] maxSumEndingAtIndex = new int[n];

        // for each index i, maxSumEndingAtIndex[i] = max(maxSumEningAtIndex[j]),
        // where i - k <= j < i

        // for index i we have candidate j1, j2 to select from for the max sum
        // if j1 < j2 < i, then we can ignore j1 if
        // maxSumEndingAtIndex[j1] <= maxSumEndingAtIndex[j2]

        Deque<Integer> candidates = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            // find max sum ending at i
            maxSumEndingAtIndex[i] = nums[i] +
                    (candidates.isEmpty() ? 0 : maxSumEndingAtIndex[candidates.peek()]);

            // remove candidates that are out of range from left side
            if (!candidates.isEmpty() && candidates.peekFirst() == i - d) {
                candidates.pollFirst();
            }

            // remove candidates that are smaller than the current index's max sum from
            // right side
            while (!candidates.isEmpty() && maxSumEndingAtIndex[candidates.peekLast()] <= maxSumEndingAtIndex[i]) {
                candidates.pollLast();
            }

            // add current index as a candidate
            candidates.offerLast(i);
        }

        return maxSumEndingAtIndex[n - 1];
    }

    /**
     * Find the minimum cost to jump from index 0 to index n - 1. You can jump from
     * index i to index j if i < j and 1. nums[i] <= nums[j] and nums[k] < nums[i]
     * for all k in range i < k < j, or 2. nums[i] > nums[j] and nums[k] >= nums[i]
     * for all k in range i < k < j. Each time you jump to index j, you pay
     * costs[j].
     * 
     * @param nums  the array of integers representing the values at each index
     * @param costs the array of integers representing the cost to jump to each
     *              index
     * @return the minimum cost to reach the last index
     */
    public long minJumpCost(int[] nums, int[] costs) {
        int l = nums.length;
        if (l == 1) {
            return 0L;
        }

        // build a graph, key is index
        // value is all jumpable indices
        Map<Integer, List<Integer>> graph = new HashMap<>();

        for (int i = 0; i < l; i++) {
            List<Integer> list = new ArrayList<>();
            // n[i] <= n[j] while n[i] > n[k]
            int j = i + 1;
            while (j < l && nums[i] > nums[j]) {
                j++;
            }
            if (j < l) {
                list.add(j);
            }

            // n[i] > n[j] while n[i] <= n[k]
            j = i + 1;
            while (j < l && nums[i] <= nums[j]) {
                j++;
            }
            if (j < l) {
                list.add(j);
            }

            graph.put(i, list);
        }

        // create minCost[] to keep min cost
        // use Dijkstra to update cost
        long[] minCost = new long[l];
        Arrays.fill(minCost, Long.MAX_VALUE);
        minCost[0] = 0L;

        PriorityQueue<Integer> pq = new PriorityQueue<>(
                (l1, l2) -> minCost[l1] < minCost[l2] ? -1 : 1);

        pq.offer(0);
        while (!pq.isEmpty()) {
            int cur = pq.poll();

            // reaches the destination, return the min cost
            if (cur == l - 1) {
                return minCost[cur];
            }

            for (int next : graph.get(cur)) {
                long newCost = minCost[cur] + costs[next];
                if (newCost < minCost[next]) {
                    minCost[next] = newCost;
                    pq.offer(next);
                }
            }
        }

        return -1;
    }
}
