package program.tiger.sword.common.db.datasource;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.datasource.AbstractDataSource;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Random;

/**
 * @author TigerLee
 * @date 2018年11月20日16:30:51
 */
@Slf4j
public class RoutingDataSource extends AbstractDataSource implements InitializingBean {

    private DataSource writeDataSource;

    private Map<String, DataSource> readDataSourceMap;


    @Override
    public Connection getConnection() throws SQLException {

        ConnectionType connectionType = ConnectionTypeHolder.get();
        try {
            if (connectionType == null || connectionType.getType().equals(ConnectionType.READ_WRITE)) {
                return writeDataSource.getConnection();
            }
            if (connectionType.getType().equals(ConnectionType.READ)) {
                String key = connectionType.getKey();
                if (key == null || key.length() == 0) {
                    key = randomReadKey();
                }
                if (readDataSourceMap.get(key) == null) {
                    logger.warn("can't find read connection using" + key + ", random one");
                    key = randomReadKey();
                }
                return readDataSourceMap.get(key).getConnection();
            }
        } catch (Exception e) {
            logger.error("getConnectionError", e);
            if (e instanceof SQLException) {
                throw (SQLException) e;
            } else {
                throw new SQLException(e);
            }
        }
        throw new IllegalArgumentException("invalid connection type: " + connectionType.getType() + ", key: "
                + connectionType.getKey());
    }


    private String randomReadKey() {
        String[] keys = readDataSourceMap.keySet().toArray(new String[0]);
        int size = readDataSourceMap.size();
        int rand = new Random().nextInt(size);
        return keys[rand];
    }


    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void afterPropertiesSet(){
        Assert.notNull(writeDataSource, ()->"写数据源为空");
        Assert.notNull(readDataSourceMap,()->"读数据源为空");
    }

    public void setWriteDataSource(DataSource writeDataSource) {
        this.writeDataSource = writeDataSource;
    }

    public void setReadDataSourceMap(Map<String, DataSource> readDataSourceMap) {
        this.readDataSourceMap = readDataSourceMap;
    }
}
