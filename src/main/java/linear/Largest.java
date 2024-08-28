package linear;

import java.util.ArrayDeque;
import java.util.Deque;

public class Largest {

    /**
     * Find the largest rect angular of a given array, the input is an
     * array of heights of a list of rectangular bar, with width of 1
     * @param heights heights of the bar
     * @return the max area of rectangular
     */
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

    /**
     * Find max area of containing water in an array of boundaries
     * @param height an array of integers, representing the heights of
     *               boundaries, with no width, not null
     * @return maximum amount of water containing in those boundaries
     * possible
     */
    public int maxArea(int[] height) {
        if (height.length == 0) {
            return 0;
        }

        int left = 0;
        int right = height.length - 1;
        int max = 0;
        int minHeight = Math.min(height[left], height[right]);

        /*
        For each pair of boundaries (left, right), if h[left] <= h[right]
        then the area of pair(left, right - 1) contains less,
        while the pair (left + 1, right) could contain more, so eliminate pair
        (left, right - 1)
         */

        while (left < right) {
            if (height[left] <= height[right]) {
                max = Math.max(max, minHeight * (right - left));
                left++;
                minHeight = Math.min(height[left], height[right]);
            } else {
                max = Math.max(max, minHeight * (right - left));
                right--;
                minHeight = Math.min(height[left], height[right]);
            }
        }
        return max;
    }


    public static final void main(String[] args) {
        Largest largest = new Largest();
        int[] h = new int[] {1, 8, 6, 2, 5, 4, 8, 3, 7};
        int[] h2 = new int[] {1, 5, 6, 3, 7, 2, 5, 7, 6, 5};
        int res = largest.maxArea(h2);
        System.out.println(res);
    }
}