package linear;

import java.util.LinkedList;
import java.util.Queue;

public class StringCompression {

    /**
     * Compress the char[] to a string, so that reducing each consecutive repeating
     * character
     * to one, and with the number of this character. For example, "aaabcc" compress
     * to "a3bc2"
     * 
     * @param chars char array, containing only lower case letter, with length at
     *              least one
     * @return a string representation of compression
     */
    public String compress(char[] chars) {
        StringBuilder sb = new StringBuilder();
        int consecutiveCount = 0;

        for (char c : chars) {
            if (sb.length() == 0 || sb.charAt(sb.length() - 1) != c) {
                if (consecutiveCount > 1) {
                    sb.append(consecutiveCount);
                }
                sb.append(c);
                consecutiveCount = 1;
            } else {
                consecutiveCount++;
            }
        }
        // post processing
        if (consecutiveCount > 1) {
            sb.append(consecutiveCount);
        }

        return sb.toString();
    }

    /**
     * Decompress the string, extending the consecutive characters
     * 
     * @param s an input string, with only numbers and lower case characters
     * @return decompressed string
     */
    public String deCompress(String s) {
        Queue<Character> queue = new LinkedList<>();
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            if ((c >= 'a' && c <= 'z') || (c >= '0' && c <= '9')) {
                queue.offer(c);
            } else {
                throw new IllegalArgumentException("Input should only contains lower case and numbers");
            }
        }
        queue.offer('A');

        int consecutiveCount = 0;
        char tail = 'A';

        while (!queue.isEmpty()) {
            char c = queue.poll();
            if (c >= '0' && c <= '9') {
                consecutiveCount = 10 * consecutiveCount + (c - '0');
            } else {

                // append that number
                if (consecutiveCount == 0) {
                    if (tail != 'A') {
                        sb.append(tail);
                    }
                } else {
                    for (int i = 0; i < consecutiveCount; i++) {
                        sb.append(tail);
                    }
                }
                tail = c;
                consecutiveCount = 0;
            }
        }

        return sb.toString();
    }

}
