package search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Combinations {

    /**
     * If we can form the sum to the largest value in an array, with each element
     * must be used at most once
     * 
     * @param nums integer array, non empty
     * @return if we can form the largest value as sum of other values
     */
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

    /**
     * Find out all the combinations that adds up to target value with elements in
     * the array, with each element used at most once
     * 
     * @param array  integer array, non empty
     * @param target target sum
     * @return all combinations
     */
    public List<List<Integer>> combinationSum(int[] array, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(array);
        search(result, new ArrayList<>(), array, target, 0, 0);
        return result;
    }

    private void search(List<List<Integer>> result, List<Integer> path, int[] nums, int target, int curIndex,
            int curSum) {
        if (curSum == target) {
            result.add(new ArrayList<>(path));
            return;
        }

        if (curIndex >= nums.length) {
            return;
        }

        for (int i = curIndex; i < nums.length; i++) {
            path.add(nums[i]);
            search(result, path, nums, target, i + 1, curSum + nums[i]);
            path.remove(path.size() - 1);

            // if we skip, we skip all the same value
            while (i < nums.length - 1 && nums[i] == nums[i + 1]) {
                i++;
            }
        }
    }

    /**
     * Find out how many combinations are there if we use the elements in the
     * integer array to add up tp the target as sum, with each element is distinct
     * 
     * @param nums   non empty integer array, each value is positive and distinct
     * @param target the sum to add up to
     * @return total number of possible combinations
     */
    public int combinations(int[] nums, int target) {
        // total possible combos to for target
        int[] possibleCombos = new int[target + 1];
        possibleCombos[0] = 1;

        for (int n : nums) {
            for (int i = n; i <= target; i++) {
                possibleCombos[i] += possibleCombos[i - n];
            }
        }

        return possibleCombos[target];
    }
}
