package linear;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WindowTest {
    private Window window;

    @BeforeEach
    public void setup() {
        window = new Window();
    }

    @Test
    public void testFindMaxValue() {
        int[] input = new int[] { 9, -1, 0, Integer.MAX_VALUE, Integer.MIN_VALUE, 100, -101 };
        int[] result = window.maxInWindow(input, 2);
        int[] expectedResult = new int[] { 9, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, 100 };

        assertArrayEquals(expectedResult, result);
    }

    @Test
    public void testFindMaxDecresing() {
        int[] input = new int[] { 99, 9, 1, 0, -1, -100 };
        int[] result = window.maxInWindow(input, 1);
        int[] expectedResult = new int[] { 99, 9, 1, 0, -1 };

        assertArrayEquals(expectedResult, result);
    }

    @Test
    public void testFindMaxIncreasing() {
        int[] input = new int[] { 1, 2, 3, 4, 5, 6 };
        int[] result = window.maxInWindow(input, 4);
        int[] expectedResult = new int[] { 5, 6 };

        assertArrayEquals(expectedResult, result);
    }

    @Test
    public void testFindMedian() {
        int[] input = new int[] { 5, 1, 5, 2, 6, 0, 7, 3 };
        double[] result = window.medianInWindow(input, 1);
        double[] expectedResult = new double[] { 3, 3, 3.5, 4, 3, 3.5, 5 };

        assertArrayEquals(expectedResult, result);
    }

    @Test
    public void testFindMedianDecreasing() {
        int[] input = new int[] { 7, 6, 5, 2, 1, 0, -7, -13 };
        double[] result = window.medianInWindow(input, 3);
        double[] expectedResult = new double[] { 5.5, 3.5, 1.5, 0.5, -3.5 };

        assertArrayEquals(expectedResult, result);
    }

    @Test
    public void testFindMedianIncreasing() {
        int[] input = new int[] { -3, -2, 0, 1, 3, 9 };
        double[] result = window.medianInWindow(input, 2);
        double[] expectedResult = new double[] { -2, 0, 1, 3 };

        assertArrayEquals(expectedResult, result);
    }
}
