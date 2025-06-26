package linear;

import java.util.Arrays;

public class Palindrome {

    /**
     * Find the shortest palindrome string starting from the original input
     * by adding characters on the beginning only
     * 
     * @param s original input string, must be non empty
     * @return shortest string that is palindrome
     */
    public String shortestPalindrome(String s) {
        int l = s.length();
        if (l == 0) {
            return "";
        }

        // if s.substring(i, j + 1) is palindrome
        boolean[][] isPalindrome = new boolean[l][l];

        // initialize
        for (int i = 0; i < l; i++) {
            isPalindrome[i][i] = true;
        }

        // calculate the boolean[][]
        for (int i = l - 1; i >= 0; i--) {
            for (int j = i + 1; j < l; j++) {
                if (j == i + 1) {
                    isPalindrome[i][j] = s.charAt(i) == s.charAt(j);
                } else {
                    if (s.charAt(i) == s.charAt(j) && isPalindrome[i + 1][j - 1]) {
                        isPalindrome[i][j] = true;
                    }
                }
            }
        }

        // find the longest palindrome from head
        int tail = 0;
        for (int i = 1; i < l; i++) {
            if (isPalindrome[0][i]) {
                tail = i;
            }
        }

        // concat
        String substring = s.substring(tail + 1);
        String reverse = new StringBuilder(substring).reverse().toString();
        return reverse + s;
    }

    /**
     * Find if a string is palindrome if we can remove at most one character in any
     * position
     * 
     * @param s original input string, must be non empty
     * @return if we can make the original string an palindrome one
     */
    public boolean validPalindromeAfterRemoving(String s) {
        int l = s.length();
        // isPalin[i][j][k] means if s.substring(i, j + 1) is palindrome
        // if remove k from this substring
        boolean[][][] isPalin = new boolean[l][l][2];

        for (int i = 0; i < l; i++) {
            isPalin[i][i][0] = true;
        }

        for (int i = 0; i < l - 1; i++) {
            isPalin[i][i + 1][1] = true;
        }

        for (int i = l - 1; i >= 0; i--) {
            for (int j = i + 1; j < l; j++) {
                if (j == i + 1) {
                    isPalin[i][j][0] = s.charAt(i) == s.charAt(j);
                } else {
                    isPalin[i][j][0] = isPalin[i][j][0] ||
                            (isPalin[i + 1][j - 1][0] && s.charAt(i) == s.charAt(j));
                }

                // remove 1, either s[i], s[j] or not remove
                isPalin[i][j][1] = isPalin[i][j][1] || isPalin[i + 1][j][0];
                isPalin[i][j][1] = isPalin[i][j][1] || isPalin[i][j - 1][0];
                isPalin[i][j][1] = isPalin[i][j][1] || (isPalin[i + 1][j - 1][1] && s.charAt(i) == s.charAt(j));
            }
        }

        return isPalin[0][l - 1][0] || isPalin[0][l - 1][1];
    }

    /**
     * Find the minimum cost that makes an int array into a palindrome number,
     * meaning all elements must equal to the same number. The allowed operation is
     * change a value to positive integer,
     * the cost will be the absolute value of the difference between the original
     * value and the changed value
     * 
     * @param nums The original integer array, must be non empty
     * @return total cost making the array palindrome
     */
    public long minimumCost(int[] nums) {
        Arrays.sort(nums);
        int l = nums.length;

        // change to the median will make the total sum minimum
        return palindromeWithMinCost(nums[l / 2], nums);
    }

    public long palindromeWithMinCost(int n, int[] nums) {

        if (isPalindrome(n)) {
            long sum = 0L;
            for (int element : nums) {
                sum += Math.abs(element - n);
            }
            return sum;
        }

        // Find the closest palindromes
        int largerPalindrome = findLargerPalindrome(n);
        int smallerPalindrome = findSmallerPalindrome(n);

        long sum1 = 0L;
        long sum2 = 0L;

        for (int element : nums) {
            sum1 += Math.abs(element - largerPalindrome);
            sum2 += Math.abs(element - smallerPalindrome);
        }

        return Math.min(sum1, sum2);
    }

    private boolean isPalindrome(int n) {
        String s = Integer.toString(n);
        int left = 0, right = s.length() - 1;
        while (left < right) {
            if (s.charAt(left) != s.charAt(right))
                return false;
            left++;
            right--;
        }
        return true;
    }

    private int findLargerPalindrome(int n) {
        String s = Integer.toString(n);
        String half = s.substring(0, (s.length() + 1) / 2);
        String larger = half + new StringBuilder(half.substring(0, s.length() / 2)).reverse().toString();
        int largerPalindrome = Integer.parseInt(larger);
        if (largerPalindrome > n) {
            return largerPalindrome;
        }
        // Handle special case for numbers like 999, where the next palindrome needs
        // extra digit
        larger = Integer.toString(Integer.parseInt(half) + 1);
        larger = larger + new StringBuilder(larger.substring(0, s.length() / 2)).reverse().toString();
        return Integer.parseInt(larger);
    }

    private int findSmallerPalindrome(int n) {
        String s = Integer.toString(n);
        String half = s.substring(0, (s.length() + 1) / 2);
        String smaller = half + new StringBuilder(half.substring(0, s.length() / 2)).reverse().toString();
        int smallerPalindrome = Integer.parseInt(smaller);
        if (smallerPalindrome < n) {
            return smallerPalindrome;
        }
        // Handle special case for numbers like 1000, where the next smaller palindrome
        // has fewer digits
        smaller = Integer.toString(Integer.parseInt(half) - 1);
        smaller = smaller + new StringBuilder(smaller.substring(0, s.length() / 2)).reverse().toString();
        return Integer.parseInt(smaller);
    }
}
