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

    /**
     * Find if there exist i, j, k in n array, where i < j < k and n[i] < n[j] <
     * n[k]
     * 
     * @param n integer array, must be non empty
     * @return if the i, j, k triplet exist
     */
    public boolean increasingTriplet(int[] n) {
        // this is a special case of longest icreasing subsequence, which
        // can be solved in O(N log M), M is the length of subsequence.
        // In this case M = 3
        int len = n.length;
        if (len < 3) {
            return false;
        }

        int min = Integer.MAX_VALUE;
        int secondMin = Integer.MAX_VALUE;

        for (int i = 0; i < len; i++) {
            if (n[i] <= min) {
                min = n[i];
            } else if (n[i] <= secondMin) {
                secondMin = n[i];
            } else {
                return true;
            }
        }

        return false;
    }

    /**
     * Find the length of the longest palindrome subsequence
     * 
     * @param s orginal string, must be non empty
     * @return the length of longest palindrome subsequence
     */
    public int longestPalindromeSubsequence(String s) {
        int l = s.length();

        // array[i][j] = k means from s[i] to s[j], longest increasing subsequence
        // length is k, boundary inclusive, but not necessarily included in subsequence
        int[][] longestPalindromeSubsequence = new int[l][l];

        for (int i = 0; i < l; i++) {
            longestPalindromeSubsequence[i][i] = 1;
        }

        for (int i = l - 1; i >= 0; i--) {
            for (int j = i + 1; j < l; j++) {
                if (i + 1 == j) {
                    longestPalindromeSubsequence[i][j] = s.charAt(i) == s.charAt(j) ? 2 : 1;
                } else {
                    if (s.charAt(i) == s.charAt(j)) {
                        longestPalindromeSubsequence[i][j] = Math.max(longestPalindromeSubsequence[i][j],
                                longestPalindromeSubsequence[i + 1][j - 1] + 2);
                    } else {
                        longestPalindromeSubsequence[i][j] = Math.max(longestPalindromeSubsequence[i + 1][j],
                                longestPalindromeSubsequence[i][j - 1]);
                    }
                }
            }
        }

        return longestPalindromeSubsequence[0][l - 1];
    }

}
