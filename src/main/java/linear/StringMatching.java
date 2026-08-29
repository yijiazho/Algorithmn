package linear;

import java.util.ArrayList;
import java.util.List;

public class StringMatching {

    public List<Integer> findAllPatternsWithZ(String text, String pattern) {
        String s = pattern + "$" + text;
        int[] z = zFunction(s);
        List<Integer> positions = new ArrayList<>();
        int m = pattern.length();
        for (int i = m + 1; i < z.length; i++) {
            if (z[i] == m) {
                positions.add(i - m - 1);
            }
        }
        return positions;
    }

    public List<Integer> findAllPatternsWithKMP(String text, String pattern) {
        List<Integer> positions = new ArrayList<>();
        int[] lps = lps(pattern);
        int i = 0;
        int j = 0;
        while (i < text.length()) {
            if (text.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
                if (j == pattern.length()) {
                    positions.add(i - j);
                    // check next possible
                    j = lps[j - 1];
                }
            } else {
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }

        }
        return positions;
    }

    /**
     * Generate the z function based on the string of concatenation of pattern + text
     * And returns
     * @param s
     * @return
     */
    private int[] zFunction(String s) {
        int n = s.length();
        int[] z = new int[n];

        // keep a window where [left, right) matches [0, right - left)
        int left = 0;
        int right = 0;

        for (int i = 1; i < n; i++) {
            // we can reuse the window since [left, right) matches [0, right - left)
            // [i, i + p) matches [i - left, i + p - left) by left shift
            // as long as i + p <= right
            // let k = i - left, then [i, i + p) matches [k, k + p)
            // which means z[i] = z[k]
            if (i <= right) {
                z[i] = Math.min(right - i + 1, z[i - left]);
            }

            // if i > right, or i + p > right,
            // everything after right need to be checked manually
            while (i + z[i] < n && s.charAt(z[i]) == s.charAt(i + z[i])) {
                z[i]++;
            }

            // update the window if we expands the right boundary
            // if there is an index j, j > i, that can benefit from previous window
            // it must also benefit from the new window because
            // [i, previousRight] is a subset of [i, newRight], newRight = i + z[i] - 1
            // and the beneficial part cannot exceed the right boundary
            if (i + z[i] - 1 > right) {
                left = i;
                right = i + z[i] - 1;
            }
        }
        return z;
    }

    /**
     * Calculate the longest prefix which is also a suffix as an array
     * @param pattern The pattern string to be matched
     * @return lps array
     */
    private int[] lps(String pattern) {
        int n = pattern.length();
        int[] lps = new int[n];
        // position we are calculating
        int i = 1;
        // length of current match
        int j = 0;

        while (i < n) {
            // match s[i] and s[len]
            if (pattern.charAt(i) == pattern.charAt(j)) {
                j++;
                lps[i] = j;
                i++;
            } else {
                // [0, lps[j - 1]) are matched, so the next to compare is lps[j - 1]
                if (j != 0) {
                j = lps[j - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }
        return lps;
    }
}
