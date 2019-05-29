package com.jex.market.dao.util.redis;

/**
 * Created by wanghui on 2017/6/16.
 */
public class RedisKeyUtil {
    /**
     * admin-前端 通知 更新缓存 资产缓存变更
     */
    public static final String MEMORY_ASSET_UPDATE = "1";

    /**
     * 用户资产缓存
     */
    public final static String USER_BALANCE = "u_balance";
    /**
     * 深度
     */
    public static final String MARKET_DEPTH_TRADES_KEY = "m_depth_key";
    /**
     * symbol表名
     */
    public static final String SYMBOL_TABLE_NAME = "symbol_table_name";
    /**
     * 现货(币币) 交易对 redis缓存
     */
    public final static String SYSTEM_SYMBOL_MAP = "system_symbol_map";
    /**
     * 期货交易对 redis 缓存
     */
    public final static String SYSTEM_CONTRACT_SYMBOL = "system_contract_symbol";
    /**
     * 期货资产 redis缓存
     */
    public final static String SYSTEM_CONTRACT_ASSERT = "system_contract_asset";
    /**
     * 多国语言交易对
     */
    public final static String LANG_SYMBOL = "lang_symbol";


    /**
     * 多国语言资产
     */
    public final static String LANG_ASSET = "lang_asset";


    /**
     * 现货和期权资产
     */
    public final static String SYSTEM_ASSET = "system_asset";

    //期货行情key
    public final static String CONTRACT_MARKET_KLINE = "c_kline_";
    public final static String CONTRACT_MARKET_INDEX_KLINE = "c_index_kline_";
    public final static String CONTRACT_MARKET_TICKER = "c_ticker";
    public final static String CONTRACT_MARKET_DEPTH = "mc_depth_key_200_s";

    /**
     * 市场行情
     **/
    public final static String SYSTEM_MARKET_TICKER = "system_market_ticker";

    /**
     * 行情公用数据缓存
     * 各个交易对最新成交价格，返回list
     */
    public final static String MARKET_TICKER = "m_ticker_1";

    /**
     * 现货 k线
     **/
    public static final String SPOT_MARKET_KLINE = "m_klines_s_";

    /**
     * 控制线上测试环境交易对显示与隐藏
     */
    public final static String PRODUCTION_TEST = "symbol_noshow_t";
    /**
     * 控制线上正式环境交易对显示与隐藏
     */
    public final static String PRODUCTION = "symbol_noshow";

    /**
     * 期权缓存的 redis key
     */
    public final static String SYSTEM_OPTION = "option";
    public final static String USER_OPTION = "user_option_";

    /**
     * 现货参考价格
     */
    public final static String ESTIMATE_MARKET_PRICE = "estimate_market_price";

    /**
     * 推送订阅channel
     */
    public final static String CHANNEL_BALANCE = "push_c_balance";

    /**
     * 用户数据流(websocket) 推送频道
     */
    public final static String CHANEL_USER_DATA_STREAM = "push_user_stream";

    /**
     * 用户期货资产缓存 hashMap
     */
    public final static String USER_CONTRACT_BALANCE_KEY = "user_c_balance";

    /**
     * 用户期货持仓缓存
     */
    public final static String USER_CONTRACT_POSITION_KEY = "user_c_position";


    public final static String SYSTEM_LEVEL = "system_level";

    public final static String DOCK_USER_LOCAL = "dockU_localU";

    /**
     * 用户是否被爆仓冻结
     */
    public static final String USER_TRADE_FROZEN = "trade_frozen";


    /**
     * 往推送工程推订单变化
     * 主工程推订单状态4
     */
    public static final String CHANNEL_ORDER = "push_c_order";


    /**
     * 当前委托
     */
    public final static String PRI_TRADEORDERS_CURRENT = "PRI_TradeOrders_C";

    /**
     * userApiSecretDto
     */
    public final static String USER_API_SECRET_DTO = "user_api_secret_dto";

    /**
     * 单个用户拥有listenKey  的前缀
     */
    public final static String USER_LISTEN_KEY = "user_listen_key";

    public final static String USER_LISTEN_KEY_LOCK = "user_listen_key_lock";

    /**
     * webSocket的listenKey
     */
    public static String LISTEN_KEY = "listen_key";

}
