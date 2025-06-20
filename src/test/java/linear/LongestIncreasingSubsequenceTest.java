package linear;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LongestIncreasingSubsequenceTest {

    private LongestIncreasingSubsequence longestIncreasingSubsequence;

    @BeforeEach
    public void setup() {
        longestIncreasingSubsequence = new LongestIncreasingSubsequence();
    }

    @Test
    public void testLongestIncreasingSubsequence() {
        int[] input = new int[] { 3, 4, 2, 5, 7 };
        List<Integer> result = longestIncreasingSubsequence.longestIncreasingSubsequence(input);
        List<Integer> expectedResult = List.of(3, 4, 5, 7);
        assertEquals(expectedResult, result);
    }

    @Test
    public void testLongestIncreasingSubsequenceIncreasing() {
        int[] input = new int[] { -1, 0, 2, 3, 5 };
        List<Integer> result = longestIncreasingSubsequence.longestIncreasingSubsequence(input);
        List<Integer> expectedResult = List.of(-1, 0, 2, 3, 5);
        assertEquals(expectedResult, result);
    }

    @Test
    public void testLongestIncreasingSubsequenceDecreasing() {
        int[] input = new int[] { 9, 8, 7, 6, 5 };
        List<Integer> result = longestIncreasingSubsequence.longestIncreasingSubsequence(input);
        List<Integer> expectedResult = List.of(5);
        assertEquals(expectedResult, result);
    }
}
