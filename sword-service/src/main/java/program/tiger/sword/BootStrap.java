package program.tiger.sword;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.reactive.config.EnableWebFlux;

/**
 * @author junhu.li
 * @ClassName BootStrap
 * @Description 项目启动
 * @date 2019-02-2218:41
 * @Version 1.0.0
 */
@SpringBootApplication
public class BootStrap {

    public static void main(String[] args) {
        SpringApplication.run(BootStrap.class, args);
    }
}
