package linear;

public class Palindrome {

    /**
     * Find the shortest palindrome string starting from the original input
     * by adding characters on the beginning only
     * 
     * @param s original input string, must be non empty
     * @return shortest string that is palindrome
     */
    public String shortestPalindromeAfterAppendingLeadingCharacters(String s) {
        int l = s.length();
        if (l == 0) {
            return "";
        }

        // if s.substring(i, j + 1) is palindrome
        boolean[][] isPalindrome = new boolean[l][l];

        // initialize
        for (int i = 0; i < l; i++) {
            isPalindrome[i][i] = true;
        }

        // calculate the boolean[][]
        for (int i = l - 1; i >= 0; i--) {
            for (int j = i + 1; j < l; j++) {
                if (j == i + 1) {
                    isPalindrome[i][j] = s.charAt(i) == s.charAt(j);
                } else {
                    if (s.charAt(i) == s.charAt(j) && isPalindrome[i + 1][j - 1]) {
                        isPalindrome[i][j] = true;
                    }
                }
            }
        }

        // find the longest palindrome from head
        int tail = 0;
        for (int i = 1; i < l; i++) {
            if (isPalindrome[0][i]) {
                tail = i;
            }
        }

        // concat
        String substring = s.substring(tail + 1);
        String reverse = new StringBuilder(substring).reverse().toString();
        return reverse + s;
    }

    /**
     * Find if a string is palindrome if we can remove at most one character in any
     * position
     * 
     * @param s original input string, must be non empty
     * @return if we can make the original string an palindrome one
     */
    public boolean isPalindromeAfterRemovingAtMostOneCharacter(String s) {
        int l = s.length();
        // isPalin[i][j][k] means if s.substring(i, j + 1) is palindrome
        // if remove k from this substring
        boolean[][][] isPalin = new boolean[l][l][2];

        for (int i = 0; i < l; i++) {
            isPalin[i][i][0] = true;
        }

        for (int i = 0; i < l - 1; i++) {
            isPalin[i][i + 1][1] = true;
        }

        for (int i = l - 1; i >= 0; i--) {
            for (int j = i + 1; j < l; j++) {
                if (j == i + 1) {
                    isPalin[i][j][0] = s.charAt(i) == s.charAt(j);
                } else {
                    isPalin[i][j][0] = isPalin[i][j][0] ||
                            (isPalin[i + 1][j - 1][0] && s.charAt(i) == s.charAt(j));
                }

                // remove 1, either s[i], s[j] or not remove
                isPalin[i][j][1] = isPalin[i][j][1] || isPalin[i + 1][j][0];
                isPalin[i][j][1] = isPalin[i][j][1] || isPalin[i][j - 1][0];
                isPalin[i][j][1] = isPalin[i][j][1] || (isPalin[i + 1][j - 1][1] && s.charAt(i) == s.charAt(j));
            }
        }

        return isPalin[0][l - 1][0] || isPalin[0][l - 1][1];
    }

    /**
     * Find out if a string can be palindrome by removing at most k characters
     * 
     * @param s original string, non empty, with lower cases only
     * @param k integer, which is no larger than the string length
     * @return if the string can be palindrome
     */
    public boolean isPalindromeAfterRemovingAtMostKCharacters(String s, int k) {
        int l = s.length();
        // isPalin[i][j][p] = true/false means if
        // s.substring(i, j + 1) can or cannot be palindrome
        // after removing exact k characters
        boolean[][][] isPalindrome = new boolean[l][l][k + 1];

        for (int i = 0; i < l; i++) {
            isPalindrome[i][i][0] = true;
            if (i < l - 1 && s.charAt(i) == s.charAt(i + 1)) {
                isPalindrome[i][i + 1][0] = true;
            }
        }

        for (int p = 0; p <= k; p++) {
            for (int i = l - 1; i >= 0; i--) {
                for (int j = i + 1; j < l; j++) {
                    if (s.charAt(i) == s.charAt(j)) {
                        isPalindrome[i][j][p] = isPalindrome[i][j][p] || isPalindrome[i + 1][j - 1][p];
                    } else if (p > 0) {
                        isPalindrome[i][j][p] = isPalindrome[i][j][p] || isPalindrome[i + 1][j][p - 1]
                                || isPalindrome[i][j - 1][p - 1];
                    }
                }
            }
        }

        for (int p = 0; p <= k; p++) {
            if (isPalindrome[0][l - 1][p]) {
                return true;
            }
        }
        return false;

        // alternatively, we can use Subsequence.longestPalindromeSubsequence()
    }
}
