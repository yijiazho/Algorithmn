package linear;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.TreeSet;


public class ArrayCost {
    // lc 3013
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

    // lc 2865
    // min cost to make it mountain shape
    public long maximumSumOfHeights(int[] heights) {
        int l = heights.length;
        // leftCost[i] = k, min cost to make [0, i] non-decreasing is k
        // rightCost[i] = k, min cost to make [i, l - 1] non-increasing
        long[] leftCost = new long[l];
        // long[] rightCost = new long[l];

        leftCost[0] = 0L;
        long sum = heights[0];
        // use a increasing dequeue to keep the index
        Deque<Integer> deque = new ArrayDeque<>();
        deque.addLast(0);
        for (int i = 1; i < l; i++) {
            sum += heights[i];
            if (heights[i] >= heights[i - 1]) {
                leftCost[i] = leftCost[i - 1];
                deque.addLast(i);
            } else {
                // we cannot increase i
                // we can only decrease [0, i - 1]
                // remove from left that larger than it, add to leftCost
                leftCost[i] = leftCost[i - 1];
                while (!deque.isEmpty() && heights[deque.peekLast()] > heights[i]) {
                    // poll last, and add back to it
                    int index = deque.pollLast();
                    int lastIndex = deque.isEmpty() ? -1: deque.peekLast();
                    // (lastIndex, index] is all set to heights[index] now
                    leftCost[i] += (heights[index] - heights[i]) * (index - lastIndex);
                }
                deque.addLast(i);
            }
        }

        // similarly, but we do not have to keep the array for rightCost
        long minCost = leftCost[l - 2];
        long rightCost = 0;
        deque = new ArrayDeque<>();
        deque.addFirst(l - 1);

        for (int i = l - 2; i >= 0; i--) {
            if (heights[i] >= heights[i + 1]) {
              // do not update right cost,
              deque.addFirst(i);
            } else {
                while (!deque.isEmpty() && heights[i] < heights[deque.peekFirst()]) {
                    int index = deque.pollFirst();
                    int lastIndex = deque.isEmpty() ? l : deque.peekFirst();

                    rightCost += (heights[index] - heights[i]) * (lastIndex - index);
                }
                deque.addFirst(i);
            }
            // cost to make [0, i - 1] non-decreasing
            long left = i == 0 ? 0L : leftCost[i - 1];
            minCost = Math.min(minCost, rightCost + left);
        }

        return sum - minCost;
    }


    public static final void main(String[] args) {
        ArrayCost instance = new ArrayCost();
        int[] input = new int[]{5,3,4,1,1};

        long res = instance.maximumSumOfHeights(input);
        System.out.println(res);
    }

}
