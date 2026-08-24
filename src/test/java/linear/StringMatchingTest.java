package linear;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StringMatchingTest {

    private StringMatching stringMatching;

    @BeforeEach
    public void setup() {
        this.stringMatching = new StringMatching();
    }

    @Test
    public void testSearchString() {
        String text = "helloworld";
        String pattern = "world";
        List<Integer> result = stringMatching.findAllPatterns(text, pattern);
        List<Integer> expected = List.of(5);
        assertEquals(expected, result);
    }

    @Test
    public void testSearchStringMultiplePatterns() {
        String text = "helloworldworldworld";
        String pattern = "world";
        List<Integer> result = stringMatching.findAllPatterns(text, pattern);
        List<Integer> expected = List.of(5, 10, 15);
        assertEquals(expected, result);
    }
}
