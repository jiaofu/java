package com.jex.market.dao.util.db;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    /**
     * 返回需要使用的DataSource的key值，然后根据这个key从resolvedDataSources这
     * 个map里取出对应的DataSource，如果找不到，则用默认的resolvedDefaultDataSource。
     *
     * @return
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.getDataSource();
    }
}
