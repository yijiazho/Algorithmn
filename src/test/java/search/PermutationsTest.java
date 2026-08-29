package search;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PermutationsTest {

    private Permutations permutations;

    @BeforeEach
    public void setup() {
        permutations = new Permutations();
    }

    @Test
    public void testPermutations() {
        int[] nums = new int[] { 3, 13, 7, 5 };
        int result = permutations.permutations(nums, 20);
        assertEquals(27, result);
    }

    @Test
    public void testNextPermutation() {
        int[] nums = new int[] { 5, 7, 8, 6, 4, 2 };
        permutations.nextPermutation(nums);
        assertArrayEquals(new int[] { 5, 8, 2, 4, 6, 7 }, nums);
    }

    @Test
    public void testNextPermutationPeakAtTail() {
        int[] nums = new int[] { 7, 2, 5, 3, 8 };
        permutations.nextPermutation(nums);
        assertArrayEquals(new int[] { 7, 2, 5, 8, 3 }, nums);
    }

    @Test
    public void testNextPermutationDuplicate() {
        int[] nums = new int[] { 3, 5, 2, 3, 1, 1 };
        permutations.nextPermutation(nums);
        assertArrayEquals(new int[] { 3, 5, 3, 1, 1, 2 }, nums);
    }

    @Test
    public void testNextPermutationDecreasing() {
        int[] nums = new int[] { 5, 4, 3, 2, 1 };
        permutations.nextPermutation(nums);
        assertArrayEquals(new int[] { 1, 2, 3, 4, 5 }, nums);
    }

    @Test
    public void testSmallestPermutation() {
        String s = "lls";
        int[] nums = permutations.smallestPermutation(s);
        assertArrayEquals(new int[] { 1, 2, 4, 3 }, nums);
    }

    @Test
    public void testSmallestPermutationIncreasing() {
        String s = "ll";
        int[] nums = permutations.smallestPermutation(s);
        assertArrayEquals(new int[] {1, 2, 3}, nums);
    }

    @Test
    public void testSmallestPermutationDecreasing() {
        String s = "ss";
        int[] nums = permutations.smallestPermutation(s);
        assertArrayEquals(new int[] {3, 2, 1}, nums);
    }

    @Test
    public void testSmallestPermutationComplex() {
        String s = "llsssllssl";
        int[] nums = permutations.smallestPermutation(s);
        assertArrayEquals(new int[] { 1, 2, 6, 5, 4, 3, 7, 10, 9, 8, 11 }, nums);
    }

    @Test
    public void testGeneratePalindromePermutations() {
        String input = "aabbccc";
        List<String> result = permutations.generatePalindromePermutations(input);
        assertEquals(List.of("abcccba", "acbcbca", "bacccab", "bcacacb", "cabcbac", "cbacabc"), result);
    }
}
