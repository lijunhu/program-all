package program.tiger.sword.leetcode;

import org.junit.Test;

public class ReverseList {

    static class ListNode<T> {
        private T val;
        private ListNode<T> next;

        public ListNode() {
        }

        public ListNode(T val) {
            this.val = val;
        }

        public ListNode(T val, ListNode<T> next) {
            this.val = val;
            this.next = next;
        }

        public T getVal() {
            return val;
        }

        public ListNode<T> getNext() {
            return next;
        }
    }

    public <T> ListNode<T> iterate(ListNode<T> head) {
        ListNode<T> prev = null, curr = head;

        while (curr != null) {
            ListNode<T> next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }

    public <T> ListNode<T> recur(ListNode<T> head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode<T> newHead = recur(head.next);
        head.next.next = head;
        head.next = null;
        return newHead;


    }

    @Test
    public void Test() {
        for (int i=0;i<100;i++){

        }
        ListNode<Integer> l5 = new ListNode<>(5);
        ListNode<Integer> l4 = new ListNode<>(4, l5);
        ListNode<Integer> l3 = new ListNode<>(3, l4);
        ListNode<Integer> l2 = new ListNode<>(2, l3);
        ListNode<Integer> l1 = new ListNode<>(1, l2);

        ListNode<Integer> a = new ReverseList().recur(l1);

        while (a != null) {
            System.out.print(a.val);
            if (a.next != null) {
                System.out.print("->");
            } else {
                System.out.println();
            }
            a = a.next;
        }
    }
}
