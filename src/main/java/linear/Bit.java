package linear;

public class Bit {

    /**
     * Given a string array, each element is a binary string with 0 and 1 only. Find
     * the size of the largest subset of the array such that there are at most m 0's
     * and n 1's in the subset.
     * 
     * @param strs the input array of binary strings
     * @param m    the maximum number of 0's allowed in the subset, positve integer
     * @param n    the maximum number of 1's allowed in the subset, positive integer
     * @return the size of the largest subset that satisfies the constraints
     */
    public int sizeOfLargestSubset(String[] strs, int m, int n) {

        int[][] maxLengthOfSubsetWithRemainingZerosAndOnes = new int[m + 1][n + 1];

        for (String str : strs) {
            int zeros = 0;
            int ones = 0;
            for (char c : str.toCharArray()) {
                if (c == '0') {
                    zeros++;
                } else if (c == '1') {
                    ones++;
                }
            }

            // Reverse iteration to avoid the current string affecting the results of the
            // current iteration
            for (int i = m; i >= zeros; i--) {
                for (int j = n; j >= ones; j--) {
                    maxLengthOfSubsetWithRemainingZerosAndOnes[i][j] = Math.max(
                            maxLengthOfSubsetWithRemainingZerosAndOnes[i][j],
                            maxLengthOfSubsetWithRemainingZerosAndOnes[i - zeros][j - ones] + 1);
                }
            }
        }
        return maxLengthOfSubsetWithRemainingZerosAndOnes[m][n];
    }

    /**
     * Recursive helper method to find the size of the largest subset.
     * 
     * @param strs  the input array of binary strings
     * @param index the current index in the array
     * @param m     the remaining number of 0's allowed
     * @param n     the remaining number of 1's allowed
     * @param count the current count of strings in the subset
     * @param cache the memoization cache, cache[index][m][n] stores the maximum
     *              count for the strs[0..index-1] with m 0's and n 1's remaining
     * @return the size of the largest subset that satisfies the constraints
     */
    private int search(String[] strs, int index, int m, int n,
            int[][][] cache) {

        if (cache[index][m][n] != 0) {
            return cache[index][m][n];
        }

        if (index == strs.length) {
            cache[index][m][n] = 0;
            return 0;

        }

        String str = strs[index];
        int zeros = 0;
        int ones = 0;
        for (char c : str.toCharArray()) {
            if (c == '0') {
                zeros++;
            } else if (c == '1') {
                ones++;
            }
        }
        int maxCount = 0;
        // choose the current string if it satisfies the constraints
        if (zeros <= m && ones <= n) {
            maxCount = Math.max(maxCount, search(strs, index + 1, m - zeros, n - ones, cache) + 1);
        }
        // do not choose the current string
        maxCount = Math.max(maxCount, search(strs, index + 1, m, n, cache));
        cache[index][m][n] = maxCount;
        return maxCount;
    }
}
