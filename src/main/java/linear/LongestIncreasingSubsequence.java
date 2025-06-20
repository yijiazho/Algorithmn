package linear;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class LongestIncreasingSubsequence {

    public List<Integer> longestIncreasingSubsequence(int[] nums) {
        // Keep a list of potential longest increasing subsequence
        // if new element is larger than last element, append to tail
        // else, insert and replace the first element larger or equal new
        // new element, because the new element has more potential

        List<Integer> indexOfPotentialIncreasingElements = new ArrayList<>();
        Map<Integer, Integer> longestMapIndexReverse = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            if (indexOfPotentialIncreasingElements.isEmpty()) {
                // this is the start of longest
                longestMapIndexReverse.put(i, -1);
                indexOfPotentialIncreasingElements.add(i);

            } else if (nums[i] > nums[indexOfPotentialIncreasingElements
                    .get(indexOfPotentialIncreasingElements.size() - 1)]) {
                // we can append to the current tail because this element is larger than the
                // tail
                longestMapIndexReverse.put(i,
                        indexOfPotentialIncreasingElements.get(indexOfPotentialIncreasingElements.size() - 1));
                indexOfPotentialIncreasingElements.add(i);

            } else {
                // find the first element in this list that is larger or equal to given element
                // and replace
                int left = 0;
                int right = indexOfPotentialIncreasingElements.size() - 1;
                while (left <= right) {
                    int mid = left + (right - left) / 2;
                    if (nums[indexOfPotentialIncreasingElements.get(mid)] >= nums[i]) {
                        right = mid - 1;
                    } else {
                        left = mid + 1;
                    }
                }
                indexOfPotentialIncreasingElements.set(left, i);
                if (left == 0) {
                    longestMapIndexReverse.put(i, -1);
                } else {
                    longestMapIndexReverse.put(i, longestMapIndexReverse.get(left - 1));
                }
            }
        }

        // build the longest list
        List<Integer> longest = new LinkedList<>();
        int tailIndex = indexOfPotentialIncreasingElements.get(indexOfPotentialIncreasingElements.size() - 1);
        while (tailIndex != -1) {
            longest.add(0, nums[tailIndex]);
            tailIndex = longestMapIndexReverse.get(tailIndex);
        }

        return longest;
    }
}