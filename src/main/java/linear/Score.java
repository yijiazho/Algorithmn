package linear;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Score {

    /**
     * Selecting any combination of index and find the maximum scores among all
     * A combination is valid unless there exists i and j, where
     * points[i] > points[j] and age[i] < age[j]
     */
    public int maxPoints(int[] points, int[] ages) {
        int n = points.length;

        Integer[] sortedIndex = new Integer[n];
        for (int i = 0; i < n; i++) {
            sortedIndex[i] = i;
        }

        Arrays.sort(sortedIndex, (i, j) -> {
            if (ages[i] != ages[j]) {
                return ages[i] - ages[j];
            } else {
                return points[i] - points[j];
            }
        });

        int[] max = new int[1];
        // Key is list of curIndex and minRequirement, which is the state
        // since minRequirement is basically point[n], we can use n * n to represent
        // Value is the optimized total score of this state
        Map<List<Integer>, Integer> optimizedSolution = new HashMap<>();
        traverse(points, ages, sortedIndex, 0, 0, -1, max, optimizedSolution);
        return max[0];
    }

    private void traverse(int[] points, int[] ages, Integer[] sortedIndex, int curIndex, int totalScoresSoFar,
            int minRequirementIndex, int[] max, Map<List<Integer>, Integer> optimizedSolution) {
        int n = points.length;
        if (curIndex >= n) {
            max[0] = Math.max(max[0], totalScoresSoFar);
            return;
        }

        int index = sortedIndex[curIndex];

        List<Integer> key = createKey(index, minRequirementIndex);

        if (optimizedSolution.getOrDefault(key, 0) <= totalScoresSoFar) {
            // this path is new or more optimized
            optimizedSolution.put(key, totalScoresSoFar);
        } else {
            // this path is less optimized
            return;
        }

        // either keep (if valid)
        if (minRequirementIndex == -1 || points[index] >= points[minRequirementIndex]) {
            traverse(points, ages, sortedIndex, curIndex + 1, totalScoresSoFar + points[index], index, max,
                    optimizedSolution);
        }

        // or skip
        traverse(points, ages, sortedIndex, curIndex + 1, totalScoresSoFar, minRequirementIndex, max,
                optimizedSolution);

    }

    private List<Integer> createKey(int index, int minRequirement) {
        return List.of(index, minRequirement);
    }
}
