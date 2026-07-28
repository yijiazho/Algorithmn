package linear;

public class Prefix {

    /**
     * Given an integer array, if we split the array into two parts, how many ways
     * can we split the array such that the first half is a prefix of the second
     * half.
     * 
     * @param nums the input integer array
     * @return the number of ways to split the array
     */
    public int numberOfPrefixes(int[] nums) {
        int n = nums.length;
        // longestMatching[i][j] means the longest matching starting from i and j
        // both inclusive
        int[][] longestMatching = new int[n][n];

        for (int i = n - 1; i >= 0; i--) {
            for (int j = i; j < n; j++) {
                if (nums[i] == nums[j]) {
                    if (i == n - 1 || j == n - 1) {
                        longestMatching[i][j] = 1;
                    } else {
                        longestMatching[i][j] = longestMatching[i + 1][j + 1] + 1;
                    }
                }
            }
        }

        // for subarray1 [start1, end1] and subarry2 [start2, end2],
        // subarray1 is a prefix of subarray2 if and only if
        // longestMatching[start1][start2] >= end1 - start1 + 1
        // meaning the entire subarray1 (or even more) is matching with subarray2

        // count the number of subarray if we split the array into two parts
        int count = 0;
        for (int i = 0; i < n - 1; i++) {
            // subarray1 is [0, i], subarray2 is [i + 1, n - 1]
            if (longestMatching[0][i + 1] >= i + 1) {
                count++;
            }
        }

        return count;
    }

    /**
     * Use rolling hash to check if subarray1 is a prefix of subarray2
     * 
     * @param hash   The rolling hash of the array
     * @param pow    The power of the base used in rolling hash
     * @param start1 The starting index of subarray1
     * @param end1   The ending index of subarray1
     * @param start2 The starting index of subarray2
     * @param end2   The ending index of subarray2
     * @return true if subarray1 is a prefix of subarray2, false otherwise
     */
    private boolean isPrefix(long[] hash, long pow[], int start1, int end1, int start2, int end2) {
        long hash1 = hash[end1] - hash[start1] * pow[end1 - start1];
        long hash2 = hash[start2 + end1 - start1] - hash[start2] * pow[end1 - start1];
        return hash1 == hash2;
    }

    /**
     * 
     * @param nums
     * @return
     */
    private int[] buildZArray(int[] nums) {
        int n = nums.length;
        int[] z = new int[n];

        int left = 0;
        int right = 0;

        for (int i = 1; i < n; i++) {
            if (i <= right) {
                z[i] = Math.min(right - i + 1, z[i - left]);
            }
            while (i + z[i] < n && nums[z[i]] == nums[i + z[i]]) {
                z[i]++;
            }
            if (i + z[i] - 1 > right) {
                left = i;
                right = i + z[i] - 1;
            }
        }
        return z;
    }
}
