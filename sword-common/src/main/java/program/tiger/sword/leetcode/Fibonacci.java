package program.tiger.sword.leetcode;

public class Fibonacci {

    /**
     * 0 1 1 2 3 5 8 13 21 34 55
     *
     * @param n
     * @return
     */

    public long solution(int n) {
        long[] results = new long[n + 1];
        for (int i = 0; i < n + 1; i++) {
            if (i <= 1) {
                results[i] = i;
            } else {
                results[i] = results[i - 1] + results[i - 2];
            }
        }
        return results[n];
    }

    public long recSolution(int n) {
        if (n == 0) {
            return 0;
        } else if (n == 1) {
            return 1;
        } else {
            return recSolution(n - 1) + recSolution(n - 2);
        }

    }
}
