package linear;

import java.util.ArrayDeque;
import java.util.Deque;

public class Largest {

    public int largestRectangleArea(int[] heights) {
        /*
        In order to find the largest area, we need to find the boundaries
        of the potential largest rect

        How do we choose left/right boundary?
        Suppose left boundary is fixed, we want the right boundary to be
        1. index as large as possible
        2. value as large as possible
        So for a fixed index i as left boundary, we have 2 possible candidates
        for right boundaries, j1, j2, i < j1 < j2, && height[j1] < height[j2]
        In this case, j1 can never beat j2 to be the right boundary
        So in order to find a right boundary, we need to keep a sorted data structure,
        from left to right descending

        so traverse from right to left, use current index as left boundary, and the possible
        right boundary is in the data structure

        This means if some index j is polled out of this data structure, for the height[j], the
        left boundary is current index, i, the right boundary is the top index in the ds
         */

        int n = heights.length;
        Deque<Integer> rightBoundaryIndex = new ArrayDeque<>();
        rightBoundaryIndex.addLast(n);
        int max = 0;

        for (int i = n - 1; i >= 0; i--) {
            // poll left, until the top is smaller than current
            // and serve as right boundary
            int currentH = getHeight(heights, i);
            while (!rightBoundaryIndex.isEmpty() && getHeight(heights, rightBoundaryIndex.peekFirst()) > currentH) {
                int polledIndex = rightBoundaryIndex.pollFirst();
                // use current index as left boundary, and the top of this data structure as right bound
                int right = rightBoundaryIndex.peekFirst();

                max = Math.max(max, getHeight(heights, polledIndex) * (right - i - 1));
            }

            rightBoundaryIndex.offerFirst(i);

        }

        // left boundary at this point is 0
        while (rightBoundaryIndex.peekFirst() != n) {
            int polledIndex = rightBoundaryIndex.pollFirst();
            int right = rightBoundaryIndex.peekFirst();

            max = Math.max(max, right * heights[polledIndex]);
        }

        return max;
    }

    private int getHeight(int[] heights, int n) {
        if (n < 0 || n >= heights.length) {
            return -1;
        } else {
            return heights[n];
        }
    }

    public static final void main(String[] args) {
        Largest largest = new Largest();
        int[] h = new int[] {12, 5, 5, 5, 12};
        int res = largest.largestRectangleArea(h);
        System.out.println(res);
    }
}