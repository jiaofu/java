package com.jex.market.dao.util.db;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Describe: DataSource Config
 */
@Configuration
public class DataSourceConfig {


    /**
     * 配置数据源1
     *
     * @return
     */
    @Bean(name = "db1")
    @ConfigurationProperties(prefix = "spring.datasource.db1")
    @Primary
    public DataSource dataSource1() {
        return DataSourceBuilder.create().build();
    }


    /**
     * 配置数据源2
     *
     * @return
     */
    @Bean(name = "db2")
    @ConfigurationProperties(prefix = "spring.datasource.db2")
    public DataSource dataSource2() {
        return DataSourceBuilder.create().build();
    }


    @Bean(name = "dynamicDS")
    public DataSource dataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        // 默认数据源,当没有指定的时候使用，可以当做主数据源
        dynamicDataSource.setDefaultTargetDataSource(dataSource1());
        Map<Object, Object> dsMap = new HashMap();
        dsMap.put("db1", dataSource1());
        dsMap.put("db2", dataSource2());

        // 注册多数据源
        dynamicDataSource.setTargetDataSources(dsMap);

        return dynamicDataSource;
    }

}
