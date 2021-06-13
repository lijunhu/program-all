package program.tiger.sword.leetcode;

import org.junit.Test;

public class TestFibonacci {

    @Test
    public void testSolution(){
        System.out.println(new Fibonacci().solution(10));
    }

    @Test
    public void testRecSolution(){
        System.out.println(new Fibonacci().recSolution(100));
    }
}
