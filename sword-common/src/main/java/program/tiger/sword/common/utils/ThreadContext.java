package program.tiger.sword.common.utils;

public class ThreadLocalContext {

    private static final ThreadLocal<Context> contextHolder= new ThreadLocal<Context>();


    class Context{

    }
}
