package utility;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import utility.TimeComplexityAnalyzer.Complexity;
import static utility.TimeComplexityAnalyzer.analyze;

public class TimeComplexityAnalyzerTest {

    @Test
    public void testConstant() {
        Complexity c = analyze(n -> {
            int sum = n * n;
        }, 100, 5000, 200);
        assertEquals(Complexity.CONSTANT, c);
    }

    @Test
    public void testSquare() {
        Complexity c = analyze(n -> {
            int sum = 0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    sum += (i * j) % 7;
                }
            }
            // Prevents dead-code elimination
            if (sum == -1)
                System.out.println("Impossible");
        }, 100, 5000, 200);
        assertEquals(Complexity.N_SQUARE, c);
    }
}
