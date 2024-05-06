package tree;

import utility.TreeNode;

public class CountNodes {
    private int totalNodes;

    public int equalToDescendants(TreeNode root) {
        totalNodes = 0;
        totalSum(root);
        return totalNodes;
    }

    private long totalSum(TreeNode root) {
        if (root == null) {
            return 0L;
        }

        long left = totalSum(root.left);
        long right = totalSum(root.right);

        if (root.val == left + right) {
            totalNodes++;
        }
        return root.val + left + right;
    }

    public static final void main(String[] args) {

    }
}
