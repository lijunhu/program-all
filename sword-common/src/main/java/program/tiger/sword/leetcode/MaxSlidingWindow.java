package program.tiger.sword.leetcode;

public class MaxSlidingWindow {

    public int[] solution(int[] nums, int k) {
        if (nums.length <= 0) {
            return nums;
        }
        int len = nums.length - k + 1;
        int[] result = new int[len];
        int a;
        for (int i = 0; i < len; i++) {
            a = nums[i];
            for (int j = i; j < k; j++) {
                if (a < nums[j]) {
                    a = nums[j];
                }
            }
            k++;
            result[i] = a;
        }
        return result;
    }
}
