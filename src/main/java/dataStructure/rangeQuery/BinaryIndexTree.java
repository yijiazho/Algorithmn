package dataStructure.rangeQuery;

/**
 * Binary Index Tree is a specific segment tree, which keeps the sum between array[0] and array[i]
 * It is best for efficiently range query, and update specific value in the array
 */
public class BinaryIndexTree {

    //parent of bit[x] is bit[y], where y = x - (x & (-x))
    // bit[x] = sum from [y, x)
    int[] bit;
    int[] array;

    public BinaryIndexTree(int[] nums) {
        int size = nums.length;
        bit = new int[size + 1];
        array = new int[size];
        for (int i = 0; i < size; i++) {
            update(i, nums[i]);
            array[i] = nums[i];
        }
    }

    public void update(int index, int val) {
        int target = val - array[index];
        array[index] = val;
        index++;
        while (index < bit.length) {
            bit[index] += target;
            index += index & (-index);
        }
    }

    // return sum from left to right, both inclusive
    public int rangeSum(int left, int right) {
        return query(right + 1) - query(left);
    }

    // return prefix sum from 0 to index, [0, index)
    private int query(int index) {
        int sum = 0;
        while (index > 0) {
            sum += bit[index];
            index -= index & (-index);
        }
        return sum;
    }
}
