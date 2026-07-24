package tree;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class BTreeTest {

    @Test
    public void testBTree() {
        BTree bTree = new BTree(4);

        bTree.insert(10);
        bTree.insert(20);
        bTree.insert(5);
        bTree.insert(6);
        bTree.insert(12);
        bTree.insert(30);
        bTree.insert(7);

        bTree.printTree();

        assertFalse(bTree.search(15));
        assertTrue(bTree.search(10));

        assertFalse(bTree.insert(10));
        assertTrue(bTree.insert(15));

        assertFalse(bTree.delete(100));
        assertTrue(bTree.delete(10));
        assertFalse(bTree.delete(10));

        bTree.printTree();
    }
}
