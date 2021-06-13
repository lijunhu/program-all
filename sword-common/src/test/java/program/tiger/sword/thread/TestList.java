package program.tiger.sword.thread;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicLong;

public class TestList {


    private final ExecutorService executorService = Executors.newFixedThreadPool(100);


    private static final Vector<AtomicLong> list = new Vector<>(11);

    private CountDownLatch countDownLatch;


    @Test
    public void testList() throws Exception {
        countDownLatch = new CountDownLatch(1000);
        for (int i = 0; i < 1000; i++) {
            final int n = i;
            executorService.execute(()->add(n));
        }
        Thread.sleep(1000*600);
        countDownLatch.await();
    }


    public void add(int n) {

        countDownLatch.countDown();

        try {
            Thread.sleep(1000 );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        AtomicLong l = new AtomicLong(System.currentTimeMillis());
        if (list.size() == 0) {
            list.addElement(new AtomicLong(System.currentTimeMillis()));
        } else {
            l = list.get(list.size() - 1);
        }
        if ((System.currentTimeMillis() - l.get()) > 1000) {
            System.out.println(list.size());
            if (list.size() > 10) {
                list.removeElementAt(0);
            }
            list.addElement(new AtomicLong(System.currentTimeMillis()));
        }
    }

}
