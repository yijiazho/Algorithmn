package linear;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PalindromeTest {

    private Palindrome palindrome;

    @BeforeEach
    public void setup() {
        palindrome = new Palindrome();
    }

    @Test
    public void testIsPalindromeAfterRemovingAtMostOneCharacter() {
        String s = "testst";
        boolean result = palindrome.isPalindromeAfterRemovingAtMostOneCharacter(s);
        assertEquals(true, result);
    }

    @Test
    public void testShortestPalindromeAfterAppendingLeadingCharacters() {
        String s = "ifill";
        String result = palindrome.shortestPalindromeAfterAppendingLeadingCharacters(s);
        assertEquals("llifill", result);
    }

    @Test
    public void testIsPalindromeAfterRemovingAtMostKCharacters() {
        String s = "abcddeca";
        boolean result = palindrome.isPalindromeAfterRemovingAtMostKCharacters(s, 2);
        assertEquals(true, result);
    }

    @Test
    public void testCountNumberOfBinaryPalindromes() {
        int n = 10;
        int result = palindrome.countNumberOfBinaryPalindromes(n);
        assertEquals(6, result);
    }
}
