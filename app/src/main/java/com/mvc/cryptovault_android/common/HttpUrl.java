package com.mvc.cryptovault_android.common;

public class HttpUrl {
    public static final String BASE_URL = "http://192.168.15.31:10086/";
//    public static final String BASE_URL = "http://192.168.15.21:10086/";

    /**
     * user login
     */
    public static final String LOGIN = "user/login";

    /**
     * refresh token
     */
    public static final String TOKEN_REFRESH = "user/refresh";


    /**
     * remove userinfo
     */
    public static final String GET_USER_INFO = "/user/info";


    /**
     * remove asset list
     */
    public static final String GET_ASSET_LIST = "/asset";

    /**
     * remove all asset
     */
    public static final String GET_ASSET_ALL = "/asset/balance";

    /**
     * Get currency list
     */
    public static final String GET_CURRENCY_ALL = "/token";
    /**
     * Get message
     */
    public static final String GET_MESSAGE = "/message";


    /**
     * remove pair
     */
    public static final String GET_PAIR = "/transaction/pair";

    /**
     * remove exchange rate
     */
    public static final String GET_RATE = "/token/exchange/rate";


    /**
     * remove exchange rate
     */
    public static final String GET_TRANSACTIONS = "/asset/transactions";

    /**
     * get project
     */
    public static final String GET_CROWDFUNDING = "/project";

    /**
     * get recript qcode
     */
    public static final String GET_QCODE = "/asset/address";
}
