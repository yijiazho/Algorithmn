package tree;

import java.util.HashMap;
import java.util.Map;

class TreeNode {
    // start and end index of the segment
    int start, end;
    // left, right child
    TreeNode left, right;
    // longest repeating characters in [start, end]
    int longestRepeating;
    // longest repeating from [start, startRightBound]
    int startRightBound;
    // longest repeating from [endLeftBound, end]
    int endLeftBound;


    public TreeNode(int start, int end) {
        this.start = start;
        this.end = end;
    }
}

public class SegmentTree {

    public int[] longestRepeating(String s, String queryCharacters, int[] queryIndices) {
        char[] chars = s.toCharArray();
        int n = queryIndices.length;
        int[] res = new int[n];

        // build tree recursively
        TreeNode root = buildTree(chars, 0, s.length() - 1);

        // in each query, update if necessary
        // only update one branch from top
        for (int i = 0; i < n; i++) {
            char c = queryCharacters.charAt(i);
            int index = queryIndices[i];

            if (chars[index] != c) {
                chars[index] = c;
                updateTree(root, index, chars);
            }
            res[i] = root.longestRepeating;
        }

        return res;
    }

    // build a balanced tree inorder
    private TreeNode buildTree(char[] chars, int start, int end) {
        TreeNode root = new TreeNode(start, end);

        if (start == end) {
            root.longestRepeating = 1;
            root.startRightBound = start;
            root.endLeftBound = start;
            return root;
        }

        int mid = start + (end - start) / 2;

        TreeNode left = buildTree(chars, start, mid);
        TreeNode right = buildTree(chars, mid + 1, end);

        root.left = left;
        root.right = right;
        root.endLeftBound = right.endLeftBound;
        root.startRightBound = left.startRightBound;

        if (chars[mid] == chars[mid + 1]) {
            // calculate the length, including the char at mid and mid + 1
            int length = 0;

            // all [start, mid] are the same char, try to concat
            // the right of [mid + 1, startRightBound]
            if (left.startRightBound == mid) {
                root.startRightBound = right.startRightBound;
            }

            // similarly, all [mid + 1, end] are same char, concat to left
            // [endLeftBound, mid]
            if (right.endLeftBound == mid + 1) {
                root.endLeftBound = left.endLeftBound;
            }
        }

        // 4 options, 1. longest in left, 2. longest in right
        // 3. [start, startRightEnd], 4.[endLeftBound, end]
        root.longestRepeating =
                Math.max(root.startRightBound - root.start + 1,
                        Math.max(root.end - root.endLeftBound + 1,
                                Math.max(left.longestRepeating, right.longestRepeating)));

        return root;
    }

    // update the segment tree, only in one branch
    private void updateTree(TreeNode root, int index, char[] chars) {
        // leaf node, start == end == index
        if (root.start == root.end) {
            root.longestRepeating = 1;
            root.startRightBound = index;
            root.endLeftBound = index;
            return;
        }

        int mid = root.start + (root.end - root.start) / 2;

        if (index >= root.start && index <= mid) {
            // update left tree recursively
            updateTree(root.left, index, chars);

            // update the variables of root
            if (chars[mid] == chars[mid + 1]) {
                // all [start, mid] are the same char, try to concat
                // the right of [mid + 1, startRightBound]
                if (root.left.endLeftBound == mid) {
                    root.endLeftBound = root.right.endLeftBound;
                }
                root.longestRepeating =
                        Math.max(root.longestRepeating,
                                Math.max(root.left.longestRepeating, root.endLeftBound - root.start + 1));
            }
        } else {
            // update right tree recursively
            updateTree(root.right, index, chars);

            // update the variables of root
            if (chars[mid] == chars[mid + 1]) {
                // all [mid + 1, end] are the same char, try to concat
                // the left of [endLeftBound, mid]
                if (root.right.startRightBound == mid + 1) {
                    root.startRightBound = root.left.startRightBound;
                }
                root.longestRepeating =
                        Math.max(root.longestRepeating,
                                Math.max(root.right.longestRepeating, root.end - root.startRightBound + 1));
            }
        }

    }



    public static final void main(String[] args) {
        SegmentTree instance = new SegmentTree();
        String s = "babacc";
        String chars = "bcb";
        int[] indices = new int[]{1,3,3};

        int[] res = instance.longestRepeating(s,chars, indices);

        for (int n: res) {
            System.out.println(n);
        }
    }

}
