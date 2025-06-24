package linear;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StringCompressionTest {

    private StringCompression stringCompression;

    @BeforeEach
    public void setup() {
        stringCompression = new StringCompression();
    }

    @Test
    public void testStringCompression() {
        String original = "aabcccdee";
        String result = stringCompression.compress(original.toCharArray());
        String expected = "a2bc3de2";
        assertEquals(expected, result);

        String decompressed = stringCompression.deCompress(result);
        assertEquals(original, decompressed);
    }

    @Test
    public void testStringCompressionSingleCharacter() {
        String original = "abcdefg";
        String result = stringCompression.compress(original.toCharArray());
        String expected = "abcdefg";
        assertEquals(expected, result);

        String decompressed = stringCompression.deCompress(result);
        assertEquals(original, decompressed);
    }

    @Test
    public void testStringCompressionConsecutiveCharacters() {
        String original = "aaaaaaaa";
        String result = stringCompression.compress(original.toCharArray());
        String expected = "a8";
        assertEquals(expected, result);

        String decompressed = stringCompression.deCompress(result);
        assertEquals(original, decompressed);
    }

    @Test
    public void testStringCompressionTwoDigits() {
        String original = "aaaaaaaaaabbbbbbbbbbb";
        String result = stringCompression.compress(original.toCharArray());
        String expected = "a10b11";
        assertEquals(expected, result);

        String decompressed = stringCompression.deCompress(result);
        assertEquals(original, decompressed);
    }
}
