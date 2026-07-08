package math;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NumberParserTest {

    private NumberParser numberParser;

    @BeforeEach
    public void setup() {
        numberParser = new NumberParser();
    }

    @Test
    public void testNumberParser() {
        String input = "1+2*3+4";
        Set<Integer> expected = Set.of(21, 13, 11, 15);
        Set<Integer> result = numberParser.allPossibleAnswers(input);
        assertEquals(expected, result);
    }

    @Test
    public void testNumberParserWithTwoDigits() {
        String input = "11+12*10";
        Set<Integer> expected = Set.of(131, 230);
        Set<Integer> result = numberParser.allPossibleAnswers(input);
        assertEquals(expected, result);
    }

    @Test
    public void testParserWithMultipleOperators() {
        String input = "2*3-4*5";
        Set<Integer> expected = Set.of(10, -14, -10, -34);
        Set<Integer> result = numberParser.allPossibleAnswers(input);
        assertEquals(expected, result);
    }

    @Test
    public void testParserWithDuplicateResults() {
        String input = "1+1+1+1";
        Set<Integer> expected = Set.of(4);
        Set<Integer> result = numberParser.allPossibleAnswers(input);
        assertEquals(expected, result);
    }
}
