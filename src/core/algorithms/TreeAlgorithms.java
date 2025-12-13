package core.algorithms;

import core.structures.TreeNode;
import core.structures.BinarySearchTree;

public class TreeAlgorithms {

    public static String inorder(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        inorderRec(root, sb);
        return sb.length() > 0 ? sb.toString().trim() : "Empty";
    }

    private static void inorderRec(TreeNode root, StringBuilder sb) {
        if (root != null) {
            inorderRec(root.left, sb);
            sb.append(root.value).append(" ");
            inorderRec(root.right, sb);
        }
    }

    public static String preorder(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        preorderRec(root, sb);
        return sb.length() > 0 ? sb.toString().trim() : "Empty";
    }

    private static void preorderRec(TreeNode root, StringBuilder sb) {
        if (root != null) {
            sb.append(root.value).append(" ");
            preorderRec(root.left, sb);
            preorderRec(root.right, sb);
        }
    }

    public static String postorder(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        postorderRec(root, sb);
        return sb.length() > 0 ? sb.toString().trim() : "Empty";
    }

    private static void postorderRec(TreeNode root, StringBuilder sb) {
        if (root != null) {
            postorderRec(root.left, sb);
            postorderRec(root.right, sb);
            sb.append(root.value).append(" ");
        }
    }
}
