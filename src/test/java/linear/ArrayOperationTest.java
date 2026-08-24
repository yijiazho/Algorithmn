package linear;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ArrayOperationTest {
    private ArrayOperation arrayOperation;

    @BeforeEach
    public void setup() {
        arrayOperation = new ArrayOperation();
    }

    @Test
    public void testArrayOperationRemoveToSort() {
        int[] nums = new int[] { 5, 2, 3, 1, 4, 3, 2 };
        int result = arrayOperation.minimumPairRemoval(nums);
        assertEquals(4, result);
    }

    @Test
    public void testArrayOperationRemoveToSortAlreadySorted() {
        int[] nums = new int[] { 1, 2, 2, 4 };
        int result = arrayOperation.minimumPairRemoval(nums);
        assertEquals(0, result);
    }

    @Test
    public void testArrayOperationRemoveToSortWithNegative() {
        int[] nums = new int[] { 3, -2, 2, 1, 0, -1 };
        int result = arrayOperation.minimumPairRemoval(nums);
        assertEquals(5, result);
    }
}
