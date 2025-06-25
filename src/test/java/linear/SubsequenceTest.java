package linear;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
}
