package linear;

import java.util.HashMap;
import java.util.Map;

public class Median {

    /**
     * Return the median of 2 sorted non-null arrays, if the total number is even
     * then return the average of 2 middle numbers
     * 
     * @param n1 sorted array, descending
     * @param n2 sorted array, descending
     * @return the median
     */
    public double findMedianInTwoSortedArrays(int[] n1, int[] n2) {
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
     * Find the median of a n * n matrix. Each row of the matrix is sorted in
     * ascending order. n is odd.
     * 
     * @param matrix a n * n matrix, each row is sorted in ascending order
     * @return the median of the matrix
     */
    public int findMedianInMatrix(int[][] matrix) {
        int n = matrix.length;
        int total = n * n;
        int[] startingIndex = new int[n];
        return removeSmallest(matrix, total / 2, startingIndex);
    }

    private int removeSmallest(int[][] matrix, int toRemove, int[] startingIndex) {
        int n = matrix.length;

        if (toRemove == 0) {
            // find the min value of each leading number
            int min = Integer.MAX_VALUE;
            for (int i = 0; i < n; i++) {
                if (startingIndex[i] < n) {
                    min = Math.min(min, matrix[i][startingIndex[i]]);
                }
            }
            return min;
        }

        if (toRemove < n) {
            // remove the smallest number
            int minIndex = -1;
            int minValue = Integer.MAX_VALUE;
            for (int i = 0; i < n; i++) {
                if (startingIndex[i] < n && matrix[i][startingIndex[i]] < minValue) {
                    minValue = matrix[i][startingIndex[i]];
                    minIndex = i;
                }
            }
            startingIndex[minIndex]++;
            return removeSmallest(matrix, toRemove - 1, startingIndex);
        }

        // try to remove number equals to toRemove / n
        int minIndex = -1;
        int minValue = Integer.MAX_VALUE;
        int removed = -1;
        for (int i = 0; i < n; i++) {
            if (startingIndex[i] >= n) {
                continue;
            }
            int index = startingIndex[i] + toRemove / n - 1;
            if (index >= n) {
                index = n - 1;
            }
            if (matrix[i][index] < minValue) {
                minValue = matrix[i][index];
                minIndex = i;
                removed = index - startingIndex[i] + 1;
            }
        }

        // find the min value and push the starting index to the next one
        startingIndex[minIndex] += removed;
        return removeSmallest(matrix, toRemove - removed, startingIndex);
    }

    /**
     * Count the number of subarrays whose median is exactly k,
     * the median of an array of even length is the smaller median
     * 
     * @param nums an integer array whose value is unique
     * @param k    the target value
     * @return the number of subarrays
     */
    public int numberOfSubarraysWithMedian(int[] nums, int k) {
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
