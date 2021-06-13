package program.tiger.sword.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;


@Controller
public class Hello {

    @RequestMapping(path = "/hello", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Flux<String> hello() {
        return Flux.just("name");
    }
}
