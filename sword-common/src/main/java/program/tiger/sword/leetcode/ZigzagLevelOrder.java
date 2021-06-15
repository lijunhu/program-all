package program.tiger.sword.leetcode;

import java.util.*;

public class ZigzagLevelOrder {

    public static class Node<T> {
        private T data;
        private ZigzagLevelOrder.Node<T> leftChild;
        private ZigzagLevelOrder.Node<T> rightChild;

        public Node() {
        }

        public Node(T data) {
            this.data = data;
        }

        public Node(T data, ZigzagLevelOrder.Node<T> leftChild, ZigzagLevelOrder.Node<T> rightChild) {
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

        public ZigzagLevelOrder.Node<T> getLeftChild() {
            return leftChild;
        }

        public void setLeftChild(ZigzagLevelOrder.Node<T> leftChild) {
            this.leftChild = leftChild;
        }

        public ZigzagLevelOrder.Node<T> getRightChild() {
            return rightChild;
        }

        public void setRightChild(ZigzagLevelOrder.Node<T> rightChild) {
            this.rightChild = rightChild;
        }

    }

    public <T> List<List<T>> solution(Node<T> root) {

        List<List<T>> ret = new ArrayList<>();
        if (root == null) {
            return ret;
        }


        Queue<Node<T>> queue = new LinkedList<>();
        queue.offer(root);
        boolean isOrderLeft = true;
        while (!queue.isEmpty()) {
            Deque<T> level = new LinkedList<>();
            int currLevel = queue.size();
            for (int i = 0; i < currLevel; i++) {
                Node<T> node = queue.poll();
                if (isOrderLeft) {
                    level.offerLast(node.getData());
                } else {
                    level.offerFirst(node.getData());
                }
                if (node.getLeftChild() != null) {
                    queue.offer(node.getLeftChild());
                }
                if (node.getRightChild() != null) {
                    queue.offer(node.getRightChild());
                }

            }
            ret.add(new LinkedList<>(level));
            isOrderLeft = !isOrderLeft;
        }
        return ret;
    }


}
