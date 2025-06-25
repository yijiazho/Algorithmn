package linear;

public class Substring {

    public int longestSubString(String haystack, String needle) {
        if (needle.isEmpty()) {
            return 0; // Empty needle matches at the beginning.
        }

        int[] lps = computeLPSArray(needle);
        int i = 0; // Index for haystack.
        int j = 0; // Index for needle.

        while (i < haystack.length()) {
            if (haystack.charAt(i) == needle.charAt(j)) {
                i++;
                j++;
                if (j == needle.length()) {
                    return i - j; // Match found.
                }
            } else {
                if (j != 0) {
                    j = lps[j - 1]; // Update j based on the LPS array.
                } else {
                    i++; // No match, move to the next character in haystack.
                }
            }
        }

        return -1; // Needle not found in haystack.
    }

    private int[] computeLPSArray(String needle) {
        int[] lps = new int[needle.length()];
        int len = 0; // Length of the previous longest prefix suffix.

        for (int i = 1; i < needle.length();) {
            if (needle.charAt(i) == needle.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) {
                    len = lps[len - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }

        return lps;
    }

    public int longestCommonSubstring(String a, String b) {
        int m = a.length();
        int n = b.length();

        // l[i][j] means the length of longest substring from
        // a.substring(0, i) and b.substring(0, j), while both
        // a[i - 1] and b[j - 1] are included as the tail
        int[][] longestSubstring = new int[m + 1][n + 1];
        int maxLen = 0;

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    longestSubstring[i][j] = longestSubstring[i - 1][j - 1] + 1;
                    maxLen = Math.max(maxLen, longestSubstring[i][j]);
                } else {
                    longestSubstring[i][j] = 0;
                }
            }
        }
        // can be improved by m * 2
        return maxLen;
    }

    /**
     * Find the frequency of the longest repeating substring, repeating means it
     * must
     * appear more than once in the original string
     * 
     * @param s original string, not null
     * @return the frequency of such substring
     */
    public int longestRepeatingSubstring(String s) {
        int l = s.length();
        int max = 0;

        // longest[i][j] = k means there are k consecutive
        // matches from s[i - 1] and s[j - 1] to their left side
        // both tail must be inclusive
        int[][] longest = new int[l + 1][l + 1];

        for (int i = 1; i <= l; i++) {
            for (int j = i + 1; j <= l; j++) {
                if (s.charAt(i - 1) == s.charAt(j - 1)) {
                    longest[i][j] = longest[i - 1][j - 1] + 1;
                    max = Math.max(max, longest[i][j]);
                }
            }
        }
        return max;
    }
}
