package linear;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StringInterleavingTest {

    private StringInterleaving stringInterleaving;

    @BeforeEach
    public void setup() {
        stringInterleaving = new StringInterleaving();
    }

    @Test
    public void testIsInterleaving() {
        String word1 = "abc";
        String word2 = "def";
        String target = "adbcef";
        boolean result = stringInterleaving.isInterleaving(word1, word2, target);
        assertTrue(result);
    }

    @Test
    public void testIsInterleavingConcatenation() {
        String word1 = "abc";
        String word2 = "def";
        String target = "adbcef";
        boolean result = stringInterleaving.isInterleaving(word1, word2, target);
        assertTrue(result);
    }

    @Test
    public void testIsInterleavingWithEmptyString() {
        String word1 = "";
        String word2 = "abc";
        String target = "abc";
        boolean result = stringInterleaving.isInterleaving(word1, word2, target);
        assertTrue(result);
    }

    @Test
    public void testNumberOfInterleavings() {
        String word1 = "abc";
        String word2 = "bac";
        String target = "abc";
        int result = stringInterleaving.numberOfInterleavings(word1, word2, target);
        assertEquals(5, result);
    }
}
