package search;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Combination {

    public boolean findSum(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return false;
        }
        int l = nums.length;

        // find max
        int maxIndex = 0;
        for (int i = 1; i < l; i++) {
            if (nums[i] > nums[maxIndex]) {
                maxIndex = i;
            }
        }

        int maxValue = nums[maxIndex];

        // dp.get(i) contains a set of possible sums, using values from 0 to i,
        // both inclusively
        List<Set<Integer>> validIntegerAndSumCombination = new ArrayList<>();
        for (int i = 0; i < l; i++) {
            validIntegerAndSumCombination.add(new HashSet<>());
        }
        Set<Integer> firstSum = validIntegerAndSumCombination.get(0);
        firstSum.add(0);
        firstSum.add(nums[0]);

        for (int i = 1; i < l; i++) {
            Set<Integer> sumSetAtIndex = validIntegerAndSumCombination.get(i);

            if (i == maxIndex) {
                sumSetAtIndex.addAll(validIntegerAndSumCombination.get(i - 1));
            } else {
                for (int possibleSumAtLastIndex : validIntegerAndSumCombination.get(i - 1)) {
                    sumSetAtIndex.add(possibleSumAtLastIndex);
                    sumSetAtIndex.add(possibleSumAtLastIndex + nums[i]);
                }
            }
        }

        return validIntegerAndSumCombination.get(l - 1).contains(maxValue);
    }

}
