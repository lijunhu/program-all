package program.tiger.sword.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;

/**
 * @author junhu.li
 * @ClassName HelloController
 * @Description TODO
 * @date 2019-02-2618:50
 * @Version 1.0.0
 */
@Controller
public class HelloController {
    @RequestMapping(path = "/hello", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Flux<String> hello() {
        return Flux.just("hello");
    }
}
