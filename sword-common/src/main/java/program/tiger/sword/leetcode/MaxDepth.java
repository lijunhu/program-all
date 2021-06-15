package program.tiger.sword.leetcode;

import java.util.LinkedList;
import java.util.Queue;

public class MaxDepth {

    public static class Node<T> {
        private T data;
        private MaxDepth.Node<T> leftChild;
        private MaxDepth.Node<T> rightChild;

        public Node() {
        }

        public Node(T data) {
            this.data = data;
        }

        public Node(T data, MaxDepth.Node<T> leftChild, MaxDepth.Node<T> rightChild) {
            this.data = data;
            this.leftChild = leftChild;
            this.rightChild = rightChild;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public MaxDepth.Node<T> getLeftChild() {
            return leftChild;
        }

        public void setLeftChild(MaxDepth.Node<T> leftChild) {
            this.leftChild = leftChild;
        }

        public MaxDepth.Node<T> getRightChild() {
            return rightChild;
        }

        public void setRightChild(MaxDepth.Node<T> rightChild) {
            this.rightChild = rightChild;
        }

    }


    public <T> Integer depthSolution(Node<T> root) {
        if (root == null) {
            return 0;
        }

        return Math.max(depthSolution(root.leftChild), depthSolution(root.rightChild)) + 1;
    }


    public <T> Integer breadthSolution(Node<T> root) {

        int depth = 0;
        if (root == null) {
            return depth;
        }

        Queue<Node<T>> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {

            for (int i = queue.size(); i >0; i--) {
                Node<T> node = queue.poll();
                if(node.getLeftChild() != null){
                    queue.offer(node.getLeftChild());
                }
                if (node.getRightChild() != null){
                    queue.offer(node.getRightChild());
                }
            }
            depth++;
        }

        return depth;
    }
}
