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
        int[] input = new int[] {9, -1, 0, Integer.MAX_VALUE, Integer.MIN_VALUE, 100, -101};
        int[] result = window.maxInWindow(input, 2);
        int[] expectedResult = new int[] {9, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, 100};

        assertArrayEquals(expectedResult, result);
    }

    @Test
    public void testFindMaxDecresing() {
        int[] input = new int[] {99, 9, 1, 0, -1, -100};
        int[] result = window.maxInWindow(input, 1);
        int[] expectedResult = new int[] {99, 9, 1, 0, -1};

        assertArrayEquals(expectedResult, result);
    }

    @Test
    public void testFindMaxIncreasing() {
        int[] input = new int[] {1, 2, 3, 4, 5, 6};
        int[] result = window.maxInWindow(input, 4);
        int[] expectedResult = new int[] {5, 6};

        assertArrayEquals(expectedResult, result);
    }
}
