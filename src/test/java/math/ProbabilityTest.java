package math;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProbabilityTest {
    private static final double EPSILON = 1e-6;

    private Probability probability;

    @BeforeEach
    public void setup() {
        probability = new Probability();
    }

    @Test
    public void testSingleProbability() {
        double[] result = probability.longestCycleProbabilities(1);
        double[] expected = new double[] {0.0, 1.0};
        assertArrayEquals(expected, result);
    }

    @Test
    public void testMultipleProbability() {
        double[] result = probability.longestCycleProbabilities(2);
        double[] expected = new double[] {0.0, 0.5, 0.5};
        assertArrayEquals(expected, result);
    }

    @Test
    public void testMultipleProbabilityOfThree() {
        double[] result = probability.longestCycleProbabilities(3);
        double[] expected = new double[] {0.0, 1.0 / 6, 0.5, 1.0 / 3};
        assertArrayEquals(expected, result, EPSILON);
    }

    @Test 
    public void testMultipleProbabilityLargeNumber() {
        double[] result = probability.longestCycleProbabilities(100);
        assertEquals(101, result.length);
        assertEquals(0.01, result[100], EPSILON);
    }
}
