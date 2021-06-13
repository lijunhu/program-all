package program.tiger.sword.common.aop;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 参数检查
 * @author TigerLee
 * @date 2018年11月26日21:23:18
 */
@Aspect
public class ParamCheckAop {



    @Pointcut(value = "@annotation(program.tiger.sword.common.annotations.ParamCheck)")
    public void cutPoint(){

    }

    @Around(value = "cutPoint()")
    public Object doCheckParam(){ return "";};
}
