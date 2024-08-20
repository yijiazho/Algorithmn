package linear;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Consecutive {

    /**
     * Find the longest '1's in the nums array, given
     * flipping at most k '0' to '1'
     * @param nums original array, containing only 0 and 1
     * @param k max flips
     * @return length of longest consecutive '1's in array
     */
    public int longestOnes(int[] nums, int k) {
        // loop through the array with sliding window
        // n.substring(start, end)
        int start = 0;
        int end = 0;
        int zeros = 0;
        int maxLen = 0;

        while (end <= nums.length) {
            if (zeros <= k) {
                // update and expand
                maxLen = Math.max(maxLen, end - start);
                // add nums[end]
                if (end < nums.length && nums[end] == 0) {
                    zeros++;
                }
                end++;

            } else {
                // shrink by removing nums[start]
                if (nums[start] == 0) {
                    zeros--;
                }
                start++;
            }
        }

        // post processing

        return maxLen;
    }

    /**
     * Return the min window substring in s, where it contains
     * all chars in t, including duplicates. The substring can
     * contain more
     * @param s original string, NotNull
     * @param t match string, NotNull
     * @return the minimum length of the window
     */
    public String minWindow(String s, String t) {
        if (s == null || t == null) {
            throw new IllegalArgumentException("The input cannot be null");
        }

        // build the match map
        Map<Character, Integer> matchMap = new HashMap<>();
        int matchTarget = 0;
        for (int i = 0; i < t.length(); i++) {
            char key = t.charAt(i);
            if (!matchMap.containsKey(key)) {
                matchMap.put(key, 1);
                matchTarget++;
            } else {
                matchMap.put(key, matchMap.get(key) + 1);
            }
        }

        // s.substring(left, right) starting at ""
        int left = 0;
        int right = 0;
        int match = 0;
        int minLen = Integer.MAX_VALUE;
        String minSubstring = "";
        Map<Character, Integer> map = new HashMap<>();

        // loop through the string in a window
        while (right <= s.length()) {
            // validation check
            if (match == matchTarget) {
                // as long as valid, shrink on the left
                while (match == matchTarget) {
                    char head = s.charAt(left);
                    map.put(head, map.getOrDefault(head, 0) - 1);
                    left++;

                    // head is in the string t, but less than the frequency required
                    if (matchMap.containsKey(head) && matchMap.getOrDefault(head, 0) > map.getOrDefault(head, 0)) {
                        match--;
                    }
                }

                if (right - left + 1 < minLen) {
                    minLen = right - left + 1;
                    minSubstring = s.substring(left - 1, right);
                }

            } else {
                // expand by one
                if (right < s.length()) {
                    char tail = s.charAt(right);
                    map.put(tail, map.getOrDefault(tail, 0) + 1);

                    // tail is in string i and have the same frequency
                    if (matchMap.getOrDefault(tail, 0) == map.getOrDefault(tail, 0)) {
                        match++;
                    }
                }
                right++;
            }

        }
        return minSubstring;
    }


    public static final void main(String[] args) {
        Consecutive instance = new Consecutive();
        String s = "aaaaaaaaaaaabbbbbcdd";
        String p = "abcdd";

        String res = instance.minWindow(s, p);
        System.out.println(res);
    }
}
