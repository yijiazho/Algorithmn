package tree;

class SegmentTreeNode {
    // start and end index of the segment
    int start, end;
    // left, right child
    SegmentTreeNode left, right;
    // longest repeating characters in [start, end]
    int longestRepeating;
    // right most index where [start, index] are the same character
    int endIndexOfLongestRepeatingFromStart;
    // left most index where [index, end] are the same character
    int startIndexOfLongestRepeatingFromEnd;

    public SegmentTreeNode(int start, int end) {
        this.start = start;
        this.end = end;
    }
}

/**
 * 
 * SegmentTree is a data structure that allows for efficient updates and queries
 * on an array. In this implementation, we use a segment tree to efficiently
 * execute a series of queries that update the character in a string and
 * calculate the length of the longest substring consisting of one character
 * only.
 */
public class SegmentTree {

    /**
     * Given an string s, and another string queryCharacters of length k and array
     * of indicies of length k. So we have k queries. Each query[i] updates the
     * character in the original string s, of queryIndicies[i], to character
     * queryCharacters[i]. Find out the length of longest substring in original
     * string s, consisting only one repeating characters, after each single query
     * 
     * @param s               original string, non empty
     * @param queryCharacters string of length k, representing the character to
     *                        update to in the ith query
     * @param queryIndices    array of length k, representing the index to update in
     *                        the ith query
     * @return the length of longest substring, after each query
     */
    public int[] findLongestRepeating(String s, String queryCharacters, int[] queryIndices) {
        char[] chars = s.toCharArray();
        int n = queryIndices.length;
        int[] res = new int[n];

        // build tree recursively
        SegmentTreeNode root = buildTree(chars, 0, s.length() - 1);

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

    private SegmentTreeNode buildTree(char[] chars, int start, int end) {
        SegmentTreeNode root = new SegmentTreeNode(start, end);

        if (start == end) {
            root.longestRepeating = 1;
            root.endIndexOfLongestRepeatingFromStart = start;
            root.startIndexOfLongestRepeatingFromEnd = start;
            return root;
        }

        int mid = start + (end - start) / 2;

        SegmentTreeNode left = buildTree(chars, start, mid);
        SegmentTreeNode right = buildTree(chars, mid + 1, end);

        root.left = left;
        root.right = right;
        recompute(root, chars);

        return root;
    }

    // update the segment tree, only in one branch
    private void updateTree(SegmentTreeNode root, int index, char[] chars) {
        // leaf node, start == end == index
        if (root.start == root.end) {
            root.longestRepeating = 1;
            root.endIndexOfLongestRepeatingFromStart = index;
            root.startIndexOfLongestRepeatingFromEnd = index;
            return;
        }

        int mid = root.start + (root.end - root.start) / 2;

        if (index >= root.start && index <= mid) {
            updateTree(root.left, index, chars);
        } else {
            updateTree(root.right, index, chars);
        }

        recompute(root, chars);
    }

    /**
     * Recompute the cached values of the current node.
     * 
     * @param root  the current node to recompute
     * @param chars the modified character array
     */
    private void recompute(SegmentTreeNode root, char[] chars) {
        SegmentTreeNode left = root.left;
        SegmentTreeNode right = root.right;
        int mid = left.end;

        root.longestRepeating = 1;
        root.endIndexOfLongestRepeatingFromStart = left.endIndexOfLongestRepeatingFromStart;
        root.startIndexOfLongestRepeatingFromEnd = right.startIndexOfLongestRepeatingFromEnd;

        if (chars[mid] == chars[mid + 1]) {
            if (left.endIndexOfLongestRepeatingFromStart == mid) {
                root.endIndexOfLongestRepeatingFromStart = right.endIndexOfLongestRepeatingFromStart;
            }

            if (right.startIndexOfLongestRepeatingFromEnd == mid + 1) {
                root.startIndexOfLongestRepeatingFromEnd = left.startIndexOfLongestRepeatingFromEnd;
            }

            root.longestRepeating = Math.max(root.longestRepeating,
                    right.endIndexOfLongestRepeatingFromStart - left.startIndexOfLongestRepeatingFromEnd + 1);
        }

        root.longestRepeating = Math.max(root.longestRepeating,
                Math.max(left.longestRepeating, right.longestRepeating));
    }
}
