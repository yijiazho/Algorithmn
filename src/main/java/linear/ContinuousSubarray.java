package linear;

import java.util.HashMap;
import java.util.Map;

public class ContinuousSubarray {

    /**
     * Find if there is a sub array in nums that,
     * 1, with length at least two, and 2. the sum is
     * multiple of k
     * @param nums original array, [0, Integer.MAX]
     * @param k integer in [1, Integer.MAX]
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

    public static void main(String[] args) {
        ContinuousSubarray continuousSubarray = new ContinuousSubarray();
        int[] nums = new int[] {23, 2, 6, 4, 7};
        boolean res = continuousSubarray.checkSubarraySum(nums, 13);

        System.out.println(res);
    }
}
