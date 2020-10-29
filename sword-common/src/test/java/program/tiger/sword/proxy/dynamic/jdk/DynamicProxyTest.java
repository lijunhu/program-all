package program.tiger.sword.proxy.dynamic.jdk;

import program.tiger.sword.proxy.dynamic.Person;
import program.tiger.sword.proxy.dynamic.impl.Student;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
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

        System.getProperties().put("jdk.proxy.ProxyGenerator.saveGeneratedFiles", "true");

        Person target = new Student();
        DynamicProxyHandler handler = new DynamicProxyHandler();
        handler.setTarget(target);
        Person proxy = (Person) Proxy.newProxyInstance(Person.class.getClassLoader(), target.getClass().getInterfaces(), handler);
        proxy.action();
    }
}
