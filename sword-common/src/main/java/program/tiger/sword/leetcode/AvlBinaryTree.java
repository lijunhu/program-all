package program.tiger.sword.leetcode;

public class AvlBinaryTree<T> {


    private Node<T> root;

    public AvlBinaryTree(Node<T> root) {
        this.root = root;
    }


    public boolean isBalanced() {
        int lHigh, rHigh;
        Node<T> left = root.leftChild, right = root.rightChild;
        while (left != null ) {

            left = left.leftChild;
            right = right.rightChild;
        }
        return false;
    }


    public static class Node<T> {
        private T data;
        private AvlBinaryTree.Node<T> parent;
        private AvlBinaryTree.Node<T> leftChild;
        private AvlBinaryTree.Node<T> rightChild;

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public AvlBinaryTree.Node<T> getParent() {
            return parent;
        }

        public void setParent(AvlBinaryTree.Node<T> parent) {
            this.parent = parent;
        }

        public AvlBinaryTree.Node<T> getLeftChild() {
            return leftChild;
        }

        public void setLeftChild(AvlBinaryTree.Node<T> leftChild) {
            this.leftChild = leftChild;
        }

        public AvlBinaryTree.Node<T> getRightChild() {
            return rightChild;
        }

        public void setRightChild(AvlBinaryTree.Node<T> rightChild) {
            this.rightChild = rightChild;
        }
    }
}
