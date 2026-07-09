package linear;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BitTest {

    private Bit bit;

    @BeforeEach
    public void setup() {
        bit = new Bit();
    }

    @Test
    public void testSizeOfLargestSubset() {
        String[] strs = { "10", "0001", "111001", "1", "0" };
        int m = 5;
        int n = 3;
        int expected = 4;
        int result = bit.sizeOfLargestSubset(strs, m, n);
        assertEquals(expected, result);
    }
}
