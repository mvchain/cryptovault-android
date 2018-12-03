package com.mvc.cryptovault_android.common;

public class HttpUrl {
    public static final String BASE_URL = "http://192.168.15.31:10086/";
    /**
     * user login
     */
    public static final String LOGIN = "user/login";

    /**
     * refresh token
     */
    public static final String TOKEN_REFRESH = "user/refresh";


    /**
     * get userinfo
     */
    public static final String GET_USER_INFO = "/user/info";


    /**
     * get asset list
     */
    public static final String GET_ASSET_LIST = "/asset";

    /**
     * get all asset
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

}
