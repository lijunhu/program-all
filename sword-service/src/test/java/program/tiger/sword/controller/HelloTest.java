package program.tiger.sword.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import program.tiger.sword.BaseTest;

/**
 * @author junhu.li
 * @ClassName HelloTest
 * @Description TODO
 * @date 2020/5/1114:44
 * @Version 1.0.0
 */
@RunWith(SpringRunner.class)
@WebFluxTest(controllers = HelloController.class)
public class HelloTest {

    @Autowired
    private WebTestClient testClient;

    @Test
    public void testHello() {
        testClient.get().uri("/hello").exchange().expectStatus().isOk();
    }
}
