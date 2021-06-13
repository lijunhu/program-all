package program.tiger.sword.common;

import com.google.common.collect.Maps;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.util.encoders.Hex;
import org.jetbrains.annotations.NotNull;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import program.tiger.sword.common.utils.InheritableThreadContext;
import program.tiger.sword.common.utils.JsonUtil;
import program.tiger.sword.common.utils.ThreadContext;
import program.tiger.sword.http.JanusConfig;

import java.lang.reflect.Constructor;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author junhu.li
 * @ClassName Test
 * @Description TODO
 * @date 2020/2/2919:40
 * @Version 1.0.0
 */
public class Test {
    private int a = 0;


    public static class Root {
        private School school;
        private List<Person> persons;

    }

    public static class School {

    }

    public static class Person {

    }


    public static class A<T> {
        private String name;
        private Boolean using;
        protected T data;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Boolean getUsing() {
            return using;
        }

        public void setUsing(Boolean using) {
            this.using = using;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }
    }

    public static class B extends A<Map<String, Object>> {
        private Map<String, Object> data;

        @Override
        public Map<String, Object> getData() {
            return data;
        }

        @Override
        public void setData(Map<String, Object> data) {
            this.data = data;
        }
    }


    public static class Task implements Runnable {
        private static int count;

        private final CountDownLatch countDownLatch;

        public Task(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            Thread currThread = Thread.currentThread();
            countDownLatch.countDown();
            count++;
            System.out.println(currThread.getName() + "\t" + count);
            System.out.println("继承1--------" + InheritableThreadContext.currentThreadContext().getTraceId());
            System.out.println("继承2--------" + ThreadContext.currentThreadContext().getTraceId());

        }
    }


    public static class TestNumber {
        private Long number;

        public TestNumber() {
        }

        public Long getNumber() {
            return number;
        }

        public void setNumber(Long number) {
            this.number = number;
        }
    }

    private static final int MAXIMUM_CAPACITY = 1 << 30;

    private static final int tableSizeFor(int c) {
        int n = c - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    public static void main(String[] args) throws Exception {


        long now = System.currentTimeMillis();
        for (long i = now / 1000; i>(now / 1000) - 10;i--){
            System.out.println(i);
        }

        System.out.println(1 & 2);

        ConcurrentMap<String, Integer> c = new ConcurrentHashMap<>();
        c.put("a", 1);
        c.put("a", 2);

        TestNumber number = new TestNumber();
        number.setNumber(1231231231313211L);

        System.out.println(JsonUtil.toJson(number));

        ExecutorService service = Executors.newFixedThreadPool(5,
                new CustomizableThreadFactory("custom-"));

        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            ThreadContext.currentThreadContext().setTraceId(String.valueOf(i));
            InheritableThreadContext.currentThreadContext().setTraceId(String.valueOf(i));
            service.execute(new Task(countDownLatch));
        }
        countDownLatch.await();

    }


    public Integer returnFinally() {
        try {
            a = 33;
            return a;
        } finally {
            a = 44;
        }
    }

    @org.junit.Test
    public void calcVal() {
        System.out.println((1 << 3) + (1 << 5));
    }


    public static long ip2Long(String ip) {
        ip = ip.trim();
        final String[] ipNums = ip.split("\\.");
        return (Long.parseLong(ipNums[0]) << 24)
                + (Long.parseLong(ipNums[1]) << 16)
                + (Long.parseLong(ipNums[2]) << 8)
                + (Long.parseLong(ipNums[3]));
    }


}

