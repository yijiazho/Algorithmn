package search;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class CombinationTest {

    private Combination combination;

    @BeforeEach
    public void setup() {
        combination = new Combination();
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
}
