package tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TreeSum {

    /**
     * Find all path between any two nodes in a tree that has sum add up to target
     * Using preorder
     * 
     * @param root   root node
     * @param target target integer value
     * @return all the paths, represented by a list of node values
     */
    public List<List<Integer>> findPathSum(TreeNode root, int target) {
        if (root == null) {
            return null;
        }

        List<List<Integer>> result = new ArrayList<>();
        findAllSumStartingFromNode(root, result, target);
        return result;
    }

    private List<ListAndSum> findAllSumStartingFromNode(TreeNode root, List<List<Integer>> result, int target) {
        List<ListAndSum> list = new ArrayList<>();
        if (root == null) {
            return list;
        }

        // first element in list is the total sum, starting from the second is the
        // actual list
        if (root.left == null && root.right == null) {
            ListAndSum singleElement = new ListAndSum();
            singleElement.append(root.val);
            list.add(singleElement);
            return list;
        }

        List<ListAndSum> leftChild = findAllSumStartingFromNode(root.left, result, target);
        List<ListAndSum> rightChild = findAllSumStartingFromNode(root.right, result, target);
        List<ListAndSum> allPathSumFromRoot = new ArrayList<>();

        // left + root
        for (ListAndSum leftList : leftChild) {
            if (leftList.sum + root.val == target) {
                List<Integer> returnList = new ArrayList<>();
                returnList.add(root.val);
                returnList.addAll(leftList.list);
                result.add(returnList);
            }

            ListAndSum newList = new ListAndSum(leftList.list, leftList.sum);
            newList.addToHead(root.val);
            allPathSumFromRoot.add(newList);
        }

        // right + root
        for (ListAndSum rightList : rightChild) {
            if (rightList.sum + root.val == target) {
                List<Integer> returnList = new ArrayList<>();
                returnList.add(root.val);
                returnList.addAll(rightList.list);
                result.add(returnList);
            }

            ListAndSum newList = new ListAndSum(rightList.list, rightList.sum);
            newList.addToHead(root.val);
            allPathSumFromRoot.add(newList);
        }

        // left + root + right
        for (ListAndSum leftList : leftChild) {
            for (ListAndSum rightList : rightChild) {
                if (leftList.sum + root.val + rightList.sum == target) {
                    List<Integer> resultList = new ArrayList<>();
                    resultList.add(root.val);
                    resultList.addAll(leftList.list);
                    resultList.addAll(rightList.list);
                    result.add(resultList);
                }
            }
        }
        List<Integer> pathList = new ArrayList<>();
        pathList.add(root.val);
        allPathSumFromRoot.add(new ListAndSum(pathList, root.val));
        return allPathSumFromRoot;
    }

    private class ListAndSum {
        List<Integer> list;
        int sum;

        public ListAndSum() {
            list = new LinkedList<>();
            sum = 0;
        }

        public ListAndSum(List<Integer> list, int sum) {
            this.list = new LinkedList<>();
            this.list.addAll(list);
            this.sum = sum;
        }

        public void append(int value) {
            list.add(value);
            sum += value;
        }

        public void addToHead(int value) {
            list.add(0, value);
            sum += value;
        }
    }
}
