package program.tiger.sword;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * 项目启动
 * @author TigerLee
 * @date 2018年11月16日10:45:26
 */
@SpringBootApplication
public class BootStrap extends SpringBootServletInitializer {
    /**
     * 实现SpringBootServletInitializer可以让spring-boot项目在web容器中运行
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        builder.sources(this.getClass());
        return super.configure(builder);
    }


    public static void main(String[] args) {
        SpringApplication.run(BootStrap.class, args);

    }
}
