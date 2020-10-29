package program.tiger.sword.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author junhu.li
 * @ClassName dbConfig
 * @Description TODO
 * @date 2020/4/1510:35
 * @Version 1.0.0
 */
@Component
public class DataSourceConfig {

    @Value("${spring.datasource.username}")
    private String userName;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    @Value("${spring.datasource.min-idle}")
    private Integer minIdle;
    @Value("${spring.datasource.max-active}")
    private Integer maxActive;
    @Value("${spring.datasource.validation-query}")
    private String validationQuery;
    @Value("${spring.datasource.connection-timeout}")
    private Integer connectionTimeout;
    @Value("${spring.datasource.max-left-time}")
    private Integer maxLeftTime;
    @Value("${spring.datasource.validation-time-out}")
    private Integer validationTimeout;
    @Value("${spring.datasource.idle-time-out}")
    private Integer idleTimeout;
    @Value("${spring.datasource.connection-init-sql}")
    private String connectionInitSql;

    /**
     * 配置Hikari数据连接池
     *
     * @param url 数据源
     * @return 连接池对象
     */
    public HikariDataSource getHikariDataSource(String url) {
        HikariConfig config = new HikariConfig();
        config.setMinimumIdle(minIdle);
        config.setMaximumPoolSize(maxActive);
        config.setConnectionTestQuery(validationQuery);
        config.setJdbcUrl(url);
        config.setUsername(userName);
        config.setPassword(password);
        config.setDriverClassName(driverClassName);
        config.setConnectionTimeout(connectionTimeout);
        config.setMaxLifetime(maxLeftTime);
        config.setIdleTimeout(idleTimeout);
        config.setValidationTimeout(validationTimeout);
        config.setConnectionInitSql(connectionInitSql);
        return new HikariDataSource(config);
    }

    /**
     * 用于两个库账号密码不一样的时候
     *
     * @param url      数据库url
     * @param userName 数据库用户名
     * @param password 数据库用户密码
     * @return Hikari数据库连接
     */
    public HikariDataSource getHikariDataSource(String url, String userName, String password) {
        com.zaxxer.hikari.HikariConfig config = new com.zaxxer.hikari.HikariConfig();
        config.setMinimumIdle(minIdle);
        config.setMaximumPoolSize(maxActive);
        config.setConnectionTestQuery(validationQuery);
        config.setJdbcUrl(url);
        config.setUsername(userName);
        config.setPassword(password);
        config.setConnectionTimeout(connectionTimeout);
        config.setDriverClassName(driverClassName);
        config.setMaxLifetime(maxLeftTime);
        config.setIdleTimeout(idleTimeout);
        config.setValidationTimeout(validationTimeout);
        config.setConnectionInitSql(connectionInitSql);
        return new HikariDataSource(config);
    }
}
