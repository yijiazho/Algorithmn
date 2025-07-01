package linear;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static utility.TestUtil.generateRandomArray;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

import utility.TimeComplexityAnalyzer;
import utility.TimeComplexityAnalyzer.Complexity;

public class SortTest {

    private Sort sort;

    @BeforeEach
    public void setup() {
        sort = new Sort();
    }

    @Test
    public void testQuickSort() {
        int[] array = new int[] { 3, 6, 1, 7, -9, 0 };
        sort.sort(array, SortMethod.QUICKSORT);
        assertTrue(isArrayincreasing(array));
    }

    @Test
    public void testMergeSort() {
        int[] array = new int[] { 3, 6, 1, 7, -9, 0 };
        sort.sort(array, SortMethod.MERGESORT);
        assertTrue(isArrayincreasing(array));
    }

    @Test
    public void testQuickSortDecreasing() {
        int[] array = new int[] { Integer.MAX_VALUE, 9, 1, 0, -1, Integer.MIN_VALUE };
        sort.sort(array, SortMethod.QUICKSORT);
        assertTrue(isArrayincreasing(array));
    }

    @Test
    public void testMergeSortDecreasing() {
        int[] array = new int[] { Integer.MAX_VALUE, 9, 1, 0, -1, Integer.MIN_VALUE };
        sort.sort(array, SortMethod.MERGESORT);
        assertTrue(isArrayincreasing(array));
    }

    @RetryingTest(3)
    public void testQuickSortComplexity() {
        Complexity complexity = TimeComplexityAnalyzer.analyze(n -> {
            int[] data = generateRandomArray(n);
            sort.sort(data, SortMethod.QUICKSORT);
        }, 100, 100000, 500);
        assertEquals(Complexity.N_LOG_N, complexity);
    }

    @RetryingTest(3)
    public void testMergeSortComplexity() {
        Complexity complexity = TimeComplexityAnalyzer.analyze(n -> {
            int[] data = generateRandomArray(n);
            sort.sort(data, SortMethod.MERGESORT);
        }, 100, 100000, 500);
        assertEquals(Complexity.N_LOG_N, complexity);
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
