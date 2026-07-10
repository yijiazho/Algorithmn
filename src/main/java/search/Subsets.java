package search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Subsets {

    /**
     * Find out how many subsets can be formed from the integer array that adds up
     * to the target value. Each element can be used at most once.
     * 
     * @param nums   non empty integer array, each value is positive
     * @param target the sum to add up to
     * @return total number of possible subsets
     */
    public int numberOfSubsets(int[] nums, int target) {
        int n = nums.length;
        // numberOfSubsets[i][j] represents the number of subsets that can be formed
        // using the first i elements of nums that sum up to j. The result will be
        // stored in numberOfSubsets[n][target].
        int[][] numberOfSubsets = new int[n + 1][target + 1];

        // There's one way to form the sum 0: by choosing no elements
        for (int i = 0; i <= n; i++) {
            numberOfSubsets[i][0] = 1;
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= target; j++) {
                // We can always skip the current element
                numberOfSubsets[i][j] = numberOfSubsets[i - 1][j];
                // If we can include the current element
                if (j >= nums[i - 1]) {
                    numberOfSubsets[i][j] += numberOfSubsets[i - 1][j - nums[i - 1]];
                }
            }
        }
        return numberOfSubsets[n][target];
    }

    public int numberOfSubsetsWithNegative(int[] nums, int target) {
        Map<Integer, Integer> numberOfSubsets = new HashMap<>();
        numberOfSubsets.put(0, 1);

        for (int num : nums) {
            // Carry over from last iteration, we can always skip the current element
            Map<Integer, Integer> newSubsets = new HashMap<>(numberOfSubsets);
            for (Map.Entry<Integer, Integer> entry : numberOfSubsets.entrySet()) {
                int newSum = entry.getKey() + num;
                newSubsets.put(newSum, newSubsets.getOrDefault(newSum, 0) + entry.getValue());
            }
            numberOfSubsets = newSubsets;
        }
        return numberOfSubsets.getOrDefault(target, 0);
    }

    /**
     * Find out how many subsets can be formed from the integer array, so that no
     * any two elements in the subset have an absolute difference equal to the
     * target value. Each element can be used at most once.
     * 
     * @param nums   int array, non empty, non duplicate
     * @param target the absolute difference to avoid, positive integer
     * @return total number of possible subsets
     */
    public int numberOfSubsetsWithNoAbsoluteDifference(int[] nums, int target) {
        // for positive number target, the conflict numbers % target will be the same.
        // So for each module, we can keep a sorted set of numbers, and the conflict
        // only happens between neighbors. The original problem can be divided into the
        // number of subsets in a sorted treeSet for each module.

        List<TreeSet<Integer>> numberByModule = new ArrayList<>();
        for (int i = 0; i < target; i++) {
            numberByModule.add(new TreeSet<>());
        }

        for (int num : nums) {
            int module = Math.floorMod(num, target);
            numberByModule.get(module).add(num);
        }

        int totalSubsets = 1;

        for (TreeSet<Integer> set : numberByModule) {
            // we count for the number of subsets in the sorted set, with the constraint
            // that no element is conflicting with previous element.
            int size = set.size();

            if (size <= 1) {
                totalSubsets *= (size + 1);
                continue;
            }

            int[] numberOfSubsetsWithThisValue = new int[size];
            int[] numberOfSubsetsWithoutThisValue = new int[size];

            numberOfSubsetsWithThisValue[0] = 1;
            numberOfSubsetsWithoutThisValue[0] = 1;

            Iterator<Integer> iterator = set.iterator();
            int index = 1;
            int prev = iterator.next();
            while (iterator.hasNext()) {
                // compare with previous value
                int current = iterator.next();
                if (current - prev == target) {
                    numberOfSubsetsWithThisValue[index] = numberOfSubsetsWithoutThisValue[index - 1];
                } else {
                    numberOfSubsetsWithThisValue[index] = numberOfSubsetsWithoutThisValue[index - 1]
                            + numberOfSubsetsWithThisValue[index - 1];
                }
                // We can always skip the current element
                numberOfSubsetsWithoutThisValue[index] = numberOfSubsetsWithThisValue[index - 1]
                        + numberOfSubsetsWithoutThisValue[index - 1];
                prev = current;
                index++;
            }
            totalSubsets *= (numberOfSubsetsWithThisValue[size - 1] + numberOfSubsetsWithoutThisValue[size - 1]);
        }

        return totalSubsets - 1; // subtract the empty subset
    }

    private void search(int[] nums, int index, int target, Set<Integer> currentSet, int[] maxCount) {
        if (index == nums.length) {
            maxCount[0] = Math.max(maxCount[0], currentSet.size());
            return;
        }

        // Skip the current element
        search(nums, index + 1, target, currentSet, maxCount);

        // Include the current element if possible
        if (!currentSet.contains(nums[index] - target) && !currentSet.contains(nums[index] + target)) {
            currentSet.add(nums[index]);
            search(nums, index + 1, target, currentSet, maxCount);
            currentSet.remove(nums[index]);
        }

    }
}
