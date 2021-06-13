package program.tiger.sword.leetcode;

import java.util.concurrent.locks.Lock;

public class Foo {
    private Lock lock;

    public Foo() {
    }


    public void Print(int n) throws InterruptedException {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });
        t.join(1000L);
        for (int i = 0; i <= n; i = 2 * i + 1) {

        }
    }


    class Odd implements Runnable {

        private int n;

        public Odd(int n) {
            this.n = n;
        }

        @Override
        public void run() {
            System.out.println(n);
        }
    }


    class Even implements Runnable {
        private int n;

        public Even(int n) {
            this.n = n;
        }

        @Override
        public void run() {
            System.out.println(n);
        }
    }

}
