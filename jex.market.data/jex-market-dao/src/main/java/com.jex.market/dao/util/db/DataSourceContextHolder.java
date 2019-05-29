package com.jex.market.dao.util.db;

/**
 * Describe: DataSource ContextHolder
 */
public class DataSourceContextHolder {

    /**
     * 默认数据源名称
     */
    public static final String DEFAULT_DS = "db1";

    // 数据源标识保存在线程变量中，避免多线程操作数据源时互相干扰
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    // 设置数据源标识
    public static void setDataSource(String dbType) {
        contextHolder.set(dbType);
    }

    // 获取数据源标识
    public static String getDataSource() {
        return (contextHolder.get());
    }

    // 清除数据源标识
    public static void clearDataSource() {
        contextHolder.remove();
    }
}
