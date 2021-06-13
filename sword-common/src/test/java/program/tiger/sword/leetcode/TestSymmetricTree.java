package program.tiger.sword.leetcode;

import org.junit.Test;

public class TestSymmetricTree {



    @Test
    public void testSolution(){

        SymmetricTree.Node<Integer> n1 = new SymmetricTree.Node<>(1);
        SymmetricTree.Node<Integer> n2 = new SymmetricTree.Node<>(2);
        SymmetricTree.Node<Integer> n3 = new SymmetricTree.Node<>(1);

        n2.setLeftChild(n1);
        n2.setRightChild(n1);
        n1.setRightChild(n3);
        n1.setLeftChild(n3);
        System.out.println(new SymmetricTree().solution(n2));

    }
}
