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
        int result = median.countSubarrays(input, 4);
        assertEquals(3, result);
    }
}
