package program.tiger.sword.leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class LevelOrder {


    public static class Node<T> {
        private T data;
        private LevelOrder.Node<T> leftChild;
        private LevelOrder.Node<T> rightChild;

        public Node() {
        }

        public Node(T data) {
            this.data = data;
        }

        public Node(T data, LevelOrder.Node<T> leftChild, LevelOrder.Node<T> rightChild) {
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

        public LevelOrder.Node<T> getLeftChild() {
            return leftChild;
        }

        public void setLeftChild(LevelOrder.Node<T> leftChild) {
            this.leftChild = leftChild;
        }

        public LevelOrder.Node<T> getRightChild() {
            return rightChild;
        }

        public void setRightChild(LevelOrder.Node<T> rightChild) {
            this.rightChild = rightChild;
        }
    }


    public <T> List<List<T>> solution(Node<T> root) {

        List<List<T>> ret = new ArrayList<>(10);
        if (root == null) {
            return ret;
        }
        Queue<Node<T>> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            List<T> level = new ArrayList<>(10);
            int currLevel = queue.size();
            for (int i = 0; i < currLevel; i++) {
                Node<T> node = queue.poll();
                if (node == null) {
                    level.add(null);
                    continue;
                }
                level.add(node.getData());
                if (node.getLeftChild() != null) {
                    queue.offer(node.getLeftChild());
                }
                if (node.getRightChild() != null) {
                    queue.offer(node.getRightChild());
                }
            }
            ret.add(level);
        }
        return ret;
    }


}
