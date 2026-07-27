package tree;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SegmentTreeTest {

    private SegmentTree segmentTree;

    @BeforeEach
    public void setup() {
        segmentTree = new SegmentTree();
    }

    @Test
    public void testFindLongestRepeating() {
        String s = "babacc";
        String chars = "bcb";
        int[] indices = new int[] { 1, 3, 3 };

        int[] res = segmentTree.findLongestRepeating(s, chars, indices);
        assertArrayEquals(new int[] { 3, 3, 4 }, res);
    }

    @Test
    public void testFindLongestRepeatingWithNoChange() {
        String s = "babacc";
        String chars = "bab";
        int[] indices = new int[] { 0, 1, 2 };

        int[] res = segmentTree.findLongestRepeating(s, chars, indices);
        assertArrayEquals(new int[] { 2, 2, 2 }, res);
    }

    @Test
    public void testFindLongestRepeatingWithAllSameChar() {
        String s = "aaaaaa";
        String chars = "bbbbbb";
        int[] indices = new int[] { 0, 1, 2, 3, 4, 5 };

        int[] res = segmentTree.findLongestRepeating(s, chars, indices);
        assertArrayEquals(new int[] { 5, 4, 3, 4, 5, 6 }, res);
    }
}
