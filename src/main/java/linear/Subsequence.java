package linear;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Subsequence {
    // return one longest increasing subsequence
    public List<Integer> longestIncreasingSubsequence(int[] nums) {
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

    public int findNumberOfLongestIncreasingSubsequence(int[] nums) {
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

    public int longestCommonSubsequence(String a, String b) {
        int m = a.length();
        int n = b.length();

        // l[i][j] means the length of longest subsequencce from
        // a.substring(0, i) and b.substring(0, j), while both
        // a[i - 1] and b[j - 1] are not necessaruly included
        int[][] longestSubsequence = new int[m + 1][n + 1];

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    longestSubsequence[i][j] = longestSubsequence[i - 1][j - 1] + 1;
                } else {
                    longestSubsequence[i][j] = Math.max(longestSubsequence[i - 1][j], longestSubsequence[i][j - 1]);
                }
            }
        }
        // can be improved by m * 2
        return longestSubsequence[m][n];
    }
}
