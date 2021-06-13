package program.tiger.sword.leetcode;

import java.util.Objects;

public class SymmetricTree {


    public static class Node<T> {
        private T data;
        private SymmetricTree.Node<T> leftChild;
        private SymmetricTree.Node<T> rightChild;

        public Node() {
        }

        public Node(T data) {
            this.data = data;
        }

        public Node(T data, SymmetricTree.Node<T> leftChild, SymmetricTree.Node<T> rightChild) {
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

        public SymmetricTree.Node<T> getLeftChild() {
            return leftChild;
        }

        public void setLeftChild(SymmetricTree.Node<T> leftChild) {
            this.leftChild = leftChild;
        }

        public SymmetricTree.Node<T> getRightChild() {
            return rightChild;
        }

        public void setRightChild(SymmetricTree.Node<T> rightChild) {
            this.rightChild = rightChild;
        }
    }


    public <T> boolean solution(Node<T> n) {

        return check(n, n);
    }


    public <T> boolean check(Node<T> p, Node<T> q) {

        if (p == null && q == null) {
            return true;
        } else if (p == null || q == null) {
            return false;
        }

        return Objects.equals(p.getData(), p.getData())
                && check(p.getLeftChild(), q.getRightChild())
                && check(p.getRightChild(), q.getLeftChild());
    }
}
