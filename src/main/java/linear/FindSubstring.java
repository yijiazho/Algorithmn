package linear;

import java.util.HashMap;
import java.util.Map;

public class FindSubstring {
    public boolean checkInclusion(String s1, String s2) {
        if (s1.length() > s2.length()) {
            return false;
        }

        // build frequency map for s1\
        int numberOfChars = 0;
        Map<Character, Integer> freqMap = new HashMap<>();
        for (int i = 0; i < s1.length(); i++) {
            char c = s1.charAt(i);
            freqMap.put(c, freqMap.getOrDefault(c, 0) + 1);
        }

        int start = 0;
        int end = 0;
        int windowLength = 0;

        // use a easy way to check if all chars are matched

        // substring(start, end) start from empty
        while (end < s2.length()) {
            if (windowLength <= s1.length() && validation(freqMap)) {

                if (windowLength == s1.length()) {
                    return true;
                }
                // consume next char
                char tail = s2.charAt(end);
                freqMap.put(tail, freqMap.getOrDefault(tail, 0) - 1);
                end++;
                windowLength++;
            } else {
                // pop first char
                char head = s2.charAt(start);
                freqMap.put(head, freqMap.getOrDefault(head, 0) + 1);
                start++;
                windowLength--;
            }
        }
        if (windowLength == s1.length() && validation(freqMap)) {
            return true;
        }
        return false;
    }

    private boolean validation(Map<Character, Integer> freqMap) {
        for (Integer frequency: freqMap.values()) {
            if (frequency < 0) {
                return false;
            }
        }

        return true;
    }
}
