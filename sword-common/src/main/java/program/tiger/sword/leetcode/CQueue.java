package program.tiger.sword.leetcode;

import java.util.Stack;

/**
 * @author lijh
 */
public class CQueue {

    private Stack<Integer>  s1,s2;

    public CQueue() {
        s1 = new Stack<>();
        s2 = new Stack<>();
    }

    public void appendTail(int value) {
        s1.push(value);
        s2.push(value);
    }

}
