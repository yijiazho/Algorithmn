package linear;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HistogramTest {

    private Histogram histogram;

    @BeforeEach
    public void setup() {
        histogram = new Histogram();
    }t

    @Test
    public void testHistogramArea() {
        int[] h = new int[] {2, 1, 5, 6, 2, 3};
        int area = histogram.largestRectangleArea(h);
        assertEquals(10, area);
    }

    @Test
    public void testHistogramAreaAscending() {
        int[] h = new int[] {3, 4, 5, 6, 7};
        int area = histogram.largestRectangleArea(h);
        assertEquals(16, area);
    }

    @Test
    public void testHistogramAreaDescending() {
        int[] h = new int[] {9, 8, 7, 6, 5};
        int area = histogram.largestRectangleArea(h);
        assertEquals(25, area);
    }
}
