package dataStructure;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TrieTest {
    
    private Trie trie;

    @BeforeEach
    public void setup() {
        trie = new Trie(List.of("he", "she", "his", "hers"));
    }

    @Test
    public void testSearchAllMatchesMultiplePatternsOfSameIndex() {
        String text = "ushers";
        List<List<Integer>> result = trie.searchAllMatches(text);
        List<List<Integer>> expected = List.of(
            List.of(1, 1), List.of(2, 0), List.of(2, 3)
        );
        assertEquals(expected, result);
    }

    @Test
    public void testSearchAllMatchesMultipleIndiciesOfSamePattern() {
        String text = "ushershers";
        List<List<Integer>> result = trie.searchAllMatches(text);
        List<List<Integer>> expected = List.of(
            List.of(1, 1), List.of(2, 0), List.of(2, 3), List.of(5, 1), List.of(6, 0), List.of(6, 3)
        );
        assertEquals(expected, result);
    }
}
