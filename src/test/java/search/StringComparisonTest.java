package search;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StringComparisonTest {

    private StringComparison stringComparison;

    @BeforeEach
    public void setup() {
        stringComparison = new StringComparison();
    }

    @Test
    public void testPossiblyEquals() {
        String s1 = "internationalization";
        String s2 = "i18n";
        assertTrue(stringComparison.possiblyEquals(s1, s2));
    }


    @Test
    public void testPossiblyEqualsConcatNumbers() {
        String s1 = "internationalization";
        String s2 = "i108n";
        assertTrue(stringComparison.possiblyEquals(s1, s2));
    }

    @Test
    public void testPossiblyEqualsNegative() {
        String s1 = "internationalization";
        String s2 = "i88n";
        assertFalse(stringComparison.possiblyEquals(s1, s2));
    }

    @Test
    public void testPossiblyEqualsBothEncoded() {
        String s1 = "i18n";
        String s2 = "99on";
        assertTrue(stringComparison.possiblyEquals(s1, s2));
    }

    @Test
    public void testPossiblyEqualsNumbersOnly() {
        String s1 = "i18n";
        String s2 = "20";
        assertTrue(stringComparison.possiblyEquals(s1, s2));
    }

    @Test
    public void testPossiblyEqualsBothEncodedConcatNumbers() {
        String s1 = "112s";
        String s2 = "g841";
        assertTrue(stringComparison.possiblyEquals(s1, s2));
    }
}
