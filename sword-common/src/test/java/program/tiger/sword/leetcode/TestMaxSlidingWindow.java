package program.tiger.sword.leetcode;

import org.junit.Test;

public class TestMaxSlidingWindow {


    @Test
    public void testSolution() {
        MaxSlidingWindow maxSlidingWindow = new MaxSlidingWindow();
        int[] result = maxSlidingWindow.solution(new int[]{1, 3, -1, -3, 5, 3, 6, 7}, 3);

        System.out.print('[');
        for (int i = 0; i < result.length; i++) {
            System.out.print(result[i]+ ",");
        }
        System.out.print("]\n");

    }
}
