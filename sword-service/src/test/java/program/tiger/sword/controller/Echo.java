package program.tiger.sword.controller;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebFlux;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
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
@WebFluxTest(controllers = Echo.class)
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Echo  {

    @InjectMocks
    private Mock



    @Autowired
    private WebTestClient testClient;

    @Test
    public void testEcho() {
        testClient.get().uri("/service/echo").exchange().expectStatus().isOk();
    }
}
