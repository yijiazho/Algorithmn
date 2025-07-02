package search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import utility.ArrayUtils;

public class Permutations {
    /**
     * Find out how many permutations are there if we use the elements in the
     * integer array to add up tp the target as sum, with each element is distinct
     * for example, {2, 5, 7} has 3 different permutations to form 7
     * {2, 5}, {5, 2}, {7}
     * 
     * @param nums   non empty integer array, each value is positive and distinct
     * @param target the sum to add up to
     * @return total number of possible permutations
     */
    public int permutations(int[] nums, int target) {
        // total possible permutations to for target
        int[] possiblePermutations = new int[target + 1];
        possiblePermutations[0] = 1;

        for (int i = 1; i <= target; i++) {
            for (int j = 0; j < nums.length; j++) {
                if (i >= nums[j]) {
                    possiblePermutations[i] += possiblePermutations[i - nums[j]];
                }
            }
        }

        return possiblePermutations[target];
    }

    /**
     * Sort the int array and find out the next permutations
     * For example, {7, 2, 5, 3, 8} -> {7, 2, 3, 5, 8}
     * When decresing {5, 4, 3, 2, 1} -> {1, 2, 3, 4, 5}
     * 
     * @param nums integer array, non empty
     */
    public void nextPermutation(int[] nums) {
        int l = nums.length;
        if (l == 1) {
            return;
        }
        // find the peak
        int i;
        for (i = l - 1; i >= 1; i--) {
            if (nums[i] > nums[i - 1]) {
                break;
            }
        }
        if (i == 0) {
            // decreasing, trying to swap
            for (int j = 0; j < l / 2; j++) {
                ArrayUtils.swap(nums, j, l - j - 1);
            }

        } else {
            // find the first larger than left side of the peak
            int j;
            for (j = l - 1; j >= i; j--) {
                if (nums[j] > nums[i - 1]) {
                    break;
                }
            }
            ArrayUtils.swap(nums, i - 1, j);
            Arrays.sort(nums, i, l);
        }

    }

    public List<String> generatePalindromePermutations(String s) {
        List<String> result = new ArrayList<>();
        int[] freqMap = new int[26];
        for (char c : s.toCharArray()) {
            freqMap[c - 'a']++;
        }

        int oddFreqChar = -1;
        for (int i = 0; i < 26; i++) {
            if (freqMap[i] % 2 == 1) {
                if (oddFreqChar == -1) {
                    oddFreqChar = i;
                } else {
                    return result;
                }
            }
            freqMap[i] /= 2;
        }

        traverse(result, new StringBuilder(), freqMap, s.length() / 2, oddFreqChar);
        return result;
    }

    private void traverse(List<String> result, StringBuilder path, int[] freqMap, int targetLen, int oddFreqChar) {
        if (path.length() == targetLen) {
            if (oddFreqChar == -1) {
                result.add(path.toString() + path.reverse().toString());
                path.reverse();
            } else {
                result.add(path.toString() + (char) (oddFreqChar + 'a') + path.reverse().toString());
                path.reverse();
            }
            return;
        }

        for (int i = 0; i < 26; i++) {
            if (freqMap[i] > 0) {
                freqMap[i]--;
                path.append((char) (i + 'a'));
                traverse(result, path, freqMap, targetLen, oddFreqChar);
                path.setLength(path.length() - 1);
                freqMap[i]++;
            }
        }
    }
}
