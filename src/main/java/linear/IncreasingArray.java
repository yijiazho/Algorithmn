package linear;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IncreasingArray {

    private static final int MOD = 1000000007;

    // 1537
    // num1 and num2 are strictly increasing
    public int maxSum(int[] nums1, int[] nums2) {

        // build graph
        Map<Integer, List<Integer>> graph = buildGraph(nums1, nums2);

        // traverse, with memo
        Map<Integer, Integer> cache = new HashMap<>();
        return traverse(graph, 0, cache);
    }

    private Map<Integer, List<Integer>> buildGraph(int[] nums1, int[] nums2) {
        Map<Integer, List<Integer>> graph = new HashMap<>();

        int head1 = 0;
        int head2 = 0;
        int i = 0;
        int j = 0;
        while (i < nums1.length && j < nums2.length) {
            if (nums1[i] == nums2[j]) {
                List<Integer> list1 = graph.getOrDefault(head1, new ArrayList<>());
                List<Integer> list2 = graph.getOrDefault(head2, new ArrayList<>());
                list1.add(nums1[i]);
                list2.add(nums2[j]);
                graph.put(head1, list1);
                graph.put(head2, list2);

                head1 = nums1[i];
                head2 = nums2[j];
                i++;
                j++;
            } else if (nums1[i] > nums2[j]) {
                List<Integer> list2 = graph.getOrDefault(head2, new ArrayList<>());
                list2.add(nums2[j]);
                graph.put(head2, list2);
                head2 = nums2[j];

                j++;
            } else {
                List<Integer> list1 = graph.getOrDefault(head1, new ArrayList<>());
                list1.add(nums1[i]);
                graph.put(head1, list1);
                head1 = nums1[i];

                i++;
            }
        }

        while (i < nums1.length) {
            List<Integer> list1 = graph.getOrDefault(head1, new ArrayList<>());
            list1.add(nums1[i]);
            graph.put(head1, list1);
            head1 = nums1[i];

            i++;
        }

        while (j < nums2.length) {
            List<Integer> list2 = graph.getOrDefault(head2, new ArrayList<>());
            list2.add(nums2[j]);
            graph.put(head2, list2);
            head2 = nums2[j];

            j++;
        }

        return graph;
    }

    private int traverse(Map<Integer, List<Integer>> graph, int cur, Map<Integer, Integer> cache) {
        if (cache.containsKey(cur)) {
            return cache.get(cur);
        }
        if (!graph.containsKey(cur)) {
            return cur;
        }

        long res = 0;
        for (int next: graph.get(cur)) {
            res = Math.max(res, traverse(graph, next, cache));
        }
        res += cur;

        cache.put(cur, (int) (res % MOD));
        return (int) (res % MOD);
    }

    public static final void main(String[] args) {
        int[] n1 = new int[]{2,4,5,8,10};
        int[] n2 = new int[]{4,6,8,9};

        IncreasingArray increasingArray = new IncreasingArray();
        int res = increasingArray.maxSum(n1, n2);
        System.out.println(res);
    }
}
