package program.tiger.sword.proxy.dynamic.jdk;

import java.lang.reflect.Proxy;

/**
 * @author junhu.li
 * @ClassName DynamicProxyTest
 * @Description TODO
 * @date 2019-03-2720:19
 * @Version 1.0.0
 */
public class DynamicProxyTest {


    public static void main(String[] args) {

        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

        Person target = new Student();
        DynamicProxyHandler handler = new DynamicProxyHandler();
        handler.setTarget(target);
        Person proxy = (Person) Proxy.newProxyInstance(Person.class.getClassLoader(),target.getClass().getInterfaces(),handler);
        proxy.action();
    }
}
