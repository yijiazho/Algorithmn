package dataStructure.rangeQuery;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BinaryIndexTreeTest {
    private BinaryIndexTree binaryIndexTree;

    @BeforeEach
    public void setup() {
        binaryIndexTree = new BinaryIndexTree(new int[] {3, 4, 1, 5, 6, 0, -2, -3, 2, -3});
    }

    @Test
    public void testBinaryIndexTree() {
        assertEquals(3, binaryIndexTree.rangeSum(0, 0));
        assertEquals(13, binaryIndexTree.rangeSum(0, 9));
        assertEquals(1, binaryIndexTree.rangeSum(4, 7));

        binaryIndexTree.update(0, 2);
        assertEquals(2, binaryIndexTree.rangeSum(0, 0));
        assertEquals(12, binaryIndexTree.rangeSum(0, 9));
        binaryIndexTree.update(6, 2);
        assertEquals(5, binaryIndexTree.rangeSum(4, 7));
    }

}
