package linear;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}