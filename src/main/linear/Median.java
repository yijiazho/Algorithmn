package linear;

import java.util.Arrays;

public class Median {

    // lc 2967
    // 1 find median
    // 2 find palindrome closest to target median
    public long minimumCost(int[] nums) {
        Arrays.sort(nums);
        int l = nums.length;

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

        for (int element: nums) {
            sum1 += Math.abs(element - largerPalindrome);
            sum2 += Math.abs(element - smallerPalindrome);
        }

        return Math.min(sum1, sum2);
    }

    private boolean isPalindrome(int n) {
        String s = Integer.toString(n);
        int left = 0, right = s.length() - 1;
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) return false;
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
        // Handle special case for numbers like 999, where the next palindrome needs extra digit
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
        // Handle special case for numbers like 1000, where the next smaller palindrome has fewer digits
        smaller = Integer.toString(Integer.parseInt(half) - 1);
        smaller = smaller + new StringBuilder(smaller.substring(0, s.length() / 2)).reverse().toString();
        return Integer.parseInt(smaller);
    }
}
