package utility;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class TimeComplexityAnalyzer {
    public static void analyze(String label, Function<Integer, ?> function, int start, int step, int maxSize) {
        System.out.printf("Analyzing: %s%n", label);
        System.out.printf("%10s%20s%n", "Input Size", "Time (ms)");

        List<Double> logNs = new ArrayList<>();
        List<Double> logTimes = new ArrayList<>();

        for (int size = start; size <= maxSize; size += step) {
            long startTime = System.nanoTime();
            function.apply(size);
            long endTime = System.nanoTime();
            double durationMs = (endTime - startTime) / 1_000_000.0;

            System.out.printf("%10d%20.5f%n", size, durationMs);

            if (durationMs > 0) { // Ignore 0s to avoid log(0)
                logNs.add(Math.log(size));
                logTimes.add(Math.log(durationMs));
            }
        }

        // Estimate the exponent using linear regression
        double k = linearRegressionSlope(logNs, logTimes);
        System.out.printf("Estimated Time Complexity: O(n^%.2f)%n", k);
    }

    private static double linearRegressionSlope(List<Double> x, List<Double> y) {
        int n = x.size();
        double sumX = 0, sumY = 0, sumXY = 0, sumX2 = 0;
        for (int i = 0; i < n; i++) {
            sumX += x.get(i);
            sumY += y.get(i);
            sumXY += x.get(i) * y.get(i);
            sumX2 += x.get(i) * x.get(i);
        }
        return (n * sumXY - sumX * sumY) / (n * sumX2 - sumX * sumX);
    }
}
