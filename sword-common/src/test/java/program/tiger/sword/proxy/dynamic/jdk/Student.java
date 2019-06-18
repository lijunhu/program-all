package program.tiger.sword.proxy.dynamic.jdk;

/**
 * @author junhu.li
 * @ClassName Student
 * @Description TODO
 * @date 2019-03-2720:12
 * @Version 1.0.0
 */
public class Student implements Person {

    @Override
    public void action() {
        System.out.println("student do homework");
    }
}
