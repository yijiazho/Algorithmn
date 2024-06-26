package linear;

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


    public int findNumberOfLIS(int[] nums) {
        int l = nums.length;
        // length of LIS end in position i
        int[] length = new int[l];
        // count of LIS at position i
        // count[i] = sum of count whose length == length[i] - 1
        int[] count = new int[l];
        count[0] = 1;
        Arrays.fill(length, 1);
        Arrays.fill(count, 1);

        int maxLen = 1;
        for (int i = 1; i < l; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    if (length[j] + 1 > length[i]) {
                        length[i] = length[j] + 1;
                        count[i] = count[j];
                    } else if (length[j] + 1 == length[i]) {
                        count[i] += count[j];
                    }

                }
            }
            maxLen = Math.max(maxLen, length[i]);
        }

        int res = 0;
        for (int i = 0; i < l; i++) {
            if (length[i] == maxLen) {
                res += count[i];
            }
        }
        return res;
    }



    public static final void main(String[] args) {
        Subsequence instance = new Subsequence();
        int[] input = new int[]{2,5,1,5,7,2,41,7,41};
        int res = instance.findNumberOfLIS(input);

        System.out.println(res);
    }
}
