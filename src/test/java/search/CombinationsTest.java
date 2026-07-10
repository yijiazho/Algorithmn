package search;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CombinationsTest {

    private Combinations combination;

    @BeforeEach
    public void setup() {
        combination = new Combinations();
    }

    @Test
    public void testFindSum() {
        int[] nums = new int[] { 2, 3, 1, 5 };
        assertTrue(combination.findSum(nums));
    }

    @Test
    public void testFindSumNegative() {
        int[] nums = new int[] { 2, 3, 1, -3, 6, 4 };
        assertTrue(combination.findSum(nums));
    }

    @Test
    public void testFindSumNotValid() {
        int[] nums = new int[] { -3, 0, 1, 2, 5 };
        assertFalse(combination.findSum(nums));
    }

    @Test
    public void testCombinationSum() {
        int[] nums = new int[] { 10, 3, 2, 5, 6, -3 };
        List<List<Integer>> result = combination.combinationSum(nums, 8);
        List<List<Integer>> expected = List.of(
                List.of(-3, 2, 3, 6),
                List.of(-3, 5, 6),
                List.of(2, 6),
                List.of(3, 5));
        assertEquals(expected, result);
    }

    @Test
    public void testCombinationSumDuplicate() {
        int[] nums = new int[] { 1, 0, 1, 1, 1, 1, 2 };
        List<List<Integer>> result = combination.combinationSum(nums, 2);
        List<List<Integer>> expected = List.of(
                List.of(0, 1, 1),
                List.of(0, 2),
                List.of(1, 1),
                List.of(2));
        assertEquals(expected, result);
    }

    @Test
    public void testNumberOfCombinations() {
        int[] nums = new int[] { 13, 5, 7, 2 };
        int result = combination.combinations(nums, 20);

        assertEquals(7, result);
    }

    @Test
    public void testSubsetSum() {
        int[] nums = new int[] { 1, 3, 5 };
        int[] result = combination.subsetSums(nums);
        int[] expected = new int[] { 0, 1, 3, 5, 4, 6, 8, 9 };
        Arrays.sort(result);
        Arrays.sort(expected);

        assertArrayEquals(expected, result);
        int[] originalArray = combination.recoverArray(result, 3);
        Arrays.sort(originalArray);
        Arrays.sort(nums);
        assertArrayEquals(nums, originalArray);
    }

    @Test
    public void testSubsetSumWithNegative() {
        int[] nums = new int[] { 1, -3, 2 };
        int[] result = combination.subsetSums(nums);
        int[] expected = new int[] { 0, -1, 3, -2, 2, -3, 1, 0 };

        Arrays.sort(result);
        Arrays.sort(expected);
        assertArrayEquals(expected, result);

        int[] originalArray = combination.recoverArray(result, 3);
        Arrays.sort(originalArray);
        Arrays.sort(nums);
        assertArrayEquals(nums, originalArray);
    }

    @Test
    public void testSubsetSumWithNegativeAndZero() {
        int[] nums = new int[] { 0, -1, 4, 5 };
        int[] result = combination.subsetSums(nums);
        int[] expected = new int[] { 0, 0, 5, 5, 4, -1, 4, 9, 9, -1, 4, 3, 4, 8, 3, 8 };

        Arrays.sort(result);
        Arrays.sort(expected);
        assertArrayEquals(expected, result);

        int[] originalArray = combination.recoverArray(result, 4);
        Arrays.sort(originalArray);
        Arrays.sort(nums);
        assertArrayEquals(nums, originalArray);
    }

    @Test
    public void testSubsetSumAllZeros() {
        int[] nums = new int[] { 0, 0 };
        int[] result = combination.subsetSums(nums);
        int[] expected = new int[] { 0, 0, 0, 0 };
        assertArrayEquals(expected, result);

        int[] originalArray = combination.recoverArray(result, 2);
        assertArrayEquals(nums, originalArray);
    }

}
