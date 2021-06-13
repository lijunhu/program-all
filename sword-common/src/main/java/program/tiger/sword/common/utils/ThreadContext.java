package program.tiger.sword.common.utils;

public class ThreadContext {

    private static final ThreadLocal<ThreadContext> contextHolder = ThreadLocal.withInitial(ThreadContext::new);

    public static ThreadContext currentThreadContext() {
        return contextHolder.get();
    }

    public static void clear() {
        contextHolder.remove();
    }

    private String traceId;

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }
}
