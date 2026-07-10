package search;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SubsetsTest {
    private Subsets subsets;

    @BeforeEach
    public void setup() {
        subsets = new Subsets();
    }

    @Test
    public void testNumberOfSubsets() {
        int[] nums = new int[] { 1, 2, 3 };
        int result = subsets.numberOfSubsets(nums, 4);
        assertEquals(1, result);
    }

    @Test
    public void testNumberOfSubsetsWithDuplicate() {
        int[] nums = new int[] { 1, 2, 2, 3 };
        int result = subsets.numberOfSubsets(nums, 4);
        assertEquals(2, result);
    }

    @Test
    public void testNumberOfSubsetsWithNegative() {
        int[] nums = new int[] { 1, -2, 3 };
        int result = subsets.numberOfSubsetsWithNegative(nums, 1);
        assertEquals(2, result);
    }

    @Test
    public void testNumberOfSubsetsWithNoAbsoluteDifference() {
        int[] nums = new int[] { 2, 4, 6 };
        int result = subsets.numberOfSubsetsWithNoAbsoluteDifference(nums, 2);
        assertEquals(4, result);
    }

    @Test
    public void testNumberOfSubsetsWithNoAbsoluteDifferenceWithMultipleModules() {
        int[] nums = new int[] { 1, 2, 3, 4, 6, 10, 9 };
        int result = subsets.numberOfSubsetsWithNoAbsoluteDifference(nums, 3);
        assertEquals(59, result);
    }

    @Test
    public void testNumberOfSubsetsWithNoAbsoluteDifferenceWithZero() {
        int[] nums = new int[] { 0, 1, 2, 3, 4, 6, 10 };
        int result = subsets.numberOfSubsetsWithNoAbsoluteDifference(nums, 3);
        assertEquals(59, result);
    }
}
