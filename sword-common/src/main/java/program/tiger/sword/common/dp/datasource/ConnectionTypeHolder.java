package program.tiger.sword.common.dp.datasource;

/**
 * @author TigerLee
 * @date 2018年11月16日17:46:32
 * 数据源信息
 */
public class ConnectionTypeHolder {

    private static ThreadLocal<ConnectionType> connType = ThreadLocal.withInitial(() -> ConnectionTypeHolder.get());

    public static void set(ConnectionType type) {
        connType.set(type);
    }

    public static ConnectionType get() {
        return connType.get();
    }

    public static void release() {
        connType.remove();
    }

}
