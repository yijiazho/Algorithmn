package tree;

public class AVLTree<T> {

    private AVLNode root;

    private class AVLNode {

        private final T t;
        private int height;
        private AVLNode left;
        private AVLNode right;

        private AVLNode(T t) {
            this.t = t;
            height = 1;
        }
    }

    public void insert(T value) {
        root = insert(root, value);
    }

    /**
     * Insert a value into the AVL tree and return the new root of the subtree. If
     * the subtree is unbalanced, perform rotations to restore balance.
     * 
     * @param n the root of the subtree
     * @param v the value to insert
     * @return the new root of the subtree
     */
    private AVLNode insert(AVLNode node, T v) {
        if (node == null) {
            node = new AVLNode(v);
            return node;
        } else {
            // Compare the value to insert with the value of the current node
            int k = ((Comparable<T>) node.t).compareTo(v);
            if (k > 0) {
                node.left = insert(node.left, v);
            } else {
                node.right = insert(node.right, v);
            }
            node.height = Math.max(height(node.left), height(node.right)) + 1;
            int heightDiff = heightDiff(node);

            if (heightDiff < -1) {
                // Right subtree is taller than left subtree. Rotate left to restore balance.
                if (heightDiff(node.right) > 0) {
                    node.right = rightRotate(node.right);
                    return leftRotate(node);
                } else {
                    return leftRotate(node);
                }
            } else if (heightDiff > 1) {
                // Left subtree is taller than right subtree. Rotate right to restore balance.
                if (heightDiff(node.left) < 0) {
                    node.left = leftRotate(node.left);
                    return rightRotate(node);
                } else {
                    return rightRotate(node);
                }
            } else
                ;

        }
        return node;
    }

    /**
     * Left rotate the subtree rooted at the node n and return the new root.
     *
     * @param n the root of the subtree to rotate
     * @return the new root of the subtree after rotation
     */
    private AVLNode leftRotate(AVLNode n) {
        AVLNode r = n.right;
        n.right = r.left;
        r.left = n;
        n.height = Math.max(height(n.left), height(n.right)) + 1;
        r.height = Math.max(height(r.left), height(r.right)) + 1;
        return r;
    }

    /**
     * Right rotate the subtree rooted at the node n and return the new root.
     * 
     * @param n the root of the subtree to rotate
     * @return the new root of the subtree after rotation
     */
    private AVLNode rightRotate(AVLNode n) {
        AVLNode r = n.left;
        n.left = r.right;
        r.right = n;
        n.height = Math.max(height(n.left), height(n.right)) + 1;
        r.height = Math.max(height(r.left), height(r.right)) + 1;
        return r;
    }

    private int heightDiff(AVLNode a) {
        if (a == null) {
            return 0;
        }
        return height(a.left) - height(a.right);
    }

    private int height(AVLNode a) {
        if (a == null) {
            return 0;
        }
        return a.height;
    }

}