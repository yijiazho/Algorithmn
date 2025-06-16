package linear;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.TreeSet;

public class Window {

    /**
     * For each index, find the max value in nums[index] to nums[index + k]
     * both inclusively
     * 
     * @param nums int array
     * @param k    non-negative integer, must be smaller or equal than the length
     * @return an array of max values
     */
    public int[] maxInWindow(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k < 0 || k > nums.length) {
            throw new IllegalArgumentException("Invalid input");
        }

        int n = nums.length;
        int[] max = new int[n - k];

        // for fixed j, we have 2 candidate i1, i2,
        // i1 < i2 < j, nums[i1] < nums[i2]
        // in this case, i2 is ALWAYS BETTER THAN i1
        // so we only need to keep decreasing from left to right

        Deque<Integer> indexOfPotentialLargeNumber = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {

            // remove from right to make sure it is decresing
            while (!indexOfPotentialLargeNumber.isEmpty() && nums[indexOfPotentialLargeNumber.peekLast()] <= nums[i]) {
                indexOfPotentialLargeNumber.pollLast();
            }
            indexOfPotentialLargeNumber.offerLast(i);

            // calculate, and poll the first
            if (i >= k) {
                max[i - k] = nums[indexOfPotentialLargeNumber.peekFirst()];
                if (i - indexOfPotentialLargeNumber.peekFirst() >= k) {
                    indexOfPotentialLargeNumber.pollFirst();
                }
            }
        }

        return max;
    }

    /**
     * Find the median in the window for each index and index + k
     * both inclusively
     * 
     * @param nums input int array
     * @param k    the size of step
     * @return the double array of median
     */
    public double[] medianInWindow(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k < 0 || k > nums.length) {
            throw new IllegalArgumentException("Invalid input");
        }

        int n = nums.length;
        double[] median = new double[n - k];
        // use two sorted data strucutre, that able to find min/max
        // and remove by key

        // keep the indicies of smaller half
        TreeSet<Integer> smallSet = new TreeSet<>((k1, k2) -> {
            return nums[k1] == nums[k2] ? k1 - k2 : nums[k1] - nums[k2];
        });

        // keep the indicies of larger half
        TreeSet<Integer> largeSet = new TreeSet<>((k1, k2) -> {
            return nums[k1] == nums[k2] ? k1 - k2 : nums[k1] - nums[k2];
        });

        for (int i = 0; i < n; i++) {
            // add to treeset and keep both balanced
            if (smallSet.isEmpty()) {
                smallSet.add(i);
            } else if (largeSet.isEmpty()) {
                largeSet.add(i);
            } else if (nums[smallSet.last()] >= nums[i]) {
                smallSet.add(i);
            } else {
                largeSet.add(i);
            }

            // rebalance
            while (smallSet.size() - largeSet.size() > 1) {
                largeSet.add(smallSet.pollLast());
            }
            while (largeSet.size() - smallSet.size() > 0) {
                smallSet.add(largeSet.pollFirst());
            }

            if (i >= k) {
                if (k % 2 == 1) {
                    median[i - k] = (nums[smallSet.last()] + nums[largeSet.first()]) / 2.0;
                } else {
                    median[i - k] = nums[smallSet.last()];
                }
                // remove the index i - k
                if (smallSet.contains(i - k)) {
                    smallSet.remove(i - k);
                } else {
                    largeSet.remove(i - k);
                }
            }
        }

        return median;
    }

}
