package window;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Window {


    public static final void main(String[] args) {
        String s = "ADOBECODEBANC";
        String t = "ABC";

        Window instance = new Window();

        String res = instance.minWindow(s, t);
        System.out.println(res);
    }

    public String minWindow(String s, String t) {
        // use a set and count to quickly track the string t
        Set<Character> set = new HashSet<>();
        int count = 0;
        for (int i = 0; i < t.length(); i++) {
            char c = t.charAt(i);
            if (!set.contains(c)) {
                count++;
                set.add(c);
            }
        }

        // use a map to track the window
        Map<Character, Integer> map = new HashMap<>();
        int left = 0;
        int right = 0;
        int curCount = 1;
        map.put(s.charAt(0), 1);
        int m = s.length();
        int min = Integer.MAX_VALUE;
        String res = s.substring(0, 1);

        while (left < m && right < m) {
            if (curCount < count) {
                right++;
                if (right >= m) {
                    break;
                }
                char c = s.charAt(right);
                if (set.contains(c) && map.getOrDefault(c, 0) == 0) {
                    curCount++;
                }
                map.put(c, map.getOrDefault(c, 0) + 1);

            } else {
                while (curCount == count) {
                    if (right - left + 1 < min) {
                        min = right - left + 1;
                        res = s.substring(left, right + 1);
                    }
                    // shrink by left ++
                    char c = s.charAt(left);
                    map.put(c, map.get(c) - 1);
                    if (map.get(c) == 0 && set.contains(c)) {
                        curCount--;
                    }
                    left++;
                }
            }
        }

        return res;
    }
}
