package linear;

public class KMP {

    public int strStr(String haystack, String needle) {
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

        for (int i = 1; i < needle.length(); ) {
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

    public static void main(String[] args) {
        KMP kmp = new KMP();


        String haystack = "hello";
        String needle = "ll";
        int index = kmp.strStr(haystack, needle);
        System.out.println(kmp.computeLPSArray("aabaac"));
    }
}
