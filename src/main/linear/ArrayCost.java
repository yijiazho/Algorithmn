package utility;

import java.util.Iterator;
import java.util.TreeSet;

// lc 3013
public class ArrayCost {
    // if dist == l, this problem is equivilant to find k smallest
    // this problem is equivalent to
    // finding (k-1) smallest, in a window of length = dist
    // use a treeset to keep a ordered structure
    public long minimumCost(int[] nums, int k, int dist) {
        int l = nums.length;

        TreeSet<Integer> treeSet = new TreeSet<>((i, j) -> nums[i] != nums[j] ? nums[i] - nums[j]: i - j);

        long res = Long.MAX_VALUE;

        for (int i = 1; i <= l; i++) { // O(n)
            // if the window is large enough, try to find res
            // the prev window is [i - dist - 1, i - 1]
            if (i - 1 - dist >= 1) {
                long sum = 0L;
                Iterator<Integer> iterator = treeSet.iterator();
                // loop k - 1 times, and add them together
                for (int j = 0; j < k - 1; j++) { //O(k)
                    int index = iterator.next(); // O(log dist)
                    sum += nums[index];
                }

                // update res if possible
                res = Math.min(res, sum);

                // remove the leftmost one, which is i - dist - 1
                treeSet.remove(i - dist - 1); // O(log dist)
            }

            // add to data structure
            if (i < l) {
                treeSet.add(i); // O(log dist)
            }
        }

        return res + nums[0];
    }


    public static final void main(String[] args) {
        ArrayCost instance = new ArrayCost();
        int[] input = new int[]{10,1,2,2,2,1};

        long res = instance.minimumCost(input, 4, 3);
        System.out.println(res);
    }

}
