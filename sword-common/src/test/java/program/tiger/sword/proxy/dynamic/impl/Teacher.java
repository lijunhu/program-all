package program.tiger.sword.proxy.dynamic.impl;

import program.tiger.sword.proxy.dynamic.Person;

/**
 * @author junhu.li
 * @ClassName Teacher
 * @Description TODO
 * @date 2019-03-2720:14
 * @Version 1.0.0
 */
public class Teacher implements Person {
    @Override
    public void action() {
        System.out.println("teacher read paper");
    }
}
