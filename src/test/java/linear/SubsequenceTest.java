package linear;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static utility.TestUtil.generateRandomArray;

import java.util.List;

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
        assertEquals(4, result);
    }

    @Test
    public void testMinWindow() {
        String s1 = "ggez";
        String s2 = "ggz";

        String result = subsequence.minWindow(s1, s2);
        String expected = "ggez";
        assertEquals(expected, result);
    }

    @Test
    public void testMinWindowNoMatch() {
        String s1 = "abcdefghijklmn";
        String s2 = "xyz";

        String result = subsequence.minWindow(s1, s2);
        String expected = "";
        assertEquals(expected, result);
    }

    @Test
    public void testMinWindowDuplicate() {
        String s1 = "xx";
        String s2 = "xx";

        String result = subsequence.minWindow(s1, s2);
        String expected = "xx";
        assertEquals(expected, result);
    }

    @Test
    public void findLongestUncommonSubsequenceUnique() {
        String[] input = new String[] { "abcd", "efg", "hijkl" };
        int result = subsequence.longestUncommonSubsequence(input);
        assertEquals(5, result);
    }

    @Test
    public void findLongestUncommonSubsequenceSame() {
        String[] input = new String[] { "aaaa", "aaaa", "aaa" };
        int result = subsequence.longestUncommonSubsequence(input);
        assertEquals(-1, result);
    }

    @Test
    public void findLongestUncommonSubsequence() {
        String[] input = new String[] { "aabbcc", "aabbcc", "cb" };
        int result = subsequence.longestUncommonSubsequence(input);
        assertEquals(2, result);
    }

    @Test
    public void findLongestUncommonSubsequenceSingleCharacter() {
        String[] input = new String[] { "z", "h", "o", "u", "u", "o", "h", "z" };
        int result = subsequence.longestUncommonSubsequence(input);
        assertEquals(-1, result);
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
}
