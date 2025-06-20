package math;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PowTest {

    private Pow pow;

    @BeforeEach
    public void setup() {
        pow = new Pow();
    }

    @Test
    public void testPow() {
        assertEquals(1024.0, pow.pow(2.0, 10));
        assertEquals(9.261, pow.pow(2.1, 3), 0.001);
        assertEquals(0.25, pow.pow(2.0, -2), 0.001);
        assertEquals(0.0, pow.pow(0.0, 5), 0.001);
        assertEquals(1.0, pow.pow(1.0, 100), 0.001);
        assertEquals(-8.0, pow.pow(-2.0, 3), 0.001);
    }

    @Test
    public void tesPowMatrix() {
        int[][] matrix = new int[][] {
                { 2, 3 },
                { 4, 5 }
        };
        int[][] expected = new int[][] {
                { 16, 21 },
                { 28, 37 }
        };
        int[][] result = pow.pow(matrix, 2);
        assertArrayEquals(expected, result);
    }
}
