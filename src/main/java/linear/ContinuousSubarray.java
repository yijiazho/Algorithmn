package linear;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public class ContinuousSubarray {

    /**
     * Find if there is a sub array in nums that,
     * 1, with length at least two, and 2. the sum is
     * multiple of k
     * 
     * @param nums original array, [0, Integer.MAX]
     * @param k    integer in [1, Integer.MAX]
     * @return if it contains such sub array
     */
    public boolean checkSubarraySum(int[] nums, int k) {
        int l = nums.length;

        // long[] prefix = new long[l + 1];
        long prefixMod = 0L;

        // key is the prefix sum % k, value is index
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 0);

        // prefix[i] is sum [0, i)
        for (int i = 0; i < l; i++) {
            prefixMod = (nums[i] + prefixMod) % k;
            if (map.containsKey((int) prefixMod)) {
                if (i + 1 - map.get((int) prefixMod) > 1) {
                    return true;
                }
            } else {
                map.put((int) prefixMod, i + 1);
            }

        }

        return false;
    }

    /**
     * Find out the length of the shortest subarray that, if we need to sort the
     * entire array, we only need to sort this subarray. If the array is already
     * sorted, return 0.
     * 
     * @param nums origianl int array
     * @return the length of the shortest subarray to be sorted
     */
    public int findUnsortedSubarray(int[] nums) {
        // the sorted subarray is defined by the left and right boundary
        // result is right - left + 1

        // for each index we find the right most index that is smaller than the current
        // index, and the largest value among all indices is the right boundary, while
        // the left boundary is the smallest index that is larger than any index on the
        // right side

        int l = nums.length;
        // for each index, find the right most index that is smaller
        TreeSet<Integer> rightIndexMap = new TreeSet<>((i, j) -> {
            if (nums[i] == nums[j]) {
                return i - j;
            } else {
                return nums[i] - nums[j];
            }
        });

        int rightBoundary = -1;
        int leftBoundary = l;
        for (int i = l - 1; i >= 0; i--) {
            Integer rightMostIndex = rightIndexMap.lower(i);
            if (rightMostIndex != null) {
                rightBoundary = Math.max(rightBoundary, rightMostIndex);
                leftBoundary = Math.min(leftBoundary, i);
            }
            rightIndexMap.add(i);
        }

        return rightBoundary >= leftBoundary ? rightBoundary - leftBoundary + 1 : 0;
    }

}
