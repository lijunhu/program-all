package program.tiger.sword.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import org.apache.commons.compress.utils.Lists;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author junhu.li
 * @ClassName CacheTest
 * @Description TODO
 * @date 2020/5/2911:30
 * @Version 1.0.0
 */
public class CacheTest {
    private static final Logger logger = LoggerFactory.getLogger(CacheTest.class);
    private static Cache<String, Object> cache = CacheBuilder.newBuilder()
            .expireAfterWrite(5, TimeUnit.SECONDS)
            .refreshAfterWrite(5, TimeUnit.SECONDS)
            .build(new CacheLoader<String, Object>() {
                @Override
                public Object load(String key) throws Exception {
                    return "121231";
                }
            });

    private static com.github.benmanes.caffeine.cache.Cache<Long, AtomicInteger> timeCache = Caffeine
            .newBuilder()
            .maximumSize(10L)
            .build();

    public static class Person {
        private String name;

        public Person setName(String name) {
            this.name = name;
            return this;
        }

        public String getName() {
            return name;
        }
    }

    @Test
    public void testCache() throws Exception {

        long now = System.currentTimeMillis()/1000;
        for (int i=0;i<20;i++){
            timeCache.put(now+i, new AtomicInteger(i));
        }



        AtomicInteger n = timeCache.getIfPresent(now );
        if (n != null) {
            System.out.println("---------" + n.get());
        }
        n = timeCache.getIfPresent(now+1);
        if (n != null) {
            System.out.println("---------" + n.get());
        }
        Person[] persons = new Person[3];

        persons[0] = new Person().setName("a");
        persons[1] = new Person().setName("b");
        persons[2] = new Person().setName("c");

        List<Person> pl = Lists.newArrayList();

        Person p;
        for (Person person : persons) {
            p = person;
            pl.add(p);
        }

        for (Person p0 : pl) {
            System.out.println(p0.getName());
        }


        cache.put("key", "testGuavaCache");

        Object val = cache.getIfPresent("key");
        logger.info((String) val);

    }

}
