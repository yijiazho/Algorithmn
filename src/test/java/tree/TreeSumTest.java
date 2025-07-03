package tree;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TreeSumTest {

    private TreeSum treeSum;

    @BeforeEach
    public void setup() {
        treeSum = new TreeSum();
    }

    @Test
    public void testFindPathSum() {
        TreeNode treeNode1 = new TreeNode(2);
        TreeNode treeNode2 = new TreeNode(3);
        TreeNode treeNode3 = new TreeNode(-1);
        TreeNode treeNode4 = new TreeNode(1);
        TreeNode treeNode5 = new TreeNode(5);
        TreeNode treeNode6 = new TreeNode(4);
        TreeNode treeNode7 = new TreeNode(-1);

        treeNode1.left = treeNode2;
        treeNode1.right = treeNode3;
        treeNode2.left = treeNode4;
        treeNode2.right = treeNode5;
        treeNode3.left = treeNode6;
        treeNode3.right = treeNode7;

        List<List<Integer>> result = treeSum.findPathSum(treeNode1, 5);
        List<List<Integer>> expected = List.of(
                List.of(2, 3),
                List.of(2, -1, 4),
                List.of(2, 3, 1, -1));
    }
}
