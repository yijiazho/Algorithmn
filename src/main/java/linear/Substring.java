package linear;

import java.util.ArrayList;
import java.util.List;

public class Substring {

    /**
     * Find the first occurrence of needle in haystack, return the index of the
     * first character of the first occurrence, or -1 if needle is not part of
     * haystack
     * 
     * @param haystack the original string, not null
     * @param needle   the substring to find, not null
     * @return the index of the first character of the first occurrence, or -1 if
     *         needle is not part of haystack
     */
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

    /*
     * Find the subString of length k, whose hashValue is equal to given hashValue
     */
    public String subStringHash(String s, int power, int mod, int k, int hashValue) {
        // use a sliding window, when we move the window the sum becomes
        // sum = ((sum - toRemove * power ^ 0) / power) + toAdd * power ^ (k - 1)

        long currentHash = getHashValue(s.substring(0, k), power);
        if (currentHash % mod == hashValue) {
            return s.substring(0, k);
        }

        for (int i = 0; i < s.length() - k; i++) {
            // remove s[i] and add s[i + k]
            int toRemove = s.charAt(i) - 'a' + 1;
            int toAdd = s.charAt(i + k) - 'a' + 1;
            currentHash = nextHash(currentHash, power, toAdd, toRemove, k);

            if (currentHash % mod == hashValue) {
                return s.substring(i + 1, k);
            }
        }
        return null;
    }

    public long getHashValue(String s, int power) {
        long sum = 0;

        for (int i = s.length() - 1; i >= 0; i--) {
            char c = s.charAt(i);
            int value = c - 'a' + 1;
            sum = sum * power + value;
        }

        return sum;
    }

    private long nextHash(long original, int power, int toAdd, int toRemove, int k) {
        long sum = original;
        sum = (sum - toRemove) / power + toAdd * (int) Math.pow(power, k - 1);
        return sum;
    }

    /**
     * Find the length of longest substring of s, that contains at most k distinct
     * characters
     * 
     * @param s the original input string, must be non empty, only contains lower
     *          case
     * @param k positive integer
     * @return the max length of the substring with at most k distnct characters
     */
    public int longestSubstringKDistinct(String s, int k) {
        // keeps expending until we reaches a point where there are more distinct
        // characters in the window
        int max = 0;
        int left = 0;
        int right = 0;
        int[] window = new int[26];
        int countInWindow = 0;

        while (left < s.length() && right < s.length()) {
            // start at valid state
            char c = s.charAt(right);
            window[c - 'a']++;
            if (window[c - 'a'] == 1) {
                countInWindow++;
            }

            while (countInWindow > k) {
                // while not valid, pop the left most
                char head = s.charAt(left);
                window[head - 'a']--;
                if (window[head - 'a'] == 0) {
                    countInWindow--;
                }
                left++;
            }
            // still valid state here
            max = Math.max(max, right - left + 1);
            right++;

        }
        return max;
    }

    /**
     * Find a list of string from an string array, which contains all characters in
     * the target array,
     * the frequencey should also match
     * 
     * @param s      original string array, as the candidates, must be non empty
     *               array
     * @param target target string array, each contains some characters
     * @return a list of string that matches the target
     */
    public List<String> wordSubsets(String[] s, String[] target) {
        // use a map to represent each character, frequency in target array
        // and loop the original string to check if it's all there

        int[] commonMap = new int[26];
        for (String t : target) {
            int[] curMap = new int[26];

            for (char c : t.toCharArray()) {
                curMap[c - 'a']++;
            }

            // merge into common map
            for (int i = 0; i < 26; i++) {
                commonMap[i] = Math.max(commonMap[i], curMap[i]);
            }
        }

        List<String> result = new ArrayList<>();
        for (String candidate : s) {
            boolean valid = true;
            int[] curMap = new int[26];
            for (char c : candidate.toCharArray()) {
                curMap[c - 'a']++;
            }

            // validate
            for (int i = 0; i < 26; i++) {
                if (commonMap[i] > curMap[i]) {
                    valid = false;
                    break;
                }
            }
            if (valid) {
                result.add(candidate);
            }
        }
        return result;
    }

    /**
     * Find the substring with largest lexicographical order
     * 
     * @param s the original string, non empty
     * @return the largest lexicographical order substring
     */
    public String lastSubstring(String s) {
        // Corollary 1, the substring must end in tail.
        // if not, we simply add one more character and it will be larger

        // the best candidate so far
        int i = 0;
        // the next candidate
        int j = 1;
        // steps we compare
        int k = 0;

        while (j + k < s.length()) {
            if (s.charAt(i + k) == s.charAt(j + k)) {
                k++;
            } else if (s.charAt(i + k) > s.charAt(j + k)) {
                // i is still better
                k = 0;
                // we can safely skip because any starting point j + p, where j < j + p < j + k
                // this candidate will be worse than i + p, as we already compared that
                j = j + k + 1;
            } else {
                k = 0;
                i = i + k + 1;
            }
            if (i == j) {
                j++;
            }
        }

        return s.substring(i);
    }

    /**
     * Find the total cost of the array. For each subarray, the cost is defined as
     * the product of the total sum and the minimum value of the subarray. mod by
     * 10^9 + 7
     * 
     * @param nums the input array, non null, non empty
     * @return the total cost of all subarrays, positive integer
     */
    public int totalCosts(int[] nums) {
        int mod = 1_000_000_007;
        // For each left, right boundary, we need to find the sum of subarray, and the
        // minimum value in the subarray, preferably in O(1)

        // Alternatively, we for each index i, we can find the left and right boundary
        // where nums[i] is the minimum value. If the closest smaller value on the left
        // is at index l, then left boundary can be any index from l + 1 to i.
        // Similarly, if the closest smaller or equal value on the right is at index r,
        // then right boundary can be any index from i to r - 1.

        // prefixSum[i] = sum of nums[0..i - 1], prefixSum[0] = 0
        int[] prefixSum = new int[nums.length + 1];
        for (int i = 0; i < nums.length; i++) {
            prefixSum[i + 1] = prefixSum[i] + nums[i];
        }

        // minValue[i][j] = min of nums[i..j], i <= j
        int[][] minValue = new int[nums.length][nums.length];
        for (int i = 0; i < nums.length; i++) {
            minValue[i][i] = nums[i];
            for (int j = i + 1; j < nums.length; j++) {
                minValue[i][j] = Math.min(minValue[i][j - 1], nums[j]);
            }
        }

        long totalCost = 0;
        for (int i = 0; i < nums.length; i++) {
            for (int j = i; j < nums.length; j++) {
                long sum = prefixSum[j + 1] - prefixSum[i];
                long min = minValue[i][j];
                totalCost = (totalCost + sum * min) % mod;
            }
        }

        return (int) totalCost;
    }
}
