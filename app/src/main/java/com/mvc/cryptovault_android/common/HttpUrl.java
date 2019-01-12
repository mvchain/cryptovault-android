package com.mvc.cryptovault_android.common;

public class HttpUrl {
    public static final String BASE_URL = "http://192.168.15.31:10086/";
//    public static final String BASE_URL = "http://192.168.15.21:10086/";
//    public static final String BASE_URL = "http://47.110.234.233:10086/";

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

    /**
     * Get transfer information based on id
     */
    public static final String GET_TRANASTION = "/asset/transaction";

    /**
     * 划账余额获取 / 划账
     */
    public static final String GET_DEBIT = "/asset/debit";


    /**
     * 获取参与的众筹项目列表
     */
    public static final String GET_RESERVATION = "/project/reservation";

    /**
     * 获取交易购买/出售列表
     */
    public static final String GET_RECORDING = "/transaction";

    /**
     * 挂单信息获取transactionType:1购买 2出售
     */
    public static final String GET_TRANSACTIONINFO = "/transaction/info";
    /**
     * 筛选已参与订单
     */
    public static final String GET_TPARTAKE = "/transaction/partake";
    /**
     * 获取7日K线图
     */
    public static final String GET_KLINE = "/transaction/pair/kline";
    /**
     * 获取验证码
     */
    public static final String SEND_CODE = "/user/sms";
    /**
     * 获取推送tag
     */
    public static final String GET_PUSH_TAG = "/user/tag";
    /**
     * 注册用户信息
     */
    public static final String REGSITER_EMAIL = "/user";

    /**
     * 发送邮箱验证码
     */
    public static final String SEND_EMAIL_CODE = "/user/email/logout";

}
