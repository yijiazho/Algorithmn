package linear;

import java.util.Arrays;

public class PalindromeNumber {
    /**
     * Count the number of binary palindromes from 0 to n, inclusive
     * 
     * @param n non negative integer
     * @return the number of binary palindromes from 0 to n, inclusive
     */
    public int countNumberOfBinaryPalindromes(int n) {
        if (n <= 0) {
            return 0;
        }
        int count = 0;
        for (int i = 0; i <= n; i++) {
            if (isBinaryPalindrome(i)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Find the minimum cost that makes an int array into a palindrome number,
     * The allowed operation is changing a value to positive integer,
     * the cost will be the absolute value of the difference between the original
     * value and the changed value
     * 
     * @param nums The original integer array, must be non empty
     * @return total cost making the array palindrome
     */
    public long minimumCostToMakePalindrome(int[] nums) {
        Arrays.sort(nums);
        int l = nums.length;

        // change to the median will make the total sum minimum
        return palindromeWithMinCost(nums[l / 2], nums);
    }

    /**
     * Find out the minimum cost to make an integer to a palindrome integer, the
     * cost is defined by the sum of absolute difference between the palindrome
     * number and the number in the integer array
     * 
     * @param n     original integer to make it palindrome
     * @param costs the cost to calculate based on
     * @return total cost based on the costs array
     */
    public long palindromeWithMinCost(int n, int[] costs) {

        if (isPalindromeInteger(n)) {
            long sum = 0L;
            for (int element : costs) {
                sum += Math.abs(element - n);
            }
            return sum;
        }

        // Find the closest palindromes
        int largerPalindrome = findLargerPalindrome(n);
        int smallerPalindrome = findSmallerPalindrome(n);

        long sum1 = 0L;
        long sum2 = 0L;

        for (int element : costs) {
            sum1 += Math.abs(element - largerPalindrome);
            sum2 += Math.abs(element - smallerPalindrome);
        }

        return Math.min(sum1, sum2);
    }

    /**
     * For each index, find out the kth smallest palindrome number with the given
     * length, where k is the value of queries[i]. If the kth palindrome number does
     * not exist, return -1.
     * 
     * @param queries
     * @param intLength
     * @return
     */
    public long[] kthPalindrome(int[] queries, int intLength) {
        // Consider the first half of the palindrome. The first digit can be from 1 to
        // 9, and other digits can be from 0 to 9. The min is 10 and the max is
        // 10 ^ ((intLength + 1) / 2) - 1
        long[] result = new long[queries.length];
        long start = (long) Math.pow(10, (intLength - 1) / 2);
        long end = (long) Math.pow(10, (intLength + 1) / 2) - 1;

        for (int i = 0; i < queries.length; i++) {
            long k = queries[i];
            if (k > end - start + 1) {
                result[i] = -1;
            } else {
                long half = start + k - 1;
                String halfStr = Long.toString(half);
                String palindromeStr;
                if (intLength % 2 == 0) {
                    palindromeStr = halfStr + new StringBuilder(halfStr).reverse().toString();
                } else {
                    palindromeStr = halfStr
                            + new StringBuilder(halfStr.substring(0, halfStr.length() - 1)).reverse().toString();
                }
                result[i] = Long.parseLong(palindromeStr);
            }
        }

        return result;
    }

    private boolean isBinaryPalindrome(int n) {
        String binaryString = Integer.toBinaryString(n);
        int left = 0, right = binaryString.length() - 1;
        while (left < right) {
            if (binaryString.charAt(left) != binaryString.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    private boolean isPalindromeInteger(int n) {
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
