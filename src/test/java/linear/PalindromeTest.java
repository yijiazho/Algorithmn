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
    public void testPalindromeAfterRemoving() {
        String s = "testst";
        boolean result = palindrome.validPalindromeAfterRemoving(s);
        assertEquals(true, result);
    }

    @Test
    public void testShortestPalindrome() {
        String s = "ifill";
        String result = palindrome.shortestPalindrome(s);
        assertEquals("llifill", result);
    }

    @Test
    public void testValidPalindrome() {
        String s = "abcddeca";
        boolean result = palindrome.isValidPalindrome(s, 2);
        assertEquals(true, result);
    }
}
