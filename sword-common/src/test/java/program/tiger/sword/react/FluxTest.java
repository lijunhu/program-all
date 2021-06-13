package program.tiger.sword.react;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.concurrent.CountDownLatch;

/**
 * @author junhu.li
 * @ClassName FluxTest
 * @Description TODO
 * @date 2020/5/2810:18
 * @Version 1.0.0
 */
public class FluxTest {
    private static final Logger logger = LoggerFactory.getLogger(FluxTest.class);


    @Test
    public void testFlux() throws Exception {


        final CountDownLatch countDownLatch = new CountDownLatch(5);
        Flux<Object> flux = Flux.create(sink -> {
            for (int i = 0; i < 5; i++) {
                new Thread(() -> {
                    sink.next("1");
                    countDownLatch.countDown();
                }).start();
            }
        });

        countDownLatch.await();
        flux.subscribe(o -> {
            if (o instanceof Throwable) {

            } else {
                logger.info((String) o);
            }
        });
    }

}
