package tree;

import java.util.Objects;

/**
 * Created by PaulZhang on 2016/3/31.
 */
public class Test {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        TreeNode left = new TreeNode(2);
        TreeNode right = new TreeNode(3);

        root.setLeft(left);
        root.setRight(right);

        System.out.println("root.getLeft() = " + root.getLeft().getValue());
        System.out.println("root.getRight() = " + root.getRight().getValue());

        Test test = new Test();
        root = test.invertTree(root);

        System.out.println("root.getLeft() = " + root.getLeft().getValue());
        System.out.println("root.getRight() = " + root.getRight().getValue());

        int[] nums = {1};
        int target = 1;
        System.out.println(test(nums, target));
    }

    public TreeNode invertTree(TreeNode node) {
        if (Objects.nonNull(node)) {
            node.setLeft(invertTree(node.getLeft()));
            node.setRight(invertTree(node.getRight()));

            TreeNode tempNode = node.getLeft();
            node.setLeft(node.getRight());
            node.setRight(tempNode);
            return node;
        } else {
            return null;
        }


    }

    public static int test(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target) {
                return i;
            } else {
                continue;
            }
        }
        return -1;
    }

    public int maxPathSum(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int result = 0;

        return result;
    }
}
