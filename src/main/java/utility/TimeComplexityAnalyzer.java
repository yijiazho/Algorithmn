package utility;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.IntConsumer;

import org.apache.commons.math4.legacy.analysis.ParametricUnivariateFunction;
import org.apache.commons.math4.legacy.fitting.SimpleCurveFitter;
import org.apache.commons.math4.legacy.fitting.WeightedObservedPoint;

public class TimeComplexityAnalyzer {

    public enum Complexity {
        CONSTANT("O(1)"),
        LOG_N("O(log n)"),
        N("O(n)"),
        N_LOG_N("O(n log n)"),
        N_SQUARE("O(n^2)"),
        N_CUBE("O(n^3)");

        private final String label;

        Complexity(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }

    public static Complexity analyze(IntConsumer method, int minSize, int maxSize, int stepSize) {
        List<WeightedObservedPoint> points = new ArrayList<>();

        for (int n = minSize; n <= maxSize; n += stepSize) {
            long totalTime = 0;
            int trials = 5;
            for (int t = 0; t < trials; t++) {
                long start = System.nanoTime();
                method.accept(n);
                long end = System.nanoTime();
                totalTime += (end - start);
            }
            points.add(new WeightedObservedPoint(1.0, n, totalTime / (double) trials));
        }

        Map<Complexity, Double> residuals = new HashMap<>();
        residuals.put(Complexity.CONSTANT, fit(points, x -> 1));
        residuals.put(Complexity.LOG_N, fit(points, Math::log));
        residuals.put(Complexity.N, fit(points, x -> x));
        residuals.put(Complexity.N_LOG_N, fit(points, x -> x * Math.log(x)));
        residuals.put(Complexity.N_SQUARE, fit(points, x -> x * x));
        residuals.put(Complexity.N_CUBE, fit(points, x -> x * x * x));

        return residuals.entrySet().stream()
                .min(Comparator.comparingDouble(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElse(Complexity.N);
    }

    private static double fit(List<WeightedObservedPoint> points, Function function) {
        ParametricUnivariateFunction model = new ParametricUnivariateFunction() {
            @Override
            public double value(double x, double[] parameters) {
                return parameters[0] * function.apply(x);
            }

            @Override
            public double[] gradient(double x, double[] parameters) {
                return new double[] { function.apply(x) };
            }
        };

        SimpleCurveFitter fitter = SimpleCurveFitter.create(model, new double[] { 1.0 });
        try {
            double[] fitted = fitter.fit(points);
            return computeResiduals(points, function, fitted[0]);
        } catch (Exception e) {
            return Double.MAX_VALUE;
        }
    }

    private static double computeResiduals(List<WeightedObservedPoint> points,
            Function function, double coefficient) {
        double sum = 0.0;
        for (WeightedObservedPoint point : points) {
            double expected = coefficient * function.apply(point.getX());
            double diff = point.getY() - expected;
            sum += diff * diff;
        }
        return sum;
    }

    @FunctionalInterface
    interface Function {
        double apply(double x);
    }

    public static void main(String[] args) {
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
        System.out.println("Estimated: " + c);
    }

}
