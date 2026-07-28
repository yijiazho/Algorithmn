package linear;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PrefixTest {

    Prefix prefix;

    @BeforeEach
    public void setup() {
        prefix = new Prefix();
    }

    @Test
    public void testNumberOfPrefixes() {
        int[] nums = { 1, 2, 1, 2, 1 };
        int result = prefix.numberOfPrefixes(nums);

        assertEquals(1, result);
    }

    @Test
    public void testNumberOfPrefixesNoMatch() {
        int[] nums = { 1, 2, 3, 4, 5 };
        int result = prefix.numberOfPrefixes(nums);

        assertEquals(0, result);
    }

    @Test
    public void testNumberOfPrefixesMultipleMatches() {
        int[] nums = { 1, 1, 1, 1, 2, 1 };
        int result = prefix.numberOfPrefixes(nums);

        assertEquals(2, result);
    }
}
