package linear;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

import utility.TimeComplexityAnalyzer;
import utility.TimeComplexityAnalyzer.Complexity;

public class SubsequenceTest {

    private Subsequence subsequence;

    @BeforeEach
    public void setup() {
        subsequence = new Subsequence();
    }

    @Test
    public void testLongestIncreasingSubsequence() {
        int[] input = new int[] { 3, 4, 2, 5, 7 };
        List<Integer> result = subsequence.longestIncreasingSubsequence(input);
        List<Integer> expectedResult = List.of(3, 4, 5, 7);
        assertEquals(expectedResult, result);
    }

    @Test
    public void testLongestIncreasingSubsequenceIncreasing() {
        int[] input = new int[] { -1, 0, 2, 3, 5 };
        List<Integer> result = subsequence.longestIncreasingSubsequence(input);
        List<Integer> expectedResult = List.of(-1, 0, 2, 3, 5);
        assertEquals(expectedResult, result);
    }

    @Test
    public void testLongestIncreasingSubsequenceDecreasing() {
        int[] input = new int[] { 9, 8, 7, 6, 5 };
        List<Integer> result = subsequence.longestIncreasingSubsequence(input);
        List<Integer> expectedResult = List.of(5);
        assertEquals(expectedResult, result);
    }

    @Test
    public void testFindNumberOfLongestIncreasingSubsequence() {
        int[] input = new int[] { 3, 4, 2, 9, 7 };
        int result = subsequence.findNumberOfLongestIncreasingSubsequence(input);
        assertEquals(2, result);
    }

    @Test
    public void testFindNumberOfLongestIncreasingSubsequenceDecreasing() {
        int[] input = new int[] { 5, 4, 3, 2, 1, 0 };
        int result = subsequence.findNumberOfLongestIncreasingSubsequence(input);
        assertEquals(6, result);
    }

    @Test
    public void testLongestCommonSubsequence() {
        String string1 = "abcabcd";
        String string2 = "abadc";

        int result = subsequence.longestCommonSubsequence(string1, string2);
        assertEquals(4, result);
    }

    @Test
    public void testIncreasingTriplet() {
        int[] array = new int[] { 9, 10, 5, 4, 3, 2, 1, 1, 2, -1, 4 };
        boolean result = subsequence.increasingTriplet(array);
        assertEquals(true, result);
    }

    @Test
    public void testIncreasingTripletDuplicate() {
        int[] array = new int[] { 1, 1, 1, 1, -2, 10 };
        boolean result = subsequence.increasingTriplet(array);
        assertEquals(false, result);
    }

    @Test
    public void testLongesPalindromeSubsequence() {
        String s = "bbbab";
        int result = subsequence.longestPalindromeSubsequence(s);
    }

    @RetryingTest(3)
    public void testLongestIncreasingSubsequenceComplexity() {
        Complexity complexity = TimeComplexityAnalyzer.analyze(n -> {
            int[] data = generateRandomArray(n);
            subsequence.longestIncreasingSubsequence(data);
        }, 100, 100000, 500);
        assertEquals(Complexity.N_LOG_N, complexity);
    }

    @RetryingTest(3)
    public void testIncreasingTripletComplexity() {
        Complexity complexity = TimeComplexityAnalyzer.analyze(n -> {
            int[] data = generateRandomArray(n);
            subsequence.increasingTriplet(data);
        }, 100, 100000, 500);
        assertEquals(Complexity.N, complexity);
    }

    private int[] generateRandomArray(int n) {
        Random rand = new Random(42);
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = rand.nextInt();
        }
        return arr;
    }
}
