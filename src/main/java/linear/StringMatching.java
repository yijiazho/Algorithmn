package linear;

import java.util.ArrayList;
import java.util.List;

public class StringMatching {

    public List<Integer> findAllPatterns(String text, String pattern) {
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
}
