package tree;

import java.util.HashMap;
import java.util.Map;

public class Traversal {

    public TreeNode buildTree(int[] inorder, int[] postorder) {
        int len = inorder.length;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < len; i++) {
            map.put(inorder[i], i);
        }
        return build(inorder, 0, len - 1, postorder, 0, len - 1, map);
    }

    private TreeNode build(int[] inorder, int inStart, int inEnd,
            int[] postorder, int postStart, int postEnd, Map<Integer, Integer> map) {

        if (inStart > inEnd || postStart > postEnd) {
            return null;
        }

        int rootVal = postorder[postEnd];
        TreeNode root = new TreeNode(rootVal);

        int rootInInOrder = map.get(rootVal);
        int lLeft = rootInInOrder - inStart;
        int lRight = inEnd - rootInInOrder;

        TreeNode leftChild = build(inorder, inStart, rootInInOrder - 1,
                postorder, postStart, postStart + lLeft - 1, map);
        TreeNode rightChild = build(inorder, rootInInOrder + 1, inEnd,
                postorder, postEnd - lRight, postEnd - 1, map);

        root.left = leftChild;
        root.right = rightChild;
        return root;
    }

}
