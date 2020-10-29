package program.tiger.sword.common.db.annotation;

import java.lang.annotation.*;


/**
 * 读数据源注解
 * @author TigerLee
 * @date 2018年11月16日17:42:50
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Read {

    String value() default "";
}
