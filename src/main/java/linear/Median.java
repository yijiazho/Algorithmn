package linear;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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

    /**
     * Return the median of 2 sorted non-null arrays, if the total number is even
     * then return the average of 2 middle numbers
     * 
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
     * Return the smallest after removing toRemove smallest numbers in 2 arrays
     * combined
     * 
     * @param n1       sorted array
     * @param n2       sorted array
     * @param toRemove number of integers to remove before finding smallest
     * @param l1       starting index in n1, inclusive
     * @param l2       starting index in n2, inclusive
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
            return n1[l1] < n2[l2] ? smallest(n1, n2, 0, l1 + 1, l2) : smallest(n1, n2, 0, l1, l2 + 1);
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

    /**
     * Count the number of subarrays whose median is exactly k,
     * the median of an array of even length is the smaller median
     * 
     * @param nums an integer array whose value is unique
     * @param k    the target value
     * @return the number of subarrays
     */
    public int countSubarrays(int[] nums, int k) {
        // The goal is to find an i, j pair, where i <= kIndex <= j
        // as left and right boundary of subarray respectively,
        // both inclusively
        // and make sure smaller number is half of the total size

        // smaller[i] means how many numbers on its left side
        // is smaller than k
        int[] smaller = new int[nums.length + 1];
        int kIndex = -1;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == k) {
                kIndex = i;
            }
            smaller[i + 1] = smaller[i] + (nums[i] < k ? 1 : 0);
        }

        int count = 0;
        for (int i = 0; i <= kIndex; i++) {
            for (int j = kIndex; j < nums.length; j++) {
                // use i, j as boundary
                if ((j - i) / 2 == smaller[j + 1] - smaller[i]) {
                    count++;
                }
            }
        }
        return count;
    }

    public int countFromMedian(int[] nums, int k) {

        // ALternatively, we can count in the middle from k
        // for left side, count how many numbers are smaller than k
        // from current index to kIndex, inclusively
        // for right side, count how many numbers are larger than k
        // from kIndex to current index, inclusively
        int kIndex = -1;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == k) {
                kIndex = i;
                break;
            }
        }

        // key is the difference between larger and smaller numbers, from kIndex to
        // current index
        // or from current index to kIndex
        // value is the number of such pairs with the same difference
        Map<Integer, Integer> differenceFrequencyLeft = new HashMap<>();
        Map<Integer, Integer> differenceFrequencyRight = new HashMap<>();

        int differenceBetweenLargerAndSmaller = 0;
        // for kIndex, we have 0 difference, kIndex can be the left boundary or right
        // boundary
        differenceFrequencyLeft.put(0, 1);
        for (int i = kIndex - 1; i >= 0; i--) {
            if (nums[i] < k) {
                differenceBetweenLargerAndSmaller--;
            } else {
                differenceBetweenLargerAndSmaller++;
            }
            differenceFrequencyLeft.put(differenceBetweenLargerAndSmaller,
                    differenceFrequencyLeft.getOrDefault(differenceBetweenLargerAndSmaller, 0) + 1);
        }

        differenceBetweenLargerAndSmaller = 0;
        differenceFrequencyRight.put(0, 1);
        for (int i = kIndex + 1; i < nums.length; i++) {
            if (nums[i] < k) {
                differenceBetweenLargerAndSmaller--;
            } else {
                differenceBetweenLargerAndSmaller++;
            }
            differenceFrequencyRight.put(differenceBetweenLargerAndSmaller,
                    differenceFrequencyRight.getOrDefault(differenceBetweenLargerAndSmaller, 0) + 1);
        }

        // Now let's loop the map entry set
        // if we have a difference of -1, whose frequency is a,
        // then we can find a pair with difference of 1, whose frequency is b,
        // then we can form a subarray with median k, a * b times
        int count = 0;

        for (Map.Entry<Integer, Integer> entry : differenceFrequencyLeft.entrySet()) {
            int difference = entry.getKey();
            int frequency = entry.getValue();

            if (differenceFrequencyRight.containsKey(-difference)) {
                count += frequency * differenceFrequencyRight.get(-difference);
            }
            if (differenceFrequencyRight.containsKey(-difference + 1)) {
                count += frequency * differenceFrequencyRight.get(-difference + 1);
            }
        }

        return count;
    }
}
