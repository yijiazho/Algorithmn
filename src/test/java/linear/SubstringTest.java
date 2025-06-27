package linear;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SubstringTest {

    private Substring substring;

    @BeforeEach
    public void setup() {
        substring = new Substring();
    }

    @Test
    public void testLongestCommonSubstring() {
        String a = "helloworld";
        String b = "flyinglow";

        int result = substring.longestCommonSubstring(a, b);
        assertEquals(3, result);
    }

    @Test
    public void testLongestCommonSubstringRepeating() {
        String a = "abcabccccba";
        String b = "cccc";

        int result = substring.longestCommonSubstring(a, b);
        assertEquals(4, result);
    }

    @Test
    public void testLongestSubstringKDistinct() {
        String input = "eceba";
        int result = substring.longestSubstringKDistinct(input, 2);

        assertEquals(3, result);
    }

    @Test
    public void testLongestSubstringKDistinctConsecutiveCharacter() {
        String input = "aaaaa";
        int result = substring.longestSubstringKDistinct(input, 1);

        assertEquals(5, result);
    }

    @Test
    public void testWordSubsets() {
        String[] candidates = new String[] { "amazon", "apple", "facebook", "ebay", "google" };
        String[] target = new String[] { "e", "o" };

        List<String> result = substring.wordSubsets(candidates, target);
        List<String> expected = List.of("facebook", "google");
        assertEquals(expected, result);
    }

    @Test
    public void testLastSubstring() {
        String s = "zhohudsonzhou";
        String result = substring.lastSubstring(s);

        assertEquals("zhou", result);
    }
}