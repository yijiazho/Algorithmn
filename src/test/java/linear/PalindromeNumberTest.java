package linear;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PalindromeNumberTest {
    private PalindromeNumber palindromeNumber;

    @BeforeEach
    public void setup() {
        palindromeNumber = new PalindromeNumber();
    }

    @Test
    public void testKthPalindrome() {
        int[] queries = { 1, 2, 3, 4, 5, 90 };
        int intLength = 3;
        long[] result = palindromeNumber.kthPalindrome(queries, intLength);
        long[] expected = { 101, 111, 121, 131, 141, 999 };
        assertArrayEquals(expected, result);
    }

    @Test
    public void testKthPalindromeNotExist() {
        int[] queries = { 1, 2, 3, 4, 5, 100 };
        int intLength = 3;
        long[] result = palindromeNumber.kthPalindrome(queries, intLength);
        long[] expected = { 101, 111, 121, 131, 141, -1 };
        assertArrayEquals(expected, result);
    }

    @Test
    public void testCountNumberOfBinaryPalindromes() {
        int n = 10;
        int result = palindromeNumber.countNumberOfBinaryPalindromes(n);
        assertEquals(6, result);
    }

    @Test
    public void testCountNumberOfBinaryPalindromesZero() {
        int n = 0;
        int result = palindromeNumber.countNumberOfBinaryPalindromes(n);
        assertEquals(1, result);
    }
}
