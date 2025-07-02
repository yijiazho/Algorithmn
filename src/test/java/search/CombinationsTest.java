package search;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
}
