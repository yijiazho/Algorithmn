package math;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NumberCalculationTest {

    private NumberCalculation numberCalculation;

    @BeforeEach
    public void setup() {
        numberCalculation = new NumberCalculation();
    }

    @Test
    public void testCalculateMinOperations() {
        int n = 54;
        int result = numberCalculation.minOperations(n);
        int expected = 3;
        assertEquals(expected, result);
    }
}
