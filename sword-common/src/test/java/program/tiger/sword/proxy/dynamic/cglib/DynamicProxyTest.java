package program.tiger.sword.proxy.dynamic.cglib;

import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;
import program.tiger.sword.proxy.dynamic.Person;
import program.tiger.sword.proxy.dynamic.impl.Teacher;

/**
 * @author junhu.li
 * @ClassName DynamicProxyTest
 * @Description TODO
 * @date 2020/5/2915:16
 * @Version 1.0.0
 */
public class DynamicProxyTest {

    public static void main(String[] args) {
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "/Users/lijh/IdeaProjects/program-all/sword-common/target/classes/program/");

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Teacher.class);
        enhancer.setCallback(new DynamicProxyHandler());
        Person teacher = (Teacher) enhancer.create();
        teacher.action();
    }
}
