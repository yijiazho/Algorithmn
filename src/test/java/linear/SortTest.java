package linear;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SortTest {
    
    private Sort sort;

    @BeforeEach
    public void setup() {
        sort = new Sort();
    }

    @Test
    public void testQuickSort() {
        int[] array = new int[] {3, 6, 1, 7, -9, 0};
        sort.sort(array, SortMethod.QUICKSORT);
        assertTrue(isArrayincreasing(array));
    }

    @Test
    public void testMergeSort() {
        int[] array = new int[] {3, 6, 1, 7, -9, 0};
        sort.sort(array, SortMethod.MERGESORT);
        assertTrue(isArrayincreasing(array));
    }

    private boolean isArrayincreasing(int[] array) {
        if (array == null || array.length == 0) {
            return true;
        }

        for (int i = 1; i < array.length; i++) {
            if (array[i] < array[i - 1]) {
                return false;
            }
        }
        return true;
    }
}
