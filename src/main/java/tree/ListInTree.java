package tree;

import utility.ListNode;

public class ListInTree {

    public boolean isSubPath(ListNode head, TreeNode root) {
        if (root == null) {
            return head == null;
        }
        if (head == null) {
            return true;
        }

        // if matches
        if (head.val == root.val) {
            if (isSubPath(head.next, root.left) || isSubPath(head.next, root.right)) {
                return true;
            }
        }

        if (isSubPath(head, root.left) || isSubPath(head, root.right)) {
            return true;
        }
        return false;
    }

    public static final void main(String[] args) {

    }

}
