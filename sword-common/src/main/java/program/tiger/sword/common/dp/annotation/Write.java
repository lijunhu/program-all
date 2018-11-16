package program.tiger.sword.common.dp.annotation;

import java.lang.annotation.*;

/**
 * 读数据源注解
 * @author TigerLee
 * @date 2018年11月16日17:51:50
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Write {
}
