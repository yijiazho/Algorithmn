package tree;

/**
 * Implementation of a Red-Black Tree data structure. Red-Black Trees are a type
 * of self-balancing binary search tree where each node has an extra bit for
 * denoting the color of the node, either red or black. A Red-Black Tree
 * satisfies the following properties:
 * 1. Each node is either red or black.
 * 2. The root is always black.
 * 3. All leaves (NIL nodes) are black.
 * 4. If a node is red, then both its children must be black (no two red nodes
 * can be adjacent).
 * 5. Every path from a node to its descendant NIL nodes must have the same
 * number of black nodes.
 */
public class RedBlackTree {

    private RedBlackTreeNode root;
    // Sentinel node to represent null leaves
    private RedBlackTreeNode NIL;

    public RedBlackTree() {
        NIL = new RedBlackTreeNode(0);
        NIL.isRed = false;
        NIL.left = NIL;
        NIL.right = NIL;
        root = NIL;
    }

    /**
     * Inserts a new value into the Red-Black Tree. If the value already exists, it
     * will return false. After insertion, the tree will be rebalanced to maintain
     * the Red-Black Tree properties.
     * 
     * @param value The value to be inserted into the tree.
     * @return true if the insertion is successful, false if the value already
     *         exists.
     */
    public boolean insert(int value) {
        RedBlackTreeNode node = new RedBlackTreeNode(value);
        node.left = NIL;
        node.right = NIL;
        node.parent = null;

        RedBlackTreeNode cur = root;
        RedBlackTreeNode parent = null;
        // Insert
        while (cur != NIL) {
            parent = cur;
            if (node.value < cur.value) {
                cur = cur.left;
            } else if (node.value > cur.value) {
                cur = cur.right;
            } else {
                // Value already exists in the tree
                return false;
            }
        }

        // Set parent of the node
        node.parent = parent;
        if (parent == null) {
            root = node;
        } else if (node.value < parent.value) {
            parent.left = node;
        } else {
            parent.right = node;
        }

        // Fix Red-Black Tree properties after insertion
        fixInsert(node);
        return true;
    }

    /**
     * Deletes a value from the Red-Black Tree. After deletion, the tree will be
     * rebalanced to maintain the Red-Black Tree properties. If the value does not
     * exist in the tree, it will return false.
     * 
     * @param value The value to be deleted from the tree.
     * @return true if the deletion is successful, false if the value does not exist
     *         in the tree.
     */
    public boolean delete(int value) {
        RedBlackTreeNode node = root;
        while (node != NIL) {
            if (value < node.value) {
                node = node.left;
            } else if (value > node.value) {
                node = node.right;
            } else {
                deleteNode(node);
                return true;
            }
        }
        return false;
    }

    /**
     * Search for target value in the Red-Black Tree.
     * 
     * @param value The value to search for in the tree.
     * @return true if the value is found, false otherwise.
     */
    public boolean search(int value) {
        RedBlackTreeNode cur = root;
        while (cur != NIL) {
            if (value < cur.value) {
                cur = cur.left;
            } else if (value > cur.value) {
                cur = cur.right;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * Performs a left rotation on the node. Assume the node has right child. After
     * the rotation, the right child becomes the parent of the node, and the node
     * becomes its left child.
     * 
     * @param node The node to perform the left rotation on.
     */
    public void leftRotate(RedBlackTreeNode node) {
        RedBlackTreeNode rightChild = node.right;
        node.right = rightChild.left;

        if (rightChild.left != NIL) {
            rightChild.left.parent = node;
        }

        rightChild.parent = node.parent;
        if (node.parent == null) {
            root = rightChild;
        } else if (node == node.parent.left) {
            node.parent.left = rightChild;
        } else {
            node.parent.right = rightChild;
        }

        rightChild.left = node;
        node.parent = rightChild;
    }

    /**
     * Performs a right rotation on the node. Assume the node has left child. After
     * the rotation, the left child becomes the parent of the node, and the node
     * becomes its right child.
     * 
     * @param node The node to perform the right rotation on.
     */
    public void rightRotate(RedBlackTreeNode node) {
        RedBlackTreeNode leftChild = node.left;
        node.left = leftChild.right;

        if (leftChild.right != NIL) {
            leftChild.right.parent = node;
        }

        leftChild.parent = node.parent;
        if (node.parent == null) {
            root = leftChild;
        } else if (node == node.parent.left) {
            node.parent.left = leftChild;
        } else {
            node.parent.right = leftChild;
        }

        leftChild.right = node;
        node.parent = leftChild;
    }

    private void fixInsert(RedBlackTreeNode node) {

        // As long as the parent of the node is red, we have a violation of the
        // Red-Black Tree properties because node is red by default.
        while (node != root && node.parent.isRed) {

            if (node.parent == node.parent.parent.left) {
                // Parent is a left child
                RedBlackTreeNode uncle = node.parent.parent.right;
                if (uncle.isRed) {
                    // Uncle is red, recolor parent uncle to black, grandparent to red
                    node.parent.isRed = false;
                    uncle.isRed = false;
                    node.parent.parent.isRed = true;

                    // Move up the tree as node and parent are not violating the properties anymore
                    node = node.parent.parent;
                } else {
                    // Uncle is black
                    if (node == node.parent.right) {
                        // Node is a right child, perform left rotation on parent
                        node = node.parent;
                        leftRotate(node);
                    }
                    // Now node is a left child, perform right rotation on grandparent
                    node.parent.isRed = false;
                    node.parent.parent.isRed = true;
                    rightRotate(node.parent.parent);
                }
            } else {
                // Parent is a right child
                RedBlackTreeNode uncle = node.parent.parent.left;
                if (uncle.isRed) {
                    // Uncle is red, recolor parent uncle to black, grandparent to red
                    node.parent.isRed = false;
                    uncle.isRed = false;
                    node.parent.parent.isRed = true;

                    // Move up the tree as node and parent are not violating the properties anymore
                    node = node.parent.parent;
                } else {
                    // Uncle is black
                    if (node == node.parent.left) {
                        // Node is a left child, perform right rotation on parent
                        node = node.parent;
                        rightRotate(node);
                    }
                    // Now node is a right child, perform left rotation on grandparent
                    node.parent.isRed = false;
                    node.parent.parent.isRed = true;
                    leftRotate(node.parent.parent);
                }
            }
        }
        // Root is always black
        root.isRed = false;
    }

    private void deleteNode(RedBlackTreeNode node) {
        RedBlackTreeNode y = node;
        RedBlackTreeNode x;
        boolean yOriginalColor = y.isRed;

        if (node.left == NIL) {
            x = node.right;
            transplant(node, node.right);
        } else if (node.right == NIL) {
            x = node.left;
            transplant(node, node.left);
        } else {
            y = minimum(node.right);
            yOriginalColor = y.isRed;
            x = y.right;

            if (y.parent == node) {
                x.parent = y;
            } else {
                transplant(y, y.right);
                y.right = node.right;
                y.right.parent = y;
            }

            transplant(node, y);
            y.left = node.left;
            y.left.parent = y;
            y.isRed = node.isRed; // Preserve the color of the deleted node
        }

        if (!yOriginalColor) {
            fixDelete(x);
        }
    }

    /**
     * Transplants node u with node v in the tree. This method is used during
     * deletion to replace one subtree with another.
     * 
     * @param u The node to be replaced.
     * @param v The node to replace u with.
     */
    private void transplant(RedBlackTreeNode u, RedBlackTreeNode v) {
        if (u.parent == null) {
            root = v;
        } else if (u == u.parent.left) {
            u.parent.left = v;
        } else {
            u.parent.right = v;
        }
        v.parent = u.parent;
    }

    /**
     * Finds the node with the minimum value in the subtree rooted at the given
     * node.
     * 
     * @param node The root of the subtree to search for the minimum value.
     * @return The node with the minimum value in the subtree.
     */
    private RedBlackTreeNode minimum(RedBlackTreeNode node) {
        while (node.left != NIL) {
            node = node.left;
        }
        return node;
    }

    /**
     * Fixes the Red-Black Tree properties after a deletion. This method is called
     * when a black node is deleted, which may violate the properties of the tree.
     *
     * @param node The node to start fixing from.
     */
    private void fixDelete(RedBlackTreeNode node) {
        // As long as node is not the root and node is black, we have a violation of the
        // Red-Black Tree properties.
        while (node != root && !node.isRed) {
            if (node == node.parent.left) {
                // node is a left child
                RedBlackTreeNode uncle = node.parent.right;
                if (uncle.isRed) {
                    // Uncle is red, recolor and perform left rotation on parent
                    uncle.isRed = false;
                    node.parent.isRed = true;
                    leftRotate(node.parent);
                    uncle = node.parent.right;
                }
                if (!uncle.left.isRed && !uncle.right.isRed) {
                    // Both children of uncle are black, recolor uncle to red and move up the tree
                    uncle.isRed = true;
                    node = node.parent;
                } else {
                    // At least one child of uncle is red
                    if (!uncle.right.isRed) {
                        // Right child of uncle is black, perform right rotation on uncle
                        uncle.left.isRed = false;
                        uncle.isRed = true;
                        rightRotate(uncle);
                        uncle = node.parent.right;
                    }

                    // Now uncle's right child is red, perform left rotation on parent
                    uncle.isRed = node.parent.isRed;
                    node.parent.isRed = false;
                    uncle.right.isRed = false;
                    leftRotate(node.parent);
                    // Exit the loop
                    node = root;
                }
            } else {
                // node is a right child
                RedBlackTreeNode uncle = node.parent.left;
                if (uncle.isRed) {
                    // Uncle is red, recolor and perform right rotation on parent
                    uncle.isRed = false;
                    node.parent.isRed = true;
                    rightRotate(node.parent);
                    uncle = node.parent.left;
                }
                if (!uncle.right.isRed && !uncle.left.isRed) {
                    // Both children of uncle are black, recolor uncle to red and move up the tree
                    uncle.isRed = true;
                    node = node.parent;
                } else {
                    // At least one child of uncle is red
                    if (!uncle.left.isRed) {
                        // Left child of uncle is black, perform left rotation on uncle
                        uncle.right.isRed = false;
                        uncle.isRed = true;
                        leftRotate(uncle);
                        uncle = node.parent.left;
                    }
                    // Now uncle's left child is red, perform right rotation on parent
                    uncle.isRed = node.parent.isRed;
                    node.parent.isRed = false;
                    uncle.left.isRed = false;
                    rightRotate(node.parent);
                    // Exit the loop
                    node = root;
                }
            }
        }
        // Ensure the root is always black
        node.isRed = false;
    }

    private class RedBlackTreeNode {
        int value;
        // true for red, false for black
        boolean isRed;
        RedBlackTreeNode left;
        RedBlackTreeNode right;
        RedBlackTreeNode parent;

        public RedBlackTreeNode(int value) {
            this.value = value;
            // New nodes are red by default
            this.isRed = true;
            this.left = null;
            this.right = null;
            this.parent = null;
        }
    }

}
