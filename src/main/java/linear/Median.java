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

    /**
     * Return the median of 2 sorted non-null arrays, if the total number is even
     * then return the average of 2 middle numbers
     * @param n1 sorted array, descending
     * @param n2 sorted array, descending
     * @return the median
     */
    public double findMedian(int[] n1, int[] n2) {
        int l1 = n1.length;
        int l2 = n2.length;
        int l = l1 + l2;
        if (l % 2 == 0) {
            return (smallest(n1, n2, l / 2 - 1, 0, 0) + smallest(n1, n2, l / 2, 0, 0)) / 2.0;
        } else {
            return smallest(n1, n2, l / 2, 0, 0) / 1.0;
        }
    }

    /**
     * Return the smallest after removing toRemove smallest numbers in 2 arrays combined
     * @param n1 sorted array
     * @param n2 sorted array
     * @param toRemove number of integers to remove before finding smallest
     * @param l1 starting index in n1, inclusive
     * @param l2 starting index in n2, inclusive
     * @return the (toRemove + 1) smallest value
     */
    private int smallest(int[] n1, int[] n2, int toRemove, int l1, int l2) {
        // corner cases
        if (l1 >= n1.length) {
            return n2[toRemove + l2];
        }
        if (l2 >= n2.length) {
            return n1[toRemove + l1];
        }

        if (toRemove == 0) {
            return Math.min(n1[l1], n2[l2]);
        }
        if (toRemove == 1) {
            return n1[l1] < n2[l2] ?
                    smallest(n1, n2, 0, l1 + 1, l2) :
                    smallest(n1, n2, 0, l1, l2 + 1);
        }

        int index1 = l1 + toRemove / 2 - 1;
        int index2 = l2 + toRemove - toRemove / 2 - 1;

        int val1 = index1 < n1.length ? n1[index1] : Integer.MAX_VALUE;
        int val2 = index2 < n2.length ? n2[index2] : Integer.MAX_VALUE;

        // remove half
        if (val1 < val2) {
            // remove [l1, index]
            return smallest(n1, n2, toRemove - toRemove / 2, index1 + 1, l2);
        } else {
            return smallest(n1, n2, toRemove / 2, l1, index2 + 1);
        }
    }

    public static final void main(String[] args) {
        int[] n1 = new int[] {1,2,3,7,9};
        int[] n2 = new int[] {3,4};

        Median median = new Median();
        double res = median.findMedian(n1, n2);
        System.out.println(res);
    }
}
