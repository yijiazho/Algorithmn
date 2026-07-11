package linear;

public class StringInterleaving {

    /**
     * Find the number of ways to interleave word1 and word2 to form target. At
     * least one character from each word need to be used. You can skip any
     * character from word1 or word2.
     * 
     * @param word1  non-empty string, length <= 100
     * @param word2  non-empty string, length <= 100
     * @param target non-empty string, length <= 200
     * @return the number of ways to interleave word1 and word2 to form target
     */
    public int numberOfInterleavings(String word1, String word2, String target) {
        int m = word1.length();
        int n = word2.length();
        if (m + n < target.length()) {
            return 0;
        }

        // numberOfWays[i][j][k] tells the number of ways to interleave
        // word1.substring(0, i) and word2.substring(0, j) to form target.susbstring(0,
        // k), with word1.charAt(i - 1) and word2.charAt(j - 1) must be used.
        int[][][] numberOfWays = new int[m + 1][n + 1][target.length() + 1];

        numberOfWays[0][0][0] = 1;

        for (int k = 1; k <= target.length(); k++) {
            for (int i = 0; i <= m; i++) {
                for (int j = 0; j <= n; j++) {
                    if (i > 0 && k > 0 && word1.charAt(i - 1) == target.charAt(k - 1)) {
                        // use word1.charAt(i - 1), the previouse character from word1 can be any index
                        // < i - 1
                        for (int p = 0; p < i; p++) {
                            numberOfWays[i][j][k] += numberOfWays[p][j][k - 1];
                        }
                    }
                    if (j > 0 && k > 0 && word2.charAt(j - 1) == target.charAt(k - 1)) {
                        // use word2.charAt(j - 1), the previouse character from word2 can be any index
                        // < j - 1
                        for (int q = 0; q < j; q++) {
                            numberOfWays[i][j][k] += numberOfWays[i][q][k - 1];
                        }
                    }
                }
            }
        }

        // sum up all the ways that all target characters are matched
        int totalWays = 0;
        // need at least one character from each word, so i and j must be at least 1
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                totalWays += numberOfWays[i][j][target.length()];
            }
        }
        return totalWays;
    }

    /**
     * Tells if the target string is an interleaving of word1 and word2. Each
     * character from word1 and word2 must be used exactly once in order.
     * 
     * @param word1  non-empty string, length <= 100
     * @param word2  non-empty string, length <= 100
     * @param target non-empty string, length <= 200
     * @return true if the target string is an interleaving of word1 and word2,
     *         false otherwise
     */
    public boolean isInterleaving(String word1, String word2, String target) {
        int m = word1.length();
        int n = word2.length();

        if (m + n != target.length()) {
            return false;
        }

        // This tells if word1.substring(0, i) and word2.substring(0, j) can interleave
        // to form target.susbtring(0, i + j)
        boolean[][] isInterleaving = new boolean[m + 1][n + 1];

        isInterleaving[0][0] = true;

        for (int i = 0; i < m; i++) {
            if (word1.charAt(i) == target.charAt(i)) {
                isInterleaving[i + 1][0] = true;
            } else {
                break;
            }
        }

        for (int j = 0; j < n; j++) {
            if (word2.charAt(j) == target.charAt(j)) {
                isInterleaving[0][j + 1] = true;
            } else {
                break;
            }
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (word1.charAt(i - 1) == target.charAt(i + j - 1)) {
                    isInterleaving[i][j] = isInterleaving[i][j] || isInterleaving[i - 1][j];
                }
                if (word2.charAt(j - 1) == target.charAt(i + j - 1)) {
                    isInterleaving[i][j] = isInterleaving[i][j] || isInterleaving[i][j - 1];
                }
            }
        }
        return isInterleaving[m][n];
    }

}
