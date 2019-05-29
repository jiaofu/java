package com.jex.market.dao.util;


import java.math.BigDecimal;

/**
 * 注：该类会被外部项目通过jar包形式调用。某些显示未使用的常量，会被外部项目使用。勿删！
 */
public class Constants {

    /**
     * 用来取登录用户信息
     */
    public final static String SESSION_KEY_PRE = "usersession";

    public final static String API_SECRET_BEAN_PRE = "apiSecretBean";

    public final static String USER_TOKEN_KEY = "userToken";

    /**
     * 买进交易
     */
    public static final int TRADE_BUY = 1;
    /**
     * 卖出交易
     */
    public static final int TRADE_SELL = 2;

    /**
     * 期货资产缓存时间
     */
    public static final int CONTRACT_USERT_BALANCE_EXPIRE = 5 * 60 * 60;

    /**
     * 多仓
     */
    public static String LONGS = "longs";
    /**
     * 空仓
     */
    public static String SHORTS = "shorts";

    /**
     * 修改敏感时间
     */
    public static final int USER_MODIFY_PRI_TIME = 60 * 24;

    public static final int USER_INFO_EXPIRE = 5 * 3600 + 7;//五个小时  5 * 3600+7 second
    public static final int USER_API_SECRET_EXPIRE = 5 * 3600;//五个小时  5 * 3600 second

    /**
     * 交易对关联关系key
     */
    public static String symbolRoute = "symbolRoute";
    public static String symbolTest = "symbolTest";//路径测试key

    /**
     * 提现浮动数
     */
    public static final BigDecimal MARKET_FLOAT_NUMBER = new BigDecimal("1.05");

    /**
     * 调用限速类型
     */
    public static final String USER_SPEED = "userSpeed";
    public static final String ORDER_SPEED = "orderRatio";
    public static final String FREEZE_LIMIT = "freezeLimit";
    public static final String WEIGHT_REQUEST = "requestsWeight";

    /**
     * preLimitHandle
     */
    public static final String IP_WHITELIST_PRE_HANDLE = "ipWhitePre";
    public static final String USER_WHITELIST_PRE_HANDLE = "userWhitePre";

    /**
     * paramStr
     */
    public static final String SYMBOL_PARAM = "symbol";
    public static final String LIMIT_PARAM = "limit";
    public static final String ASSET_PARAM = "asset";
    /**
     * contract
     */
    public static int QUERY_DEFUALT_SIZE = 200;
    public static int USERT_BALANCE_EXPIRE = 5 * 60 * 60;

    /**
     * localCache expire time
     */
    public static int LOCAL_USER_API_SECRET_EXPIRE = 5 * 60; //单位minute  5小时
    public static int LOCAL_USER_BEAN_EXPIRE = 5 * 60; //单位minute  5小时

    /**
     * localCache maxSize
     */
    public static int LOCAL_USER_API_SECRET_MAX_SIZE = 10000;
    public static int LOCAL_USER_BEAN_MAX_SIZE = 10000;

    /**
     * userApiSecret prefix
     */
    public static String USER_API_SECRET_PREFIX = "user_api_secret_prefix";
    public static String USER_BEAN_PREFIX = "user_spot_bean";
    public static String USER_CONTRACT_SESSION_ID_KEY = "user_contract_sessionid";

    /**
     * jwt modul
     */
    public static String JWT_AUDIENCE = "web";
    public static String JWT_ISSUER = "userid";
    public static int JWT_EXPIRE = 5 * 60 * 60 * 1000 + 7 * 1000;//millisecond  5小时


    /**
     * securityType
     */
    public static int SECURITY_ACCESS_FREELY = 0;
    public static int SECURITY_VALID_KEY = 1;
    public static int SECURITY_SIGNATURE = 2;

    /**
     * InvokAuthority
     */
    public static final int NO_AUTHORITY = 0;
    /**
     * 可读权限
     */
    public static final int READ_AUTHORITY = 1;
    /**
     * 可交易权限
     */
    public static final int TRADE_AUTHORITY = 1 << 1;
    /**
     * 可提现权限
     */
    public static final int WITHDARW_AUTHORITY = 1 << 2;
    /**
     * 开启期权发行赎回权限
     */
    public static final int OPTION_RELEASE_BACK = 1 << 4;
    /**
     * 开启期货转入转出保证金权限
     */
    public static final int TRANSFER_MARGIN = 1 << 5;


    /**
     * routeEnumType
     */
    public static final int ROUTE_PLACE_TYPE = 1;
    public static final int ROUTE_CANCEL_TYPE = 2;

}
