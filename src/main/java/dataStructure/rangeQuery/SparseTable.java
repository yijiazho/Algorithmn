package dataStructure.rangeQuery;

/**
 * SparseTable is best for range query on static array
 * When the function is associate: f(a, f(b, c)) = f(f(a, b), c),
 * then the time complexity is O(log2(n))
 * When the function is overlap friendly: f((f(a, b), f(b, c) = f(a, f(b, c))
 * then the time complexity is O(1)
 * The idea is to pre store all answer for intervals of size 2^n
 */
public class SparseTable {
    // floor of log2(i)
    private final int[] log2;
    // Keeps track of all precomputed results
    // results[i][j] means the result of function in range [j, j + 2^i)
    private final int[][] results;

    // In this case we do a overlap friendly function, range query minimum
    public SparseTable(int[] nums) {
        int n = nums.length;
        log2 = new int[n + 1];
        for (int i = 2; i <= n; i++) {
            log2[i] = log2[i / 2] + 1;
        }

        // largest interval possible within the range
        int maxK = log2[n];
        results = new int[maxK + 1][n];

        // build intervals starting from 2^0
        for (int i = 0; i < n; i++) {
            results[0][i] = nums[i];
        }
        // to 2^maxK
        for (int i = 1; i <= maxK; i++) {
            int len = 1 << i;
            int half = len >> 1;

            for (int j = 0; j + len <= n; j++) {
                // the results in range [j, j + 2 ^ i) is equal to the results
                // in 2 intervals, [j, j + 2 ^ (i - 1)), [j + half, j + 2 ^ (i - 1))
                results[i][j] = Math.min(results[i -  1][j], results[i - 1][j + half]);
            }
        }
    }

    public int rangeQueryMinimum(int left, int right) {
        int len = right - left + 1;
        int k = log2[len];
        int blockSize = 1 << k;
        // it might overlap but this function is overlap friendly
        return Math.min(results[k][left], results[k][right - blockSize + 1]);
    }
}
