package program.tiger.sword.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lijh
 */
public class Permute {


    public static void main(String[] args) {
        Permute permute = new Permute();
        List<List<Integer>> res = permute.solution(new int[]{1, 2, 3,4,5});
        System.out.println(res);
    }


    public List<List<Integer>> solution(int[] nums) {
        int len = nums.length;
        List<List<Integer>> res = new ArrayList<>(16);

        boolean[] used = new boolean[len];

        backTrace(nums, len, 0, new ArrayList<>(16), used, res);
        return res;
    }


    private void backTrace(int[] nums, int len, int depth,
                           List<Integer> path, boolean[] used,
                           List<List<Integer>> res) {
        if (depth == len) {
            res.add(new ArrayList<>(path));
            return;
        }

        for (int i = 0; i < len; i++) {
            if (!used[i]) {
                path.add(nums[i]);
                used[i] = true;
                backTrace(nums, len, depth + 1, path, used, res);
                // 注意：这里是状态重置，是从深层结点回到浅层结点的过程，代码在形式上和递归之前是对称的
                used[i] = false;
                path.remove(path.size() - 1);
            }
        }
    }
}

