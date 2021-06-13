package program.tiger.sword.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class Echo {

    @GetMapping(value = "/api/echo")
    @ResponseBody
    public Mono<String> echo() {
        return Mono.just("echo");
    }
}
