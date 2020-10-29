package program.tiger.sword.leetcode;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * @author junhu.li
 * @ClassName MergeLists
 * @Description TODO
 * @date 2020/6/119:38
 * @Version 1.0.0
 */
public class MergeLists {


    @Getter
    @Setter
    public static class ListNode {
        private int val;
        private ListNode next;

        ListNode(int val) {
            this.val = val;
        }
    }


    public ListNode mergeLists(ListNode[] listNodes) {

        for (int i = 0; i < listNodes.length; i++) {
            for (int j = 0; j < listNodes.length; j++) {

            }
        }
        return new ListNode(1);
    }

    public ListNode mergeTwoNodeLists(ListNode a, ListNode b) {
        if (Objects.isNull(a) || Objects.isNull(b)) {
            return Objects.isNull(a) ? b : a;
        }
        ListNode head, tail;
        return new ListNode(2);
    }
}
