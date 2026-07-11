package tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Traversal {

    /**
     * Build the tree from inorder and postorder traversal
     * 
     * @param inorder   the inorder traversal of the tree
     * @param postorder the postorder traversal of the tree
     * @return the root of the tree
     */
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        int len = inorder.length;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < len; i++) {
            map.put(inorder[i], i);
        }
        return build(inorder, 0, len - 1, postorder, 0, len - 1, map);
    }

    /**
     * Build the tree from inorder and postorder traversal
     * 
     * @param inorder   the inorder traversal of the tree
     * @param inStart   the starting index of the inorder traversal
     * @param inEnd     the ending index of the inorder traversal
     * @param postorder the postorder traversal of the tree
     * @param postStart the starting index of the postorder traversal
     * @param postEnd   the ending index of the postorder traversal
     * @param map       a map from node value to its index in the inorder traversal
     * @return the root of the tree
     */
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

    /**
     * Vertical order traversal of a binary tree, from top to bottom, left to right.
     * Each vertical order is a list of integers.
     * 
     * @param root the root of the binary tree
     * @return a list of lists of integers, each list representing a vertical order
     */
    public List<List<Integer>> verticalOrderTraversal(TreeNode root) {
        // Key is the x coordinate, value is a list of integers from top to bottom, left
        // to right
        Map<Integer, List<Integer>> map = new HashMap<>();
        Queue<NodeWithCoordinate> queue = new LinkedList<>();

        queue.offer(new NodeWithCoordinate(root, 0, 0));
        levelOrderTraverse(queue, map);

        List<List<Integer>> result = new ArrayList<>();
        // Sort the keys
        List<Integer> keys = new ArrayList<>(map.keySet());
        Collections.sort(keys);
        for (int key : keys) {
            result.add(map.get(key));
        }
        return result;
    }

    private void traverse(TreeNode root, int x, int y, Map<Integer, List<Integer>> map) {
        if (root == null) {
            return;
        }

        map.computeIfAbsent(x, k -> new ArrayList<>()).add(root.val);
        traverse(root.left, x - 1, y + 1, map);
        traverse(root.right, x + 1, y + 1, map);
    }

    private void levelOrderTraverse(Queue<NodeWithCoordinate> queue, Map<Integer, List<Integer>> map) {

        while (!queue.isEmpty()) {
            NodeWithCoordinate current = queue.poll();
            map.computeIfAbsent(current.x, k -> new ArrayList<>()).add(current.node.val);
            if (current.node.left != null) {
                queue.offer(new NodeWithCoordinate(current.node.left, current.x - 1, current.y + 1));
            }
            if (current.node.right != null) {
                queue.offer(new NodeWithCoordinate(current.node.right, current.x + 1, current.y + 1));
            }
        }

    }

    private class NodeWithCoordinate {
        TreeNode node;
        int x;
        int y;

        NodeWithCoordinate(TreeNode node, int x, int y) {
            this.node = node;
            this.x = x;
            this.y = y;
        }
    }
}
