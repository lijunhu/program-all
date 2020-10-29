package program.tiger.sword.common.db.datasource;

import lombok.Getter;
import lombok.Setter;

/**
 * 数据源连接类型
 * @author TigerLee
 * @date 2018年11月16日17:45:49
 */
@Getter
@Setter
public class ConnectionType {

    public static final String READ = "R";
    public static final String READ_WRITE = "RW";

    /**
     * 数据源类型
     */
    private  String type;
    /**
     * key值
     */
    private  String key;

    public ConnectionType() {
    }

    public ConnectionType(String type, String key) {
        this.type = type;
        this.key = key;
    }

}
