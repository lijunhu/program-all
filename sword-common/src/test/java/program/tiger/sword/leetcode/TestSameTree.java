package program.tiger.sword.leetcode;

import org.junit.Test;

public class TestSameTree {



    @Test
    public void testSolution(){
        SameTree.Node<Integer> n1 = new SameTree.Node<>(1);
        SameTree.Node<Integer> n2 = new SameTree.Node<>(2);
        SameTree.Node<Integer> n3 = new SameTree.Node<>(3);
        n2.setLeftChild(n1);
        n2.setRightChild(n3);

        SameTree.Node<Integer> na = new SameTree.Node<>(1);
        SameTree.Node<Integer> nb = new SameTree.Node<>(2);
        SameTree.Node<Integer> nc = new SameTree.Node<>(3);

        nb.setLeftChild(na);
        nb.setRightChild(nc);

        System.out.println(new SameTree().solution(n2,nb));
    }
}
