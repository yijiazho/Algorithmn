package tree;

public class NumberOfSmaller {

    /**
     * Find the number of smaller elements to the left of each element in the array.
     * 
     * @param nums the input array of integers
     * @return an array where each element at index i represents the count of
     *         smaller elements to the left of nums[i]
     */
    public int[] numberOfSmallerOnTheLeft(int[] nums) {
        int n = nums.length;
        int[] res = new int[n];
        TreeNode root = null;

        for (int i = 0; i < n; i++) {
            res[i] = countSmaller(root, nums[i]);
            root = insert(root, nums[i]);
        }

        return res;
    }

    /**
     * Insert a value into the binary search tree and return the root of the tree
     * 
     * @param root The root of the binary search tree
     * @param val  the value to be inserted into the binary search tree
     * @return the root of the binary search tree after insertion
     */
    private TreeNode insert(TreeNode root, int val) {
        if (root == null) {
            return new TreeNode(val);
        }

        if (root.val == val) {
            root.selfCount++;
        } else if (root.val > val) {
            root.leftCount++;
            root.left = insert(root.left, val);
        } else {
            root.right = insert(root.right, val);
        }
        return root;
    }

    private int countSmaller(TreeNode root, int val) {
        if (root == null) {
            return 0;
        }

        if (root.val == val) {
            return root.leftCount;
        } else if (root.val > val) {
            return countSmaller(root.left, val);
        } else {
            return root.leftCount + root.selfCount + countSmaller(root.right, val);
        }
    }

    private class TreeNode {
        int val;
        int selfCount;
        // number of nodes in the left subtree
        int leftCount;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
            this.selfCount = 1;
            this.leftCount = 0;
            this.left = null;
            this.right = null;
        }
    }
}