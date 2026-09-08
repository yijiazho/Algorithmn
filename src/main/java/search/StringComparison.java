package search;

public class StringComparison {

    /**
     * Find out if two encoded string can possibly share the same original string.
     * A string can be encoded by making any number of its substring to the length.
     * For example, internationalization can be encoded to i18n. Yet the encoded
     * i18n can mean 18 characters between i and n, or 1, and 8 characters between.
     *
     * @param s1 Encoded string. non empty. no more than 3 consecutive numbers
     * @param s2 Encoded string. non empty. no more than 3 consecutive numbers
     * @return if it's possible they share the same original string.
     */
    public boolean possiblyEquals(String s1, String s2) {
        Boolean[][][] mem = new Boolean[41][41][2000];
        return match(s1, s2, 0, 0, 0, mem);
    }

    /**
     * Match two encoded string by index, check if the original string can be the same
     * @param s1 Encoded string
     * @param s2 Encoded string
     * @param index1 index of s1
     * @param index2 index of s2
     * @param countDiff the difference between the numbers we face in s1 - s2
     * @param mem cache
     * @return  if it's possible to match s1.substring(0, index1), s2.substring(0, index2)
     */
    private boolean match(String s1, String s2, int index1, int index2,
                          int countDiff, Boolean[][][] mem) {
        // successful match
        if (index1 == s1.length() && index2 == s2.length() && countDiff == 0) {
            return true;
        }
        // failed match: both consumed all characters but still no match
        if ((index1 >= s1.length() && index2 >= s2.length()) && countDiff != 0) {
            return false;
        }
        // all s2 consumed, still some number in s1
        if (countDiff > 0 && index2 >= s2.length()) {
            return false;
        }
        // all s1 consumed, still some number in s2
        if (countDiff < 0 && index1 >= s1.length()) {
            return false;
        }

        // previously checked at this position
        if (mem[index1][index2][countDiff + 1000] != null) {
            return mem[index1][index2][countDiff + 1000];
        }

        boolean res = false;
        if (index1 < s1.length() && Character.isDigit(s1.charAt(index1))) {
            int value = 0;
            int count = 0;
            while (index1 + count < s1.length() && count < 3 && Character.isDigit(s1.charAt(index1 + count))) {
                value = value * 10 + (s1.charAt(index1 + count) - '0');
                count++;
                res = res || match(s1, s2, index1 + count, index2, countDiff + value, mem);
            }
        } else if (index2 < s2.length() && Character.isDigit(s2.charAt(index2))) {
            int value = 0;
            int count = 0;
            while (index2 + count < s2.length() && count < 3 && Character.isDigit(s2.charAt(index2 + count))) {
                value = value * 10 + (s2.charAt(index2 + count) - '0');
                count++;
                res = res || match(s1, s2, index1, index2 + count, countDiff - value, mem);
            }
        } else {
            // match character vs character
            if (countDiff > 0) {
                res = res || match(s1, s2, index1, index2 + 1, countDiff - 1, mem);
            } else if (countDiff < 0) {
                res = res || match(s1, s2, index1 + 1, index2, countDiff + 1, mem);
            } else {
                if (index1 < s1.length() && index2 < s2.length() && s1.charAt(index1) == s2.charAt(index2)) {
                    res = res || match(s1, s2, index1 + 1, index2 + 1, countDiff, mem);
                }
            }
        }

        mem[index1][index2][countDiff + 1000] = res;
        return res;
    }
}
