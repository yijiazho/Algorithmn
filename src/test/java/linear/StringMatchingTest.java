package linear;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
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
        List<Integer> result = stringMatching.findAllPatternsWithZ(text, pattern);
        List<Integer> result2 = stringMatching.findAllPatternsWithKMP(text, pattern);
        List<Integer> expected = List.of(5);
        assertEquals(expected, result);
        assertEquals(expected, result2);
    }

    @Test
    public void testSearchStringMultiplePatterns() {
        String text = "helloworldworldworld";
        String pattern = "world";
        List<Integer> result = stringMatching.findAllPatternsWithZ(text, pattern);
        List<Integer> result2 = stringMatching.findAllPatternsWithKMP(text, pattern);
        List<Integer> expected = List.of(5, 10, 15);
        assertEquals(expected, result);
        assertEquals(expected, result2);
    }

    @Test
    public void testSearchStringNoMatching() {
        String text = "helloworld";
        String pattern = "world!";
        List<Integer> result = stringMatching.findAllPatternsWithZ(text, pattern);
        List<Integer> result2 = stringMatching.findAllPatternsWithKMP(text, pattern);
        List<Integer> expected = new ArrayList<>();
        assertEquals(expected, result);
        assertEquals(expected, result2);
    }

    @Test
    public void testSearchRecurringPattern() {
        String text = "abababc";
        String pattern = "ababc";
        List<Integer> result = stringMatching.findAllPatternsWithZ(text, pattern);
        List<Integer> result2 = stringMatching.findAllPatternsWithKMP(text, pattern);
        List<Integer> expected = List.of(2);
        assertEquals(expected, result);
        assertEquals(expected, result2);
    }
}
