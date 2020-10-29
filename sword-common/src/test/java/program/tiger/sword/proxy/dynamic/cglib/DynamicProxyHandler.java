package program.tiger.sword.proxy.dynamic.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author junhu.li
 * @ClassName DynamicProxyHandler
 * @Description TODO
 * @date 2020/5/2915:20
 * @Version 1.0.0
 */
public class DynamicProxyHandler implements MethodInterceptor {


    public DynamicProxyHandler() {
    }
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("before target invoke");

        Object result = methodProxy.invokeSuper(o, objects);
        System.out.println("after target invoke");
        return result;
    }
}
