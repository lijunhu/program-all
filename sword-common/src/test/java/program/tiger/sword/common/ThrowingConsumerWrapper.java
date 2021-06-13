package program.tiger.sword.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

/**
 * @author junhu.li
 * @ClassName ThrowingConsumerWrapper
 * @Description TODO
 * @date 2020/5/1420:38
 * @Version 1.0.0
 */
public class ThrowingConsumerWrapper {
    private static final Logger logger = LoggerFactory.getLogger(ThrowingConsumerWrapper.class);

    public static <T, E extends Exception> Consumer<T> handlingConsumerWrapper(
            ThrowingConsumer<T, E> throwingConsumer, Class<E> exceptionClass) {

        return i -> {
            try {
                throwingConsumer.accept(i);
            } catch (Exception ex) {
                try {
                    E exCast = exceptionClass.cast(ex);
                    logger.error("Exception occured : " + exCast);
                } catch (ClassCastException ccEx) {
                    throw new RuntimeException(ex);
                }
            }
        };
    }
}
