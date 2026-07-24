package tree;

/**
 * BTree is a self-balancing tree data structure that maintains sorted data and
 * allows searches, sequential access, insertions, and deletions in logarithmic
 * time.
 */
public class BTree {
    private final int order;
    private final int minKeys;
    private BTreeNode root;

    /**
     * Construct BTree with a given order n. The order n is the maximum number of
     * children a node can have. A BTree of order n can have at most n-1 keys and at
     * least (n/2)-1 keys. n must be even and at least 4. The root node can have a
     * minimum of 1 key.
     */
    public BTree(int n) {
        this.order = n;
        this.minKeys = (n / 2) - 1;
        this.root = new BTreeNode(n, true);
    }

    /**
     * Insert a value into the BTree.
     * 
     * @return true if inserted, flase if already exists
     */
    public boolean insert(int val) {
        if (search(val)) {
            return false;
        }

        BTreeNode currentRoot = root;

        if (isFull(currentRoot)) {
            // If root is full, then tree grows in height
            BTreeNode newRoot = new BTreeNode(order, false);
            newRoot.children[0] = currentRoot;
            splitChild(newRoot, 0);
            root = newRoot;
            insertNonFull(newRoot, val);
        } else {
            insertNonFull(currentRoot, val);
        }
        return true;
    }

    /**
     * Delete a value from the BTree.
     * 
     * @param val the value to delete
     * @return true if deleted, false if not found
     */
    public boolean delete(int val) {
        if (!search(val)) {
            return false;
        }

        deleteFromNode(root, val);

        // If the root node has no keys, make its first child the new root
        if (root.numKeys == 0 && !root.isLeaf) {
            BTreeNode oldRoot = root;
            root = root.children[0];
            oldRoot.children[0] = null;
        }

        return true;
    }

    /**
     * Search for a value in the BTree.
     * 
     * @param val the value to search for
     * @return true if value exists, false otherwise
     */
    public boolean search(int val) {
        return searchNode(root, val) != null;
    }

    /**
     * Print the BTree in a structured format
     */
    public void printTree() {
        printNode(root, 0);
    }

    private void printNode(BTreeNode node, int level) {
        if (node != null) {
            System.out.print("Level " + level + ": ");
            for (int i = 0; i < node.numKeys; i++) {
                System.out.print(node.keys[i] + " ");
            }
            System.out.println();
            for (int i = 0; i <= node.numKeys; i++) {
                printNode(node.children[i], level + 1);
            }
        }
    }

    private BTreeNode searchNode(BTreeNode node, int key) {
        int i = 0;

        // find the first key greater than or equal to key
        while (i < node.numKeys && key > node.keys[i]) {
            i++;
        }

        if (i < node.numKeys && key == node.keys[i]) {
            return node;
        }

        if (node.isLeaf) {
            return null;
        } else {
            return searchNode(node.children[i], key);
        }
    }

    /**
     * Insert a key into a non-full node.
     * 
     * @param node the node to insert into
     * @param key  the key to insert
     */
    private void insertNonFull(BTreeNode node, int key) {
        int i = node.numKeys - 1;
        if (node.isLeaf) {
            // Move greater keys one position to the right
            while (i >= 0 && key < node.keys[i]) {
                node.keys[i + 1] = node.keys[i];
                i--;
            }
            node.keys[i + 1] = key;
            node.numKeys++;
        } else {
            // Find the child that can be inserted into
            while (i >= 0 && key < node.keys[i]) {
                i--;
            }
            i++;

            if (isFull(node.children[i])) {
                splitChild(node, i);
                // After splitting, the median key moves up and the full child is split.
                // Determine which of the two children is now the correct one to insert into.
                if (key > node.keys[i]) {
                    i++;
                }
            }

            insertNonFull(node.children[i], key);
        }
    }

    /**
     * Split a full child node into two nodes and promoting median key to parent
     * node.
     * 
     * @param parent
     * @param childIndex
     */
    private void splitChild(BTreeNode parent, int childIndex) {
        BTreeNode fullChild = parent.children[childIndex];
        BTreeNode newChild = new BTreeNode(order, fullChild.isLeaf);

        int medianIndex = order / 2 - 1;
        int medianKey = fullChild.keys[medianIndex];

        // Move keys and children to new child
        newChild.numKeys = order / 2 - 1;
        for (int i = 0; i < newChild.numKeys; i++) {
            newChild.keys[i] = fullChild.keys[medianIndex + 1 + i];
        }

        if (!fullChild.isLeaf) {
            for (int i = 0; i < newChild.numKeys + 1; i++) {
                newChild.children[i] = fullChild.children[medianIndex + 1 + i];
            }
        }

        // Update the full child with left half
        fullChild.numKeys = medianIndex;

        // Shift parent's children and keys to make room for median key and new child
        for (int i = parent.numKeys; i >= childIndex; i--) {
            parent.children[i + 1] = parent.children[i];
        }
        parent.children[childIndex + 1] = newChild;

        for (int i = parent.numKeys - 1; i >= childIndex; i--) {
            parent.keys[i + 1] = parent.keys[i];
        }
        parent.keys[childIndex] = medianKey;
        parent.numKeys++;
    }

    private void deleteFromNode(BTreeNode node, int key) {
        int i = findKeyIndex(node, key);

        // If the key is found in this node
        if (i < node.numKeys && node.keys[i] == key) {
            if (node.isLeaf) {
                deleteFromLeaf(node, i);
            } else {
                deleteFromInternal(node, i);
            }
            return;
        }

        // If key is not in the node keys
        if (node.isLeaf) {
            return;
        }

        boolean isLastChild = (i == node.numKeys);
        BTreeNode child = node.children[i];

        // If the child has less than minKeys keys, fill it
        if (child.numKeys < minKeys + 1) {
            fillChild(node, i);
        }

        // If the last child was merged, we recurse on the (i-1)th child
        if (isLastChild && i > node.numKeys) {
            deleteFromNode(node.children[i - 1], key);
        } else {
            deleteFromNode(node.children[i], key);
        }
    }

    private void deleteFromLeaf(BTreeNode node, int keyIndex) {
        for (int i = keyIndex + 1; i < node.numKeys; i++) {
            node.keys[i - 1] = node.keys[i];
        }

        node.keys[node.numKeys - 1] = 0;
        node.numKeys--;
    }

    /**
     * Delete a key from internal node. The key to be deleted is present in the
     * internal node at index keyIndex.
     * 
     * @param node     the parent node of the key to be deleted
     * @param keyIndex the index of the key to be deleted in the node
     */
    private void deleteFromInternal(BTreeNode node, int keyIndex) {
        int key = node.keys[keyIndex];
        BTreeNode leftChild = node.children[keyIndex];
        BTreeNode rightChild = node.children[keyIndex + 1];

        // If the left child has at least minKeys + 1 keys, find the predecessor and
        // replace the key
        if (leftChild.numKeys > minKeys) {
            int predecessorKey = getLargestKey(leftChild);
            node.keys[keyIndex] = predecessorKey;
            deleteFromNode(leftChild, predecessorKey);
            return;
        }

        // If the right child has at least minKeys + 1 keys, find the successor and
        // replace the key
        if (rightChild.numKeys > minKeys) {
            int successorKey = getSmallestKey(rightChild);
            node.keys[keyIndex] = successorKey;
            deleteFromNode(rightChild, successorKey);
            return;
        }

        // If both children have only minKeys keys, merge them and delete the key from
        // the merged child
        mergeChildren(node, keyIndex);
        deleteFromNode(leftChild, key);
    }

    /**
     * Make sure child node has at least minKeys keys after deletion
     * by borrowing from siblings or merging with a sibling.
     * 
     * @param parent     the parent node of the child
     * @param childIndex the index of the child that might have less keys
     */
    private void fillChild(BTreeNode parent, int childIndex) {
        // Borrow from left if possible
        if (childIndex > 0 && parent.children[childIndex - 1].numKeys > minKeys) {
            borrowFromPrev(parent, childIndex);
            return;
        }

        // Borrow from right if possible
        if (childIndex < parent.numKeys && parent.children[childIndex + 1].numKeys > minKeys) {
            borrowFromNext(parent, childIndex);
            return;
        }

        // Merge when neither sibling has enough keys to borrow from
        if (childIndex < parent.numKeys) {
            mergeChildren(parent, childIndex);
        } else {
            mergeChildren(parent, childIndex - 1);
        }
    }

    /**
     * Borrow a key from the previous sibling of a child node.
     * 
     * @param parent     the parent node of the child
     * @param childIndex the index of child, must be greater than 0
     */
    private void borrowFromPrev(BTreeNode parent, int childIndex) {
        BTreeNode child = parent.children[childIndex];
        BTreeNode sibling = parent.children[childIndex - 1];

        // Shift child's keys and children to the right
        for (int i = child.numKeys - 1; i >= 0; i--) {
            child.keys[i + 1] = child.keys[i];
        }
        if (!child.isLeaf) {
            for (int i = child.numKeys; i >= 0; i--) {
                child.children[i + 1] = child.children[i];
            }
        }

        // Move the parent separate key between sibling and child to child
        child.keys[0] = parent.keys[childIndex - 1];
        if (!child.isLeaf) {
            child.children[0] = sibling.children[sibling.numKeys];
        }

        // Move key from sibling to parent
        parent.keys[childIndex - 1] = sibling.keys[sibling.numKeys - 1];

        // Update counts
        child.numKeys++;
        sibling.numKeys--;
    }

    private void borrowFromNext(BTreeNode parent, int childIndex) {
        BTreeNode child = parent.children[childIndex];
        BTreeNode sibling = parent.children[childIndex + 1];

        // Move the parent separate key between child and sibling to child
        child.keys[child.numKeys] = parent.keys[childIndex];
        if (!child.isLeaf) {
            child.children[child.numKeys + 1] = sibling.children[0];
        }

        // Move the first key from sibling to parent
        parent.keys[childIndex] = sibling.keys[0];

        // Shift sibling's keys and children to the left
        for (int i = 1; i < sibling.numKeys; i++) {
            sibling.keys[i - 1] = sibling.keys[i];
        }
        if (!sibling.isLeaf) {
            for (int i = 1; i <= sibling.numKeys; i++) {
                sibling.children[i - 1] = sibling.children[i];
            }
        }

        // Update counts
        child.numKeys++;
        sibling.numKeys--;
    }

    /**
     * Merge a parent node's separate key and two child nodes into one child node.
     * The child at childIndex and childIndex + 1 will be merged with key at
     * childIndex from the parent keys.
     * 
     * @param parent     the parent node of the two children
     * @param childIndex the index of the left child to merge with its right sibling
     */
    private void mergeChildren(BTreeNode parent, int childIndex) {
        BTreeNode leftChild = parent.children[childIndex];
        BTreeNode rightChild = parent.children[childIndex + 1];

        // Merge both into left child
        int originalLeftNumKeys = leftChild.numKeys;

        // copy the key from right child to the left child
        leftChild.keys[originalLeftNumKeys] = parent.keys[childIndex];
        for (int i = 0; i < rightChild.numKeys; i++) {
            leftChild.keys[originalLeftNumKeys + 1 + i] = rightChild.keys[i];
        }

        // copy the children from right child to the left child
        if (!leftChild.isLeaf) {
            for (int i = 0; i <= rightChild.numKeys; i++) {
                leftChild.children[originalLeftNumKeys + 1 + i] = rightChild.children[i];
            }
        }

        // Update left child's key count
        leftChild.numKeys += rightChild.numKeys + 1;

        // Remove parent's separate key by shifting keys to the left
        for (int i = childIndex + 1; i < parent.numKeys; i++) {
            parent.keys[i - 1] = parent.keys[i];
        }
        parent.keys[parent.numKeys - 1] = 0;

        // Remove right child from parent's children by shifting children to the left
        for (int i = childIndex + 2; i <= parent.numKeys; i++) {
            parent.children[i - 1] = parent.children[i];
        }
        parent.children[parent.numKeys] = null;
        parent.numKeys--;

        // Clear the right child node
        for (int i = 0; i < rightChild.numKeys; i++) {
            rightChild.keys[i] = 0;
        }
        for (int i = 0; i <= rightChild.numKeys; i++) {
            rightChild.children[i] = null;
        }
        rightChild.numKeys = 0;
    }

    private boolean isFull(BTreeNode node) {
        return node.numKeys == order - 1;
    }

    private int getLargestKey(BTreeNode node) {
        BTreeNode current = node;
        while (!current.isLeaf) {
            current = current.children[current.numKeys];
        }
        return current.keys[current.numKeys - 1];
    }

    private int getSmallestKey(BTreeNode node) {
        BTreeNode current = node;
        while (!current.isLeaf) {
            current = current.children[0];
        }
        return current.keys[0];
    }

    /**
     * Find the index of the first key in the node that is greater than or equal to
     * the given key.
     * 
     * @param node the node to search in
     * @param key  the key to find the index for
     * @return the index of the first key greater than or equal to the given key
     */
    private int findKeyIndex(BTreeNode node, int key) {
        int i = 0;
        while (i < node.numKeys && node.keys[i] < key) {
            i++;
        }
        return i;
    }
}

class BTreeNode {
    int[] keys;
    BTreeNode[] children;
    int numKeys;
    boolean isLeaf;

    public BTreeNode(int order, boolean isLeaf) {
        this.keys = new int[order - 1];
        this.children = new BTreeNode[order];
        this.numKeys = 0;
        this.isLeaf = isLeaf;
    }
}