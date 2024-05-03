import java.util.*;
public class Subsequence {
    // return longest increasing subsequence
    public List<Integer> LIS(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }
        // keep increasing list of index
        List<Integer> sequence = new ArrayList<>();
        int[] parent = new int[nums.length];
        Arrays.fill(parent, -1);

        for (int i = 0; i < nums.length; i++) {
            if (sequence.isEmpty()) {
                sequence.add(i);
                parent[i] = -1;
            } else if (nums[sequence.get(sequence.size() - 1)] < nums[i]) {
                parent[i] = sequence.get(sequence.size() - 1);
                sequence.add(i);
            } else {
                // binary search to find the first smaller
                int left = 0;
                int right = sequence.size() - 1;
                while (left <= right) {
                    int mid = left + (right - left) / 2;
                    if (nums[sequence.get(mid)] >= nums[i]) {
                        right = mid - 1;
                    } else {
                        left = mid + 1;
                    }
                }
                if (left == 0) {
                    parent[i] = -1;
                } else {
                    parent[i] = sequence.get(left - 1);
                }
                sequence.set(left, i);
            }
        }

        // build res in reverse order
        List<Integer> list = new LinkedList<>();
        int cur = sequence.get(sequence.size() - 1);
        while (cur != -1) {
            list.add(0, nums[cur]);
            cur = parent[cur];
        }
        return list;
    }

    // a slightly modified solution
    // regular LIS
    // build a graph, using map to represent parent
    // find all leaf nodes with length = sequence.size()
    public int findNumberOfLIS(int[] nums) {
        // keep increasing list of index and count
        List<Integer> sequence = new ArrayList<>();
        // key = index, value = parent index
        Map<Integer, Integer> parentGraph = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            if (sequence.isEmpty()) {
                parentGraph.put(i, -1);
                sequence.add(i);
            } else if (nums[sequence.get(sequence.size() - 1)] < nums[i]) {
                parentGraph.put(i, sequence.get(sequence.size() - 1));
                sequence.add(i);
            } else {
                // binary search to find the first smaller
                int left = 0;
                int right = sequence.size() - 1;
                while (left <= right) {
                    int mid = left + (right - left) / 2;
                    if (nums[sequence.get(mid)] >= nums[i]) {
                        right = mid - 1;
                    } else {
                        left = mid + 1;
                    }
                }
                // find another potential LIS
                if (left == 0) {
                    parentGraph.put(i, -1);
                } else {
                    parentGraph.put(i, sequence.get(left - 1));
                }
                sequence.set(left, i);
            }
        }

        // find all leaf with height = sequence.size()
        Map<Integer, Integer> cache = new HashMap<>();
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            if (findHeight(parentGraph, i, cache) == sequence.size()) {
                count++;
            }
        }
        return count;
    }


    private int findHeight(Map<Integer, Integer> parentGraph, int cur, Map<Integer, Integer> cache) {
        if (cur == -1) {
            return 0;
        }
        if (cache.containsKey(cur)) {
            return cache.get(cur);
        }

        int h = 1 + findHeight(parentGraph, parentGraph.get(cur), cache);
        cache.put(cur, h);
        return h;
    }

    public static final void main(String[] args) {
        Subsequence instance = new Subsequence();
        int[] input = new int[]{2,5,1,5,7,2,41,7,41};
        int res = instance.findNumberOfLIS(input);

        System.out.println(res);
    }
}
