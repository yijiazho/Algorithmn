package tree;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NumberOfSmallerTest {

    private NumberOfSmaller numberOfSmaller;

    @BeforeEach
    public void setup() {
        numberOfSmaller = new NumberOfSmaller();
    }

    @Test
    public void testNumberOfSmallerOnTheLeft() {
        int[] nums = { 2, 5, 6, 1 };
        int[] expected = { 0, 1, 2, 0 };
        int[] result = numberOfSmaller.numberOfSmallerOnTheLeft(nums);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testNumberOfSmallerOnTheLeftWithDuplicates() {
        int[] nums = { 2, 5, 6, 1, 5 };
        int[] expected = { 0, 1, 2, 0, 2 };
        int[] result = numberOfSmaller.numberOfSmallerOnTheLeft(nums);
        assertArrayEquals(expected, result);
    }

    @Test
    public void testNumberOfSmallerOnTheLeftWithAllSameElements() {
        int[] nums = { 1, 1, 1, 1 };
        int[] expected = { 0, 0, 0, 0 };
        int[] result = numberOfSmaller.numberOfSmallerOnTheLeft(nums);
        assertArrayEquals(expected, result);
    }
}
