package linear;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MedianTest {

    private Median median;

    @BeforeEach
    public void setup() {
        median = new Median();
    }

    @Test
    public void testCountMedianArrays() {
        int[] input = new int[] { 3, 2, 1, 4, 5 };
        int result = median.numberOfSubarraysWithMedian(input, 4);
        int result2 = median.countFromMedian(input, 4);
        assertEquals(3, result);
        assertEquals(3, result2);
    }

    @Test
    public void testCountMedianArraysIncreasing() {
        int[] input = new int[] { 2, 3, 6, 7, 9, 10 };
        int result = median.numberOfSubarraysWithMedian(input, 2);
        int result2 = median.countFromMedian(input, 2);
        assertEquals(2, result);
        assertEquals(2, result2);
    }

    @Test
    public void testFindMedianInTwoSortedArrays() {
        int[] array1 = new int[] { 1, 3, 2, 4, 5 };
        int[] array2 = new int[] { 6, 7, 8, 9 };
        double result = median.findMedianInTwoSortedArrays(array1, array2);
        double expected = 5.0;
        assertEquals(expected, result, 0.0001);
    }

    @Test
    public void testFindMedianInTwoSortedArraysWithTotalEvenNumbers() {
        int[] array1 = new int[] { 1, 3, 2, 4 };
        int[] array2 = new int[] { 5, 6, 7, 8 };
        double result = median.findMedianInTwoSortedArrays(array1, array2);
        double expected = 4.5;
        assertEquals(expected, result, 0.0001);
    }

    @Test
    public void testFindMedianInTwoSortedArraysWithEmptyArray() {
        int[] array1 = new int[] { 1, 2, 3, 4 };
        int[] array2 = new int[] {};
        double result = median.findMedianInTwoSortedArrays(array1, array2);
        double expected = 2.5;
        assertEquals(expected, result, 0.0001);
    }

    @Test
    public void testFindMedianInMatrix() {
        int[][] matrix = new int[][] {
                { 1, 4, 7 },
                { 2, 5, 8 },
                { 3, 6, 9 }
        };
        int result = median.findMedianInMatrix(matrix);
        int expected = 5;
        assertEquals(expected, result);
    }

    @Test
    public void testFindMedianInMatrixSingleValue() {
        int[][] matrix = new int[][] {
                { 1 }
        };
        int result = median.findMedianInMatrix(matrix);
        int expected = 1;
        assertEquals(expected, result);
    }

    @Test
    public void testFindMedianInMatrixWithDuplicate() {
        int[][] matrix = new int[][] {
                { 1, 2, 4 },
                { 2, 3, 5 },
                { 3, 4, 6 }
        };
        int result = median.findMedianInMatrix(matrix);
        int expected = 3;
        assertEquals(expected, result);
    }

    @Test
    public void testFindMedianInMatrixWithDuplicateInRow() {
        int[][] matrix = new int[][] {
                { 1, 1, 5 },
                { 2, 2, 3 },
                { 4, 4, 6 }
        };
        int result = median.findMedianInMatrix(matrix);
        int expected = 3;
        assertEquals(expected, result);
    }
}
