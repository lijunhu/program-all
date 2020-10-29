package program.tiger.sword.proxy.dynamic.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author junhu.li
 * @ClassName DynamicProxyHandler
 * @Description TODO
 * @date 2019-03-2720:16
 * @Version 1.0.0
 */
public class DynamicProxyHandler implements InvocationHandler {

    private Object target;


    public DynamicProxyHandler() {
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before target invoke");

        Object result = method.invoke(proxy, args);
        System.out.println("after target invoke");
        return null;
    }
}
