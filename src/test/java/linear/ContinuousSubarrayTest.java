package linear;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ContinuousSubarrayTest {
    private ContinuousSubarray continuousSubarray;

    @BeforeEach
    public void setup() {
        continuousSubarray = new ContinuousSubarray();
    }

    @Test
    public void testFindUnsortedSubarray() {
        int[] nums = new int[] { 2, 6, 4, 8, 10, 9, 15 };
        int length = continuousSubarray.findUnsortedSubarray(nums);
        assertEquals(5, length);
    }

    @Test
    public void testFindUnsortedSubarraySorted() {
        int[] nums = new int[] { 1, 2, 3, 4 };
        int length = continuousSubarray.findUnsortedSubarray(nums);
        assertEquals(0, length);
    }

    @Test
    public void testFindUnsortedSubarraySortedWithDuplicates() {
        int[] nums = new int[] { 1, 2, 3, 3, 3 };
        int length = continuousSubarray.findUnsortedSubarray(nums);
        assertEquals(0, length);
    }

    @Test
    public void testFindUnsortedSubarrayWithDuplicates() {
        int[] nums = new int[] { 4, 1, 2, 2, 3, 3, 4, 4 };
        int length = continuousSubarray.findUnsortedSubarray(nums);
        assertEquals(6, length);
    }
}
