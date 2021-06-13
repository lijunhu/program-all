package program.tiger.sword.common.utils;

public class InheritableThreadContext {


    private static final InheritableThreadLocal<ThreadContext> inheritableContextHolder =
            new InheritableThreadLocal<ThreadContext>() {
                @Override
                protected ThreadContext initialValue() {
                    return new ThreadContext();
                }
            };

    public static ThreadContext currentThreadContext() {
        return inheritableContextHolder.get();
    }

    public static void clear() {
        inheritableContextHolder.remove();
    }

    private String traceId;


    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }
}
