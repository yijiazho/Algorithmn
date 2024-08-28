package dataStructure;

import java.util.HashMap;
import java.util.Map;

// Prefer using array for CPU's optimization for vectors
class SparseVector {
    // key and value are the index and values for non zeros values
    private Map<Integer, Integer> nonZeroPairs;

    SparseVector(int[] nums) {
        nonZeroPairs = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                nonZeroPairs.put(i, nums[i]);
            }
        }
    }

    // Return the dotProduct of two sparse vectors
    public int dotProduct(SparseVector that) {
        int sum = 0;
        for (Map.Entry<Integer, Integer> entry: nonZeroPairs.entrySet()) {
            Map<Integer, Integer> thatMap = that.getNonZeroPairs();
            if (thatMap.containsKey(entry.getKey())) {
                sum += entry.getValue() * thatMap.get(entry.getKey());
            }
        }
        return sum;
    }

    public Map<Integer, Integer> getNonZeroPairs() {
        return this.nonZeroPairs;
    }
}