package linear;

import java.util.ArrayDeque;
import java.util.Deque;

public class Histogram {

    /**
     * Find the largest rectangular size in histogram, with width of 1
     * @param heights height at each position
     * @return the max area
     */
    public int largestRectangleArea(int[] heights) {
        // at each position i, the max area of rectangular with height heights[i] is
        // (right - left - 1) * heights[i], where left is the index of the first height
        // smaller than heights[i] to the left, and the right is the first index whose
        // height smaller than heights[i] to the right.

        // this problem is equivalent to find the first smaller on left and first smaller on right
        // which can be done in O(n)

        if (heights == null || heights.length == 0) {
            return 0;
        }

        // find an array that represents first smaller to the left
        int[] firstSmallerToLeft = new int[heights.length];
        Deque<Integer> potentialSmallerIndexToLeft = new ArrayDeque<>();
        for (int i = 0; i < heights.length; i++) {
            while (!potentialSmallerIndexToLeft.isEmpty() && heights[potentialSmallerIndexToLeft.peekLast()] >= heights[i]) {
                potentialSmallerIndexToLeft.pollLast();
            }

            firstSmallerToLeft[i] = potentialSmallerIndexToLeft.isEmpty() ? -1 : potentialSmallerIndexToLeft.peekLast();
            potentialSmallerIndexToLeft.offerLast(i);
        }

        // second loop from right to left, find the first smaller to the right
        // and update the potential max area
        int max = 0;
        int firstSmallerToRight;
        Deque<Integer> potentialSmallerIndexToRight = new ArrayDeque<>();
        for (int i = heights.length - 1; i >= 0; i--) {
            while (!potentialSmallerIndexToRight.isEmpty() && heights[potentialSmallerIndexToRight.peekLast()] >= heights[i]) {
                potentialSmallerIndexToRight.pollLast();
            }

            firstSmallerToRight = potentialSmallerIndexToRight.isEmpty() ? heights.length : potentialSmallerIndexToRight.peekLast();
            potentialSmallerIndexToRight.offerLast(i);

            max = Math.max(max, heights[i] * (firstSmallerToRight - firstSmallerToLeft[i] - 1));
        }


        return max;
    }
}
