package com.mvc.cryptovault_android.common;

public class HttpUrl {
    //    public static final String BASE_URL = "http://192.168.15.31:10086/";
//    public static final String BASE_URL = "http://47.110.234.233:10086/";
//    public static final String URL_PATH = "api/app/";
    public static final String URL_PATH = "";
    public static final String URL_BROWSER = "api/explorer/";
//        public static final String BASE_URL = "http://192.168.15.21:10086/";

    /**
     * user login
     */
    public static final String LOGIN = URL_PATH + "user/login";

    /**
     * refresh token
     */
    public static final String TOKEN_REFRESH = URL_PATH + "user/refresh";


    /**
     * remove userinfo
     */
    public static final String GET_USER_INFO = URL_PATH + "user/info";


    /**
     * remove asset list
     */
    public static final String GET_ASSET_LIST = URL_PATH + "asset";

    /**
     * remove all asset
     */
    public static final String GET_ASSET_ALL = URL_PATH + "asset/balance";

    /**
     * Get currency list
     */
    public static final String GET_CURRENCY_ALL = URL_PATH + "token";
    /**
     * Get message
     */
    public static final String GET_MESSAGE = URL_PATH + "message";


    /**
     * remove pair
     */
    public static final String GET_PAIR = URL_PATH + "transaction/pair";

    /**
     * remove exchange rate
     */
    public static final String GET_RATE = URL_PATH + "token/exchange/rate";


    /**
     * remove exchange rate
     */
    public static final String GET_TRANSACTIONS = URL_PATH + "asset/transactions";

    /**
     * get project
     */
    public static final String GET_CROWDFUNDING = URL_PATH + "project";

    /**
     * get recript qcode
     */
    public static final String GET_QCODE = URL_PATH + "asset/address";

    /**
     * Get transfer information based on id
     */
    public static final String GET_TRANASTION = URL_PATH + "asset/transaction";

    /**
     * 划账余额获取 / 划账
     */
    public static final String GET_DEBIT = URL_PATH + "asset/debit";


    /**
     * 获取参与的众筹项目列表
     */
    public static final String GET_RESERVATION = URL_PATH + "project/reservation";

    /**
     * 获取交易购买/出售列表
     */
    public static final String GET_RECORDING = URL_PATH + "transaction";

    /**
     * 挂单信息获取transactionType:1购买 2出售
     */
    public static final String GET_TRANSACTIONINFO = URL_PATH + "transaction/info";
    /**
     * 筛选已参与订单
     */
    public static final String GET_TPARTAKE = URL_PATH + "transaction/partake";
    /**
     * 获取7日K线图
     */
    public static final String GET_KLINE = URL_PATH + "transaction/pair/kline";


    /**
     * 获取24小时最新，最高，最低价格, 每10秒钟更新
     */
    public static final String GET_PAIR_TICKERS = URL_PATH + "transaction/pair/tickers";

    /**
     * 获取验证码
     */
    public static final String SEND_CODE = URL_PATH + "user/sms";
    /**
     * 获取推送tag
     */
    public static final String GET_PUSH_TAG = URL_PATH + "user/tag";
    /**
     * 注册用户信息
     */
    public static final String REGSITER_EMAIL = URL_PATH + "user";

    /**
     * 发送邮箱验证码
     */
    public static final String SEND_EMAIL_CODE = URL_PATH + "user/email/logout";
    /**
     * 用户注册
     */
    public static final String USER_REGISTER = URL_PATH + "user/register";
    /**
     * 校验/获取助记词
     */
    public static final String USER_MNEMONICS = URL_PATH + "user/mnemonics";


    /**
     * 重置密码验证信息的步骤，成功后返回一次性token，供修改密码使用
     */
    public static final String USER_RESET_VERRFICATION = URL_PATH + "user/reset";

    /**
     * 忘记密码_修改
     */
    public static final String USER_FORGET = URL_PATH + "user/forget";
    /**
     * 修改登录密码
     */
    public static final String USER_PASSWORD = URL_PATH + "user/password";
    /**
     * 修改支付密码
     */
    public static final String USER_TRANSACTIONPASSWORD = URL_PATH + "user/transactionPassword";

    /**
     * 直接发送验证码
     */
    public static final String USER_EMAIL = URL_PATH + "user/email";
    /**
     * 获取邀请码
     */
    public static final String USER_INVITATION = URL_PATH + "user/invitation";
    /**
     * 获取推荐人列表
     */
    public static final String USER_RECOMMEND = URL_PATH + "user/recommend";

    /**
     * 用户是否签到/签到
     */
    public static final String USER_SIGN = URL_PATH + "user/sign";

    /**
     * 获取我的理财资产
     */
    public static final String FINANCIAL_BALANCE = URL_PATH + "financial/balance";

    /**
     * 获取理财列表/详情
     */
    public static final String FINANCIAL = URL_PATH + "financial";

    /**
     * 获取持仓列表
     */
    public static final String FINANCIAL_PARTAKE = URL_PATH + "financial/partake";


    /**
     * 获取App版本信息
     */
    public static final String UPDATE_APP = URL_PATH + "app";


    /**
     * 已发币项目列表
     */
    public static final String PUBLISH = URL_PATH + "project/publish";
    /**
     * 发币记录列表
     */
    public static final String PUBLISH_LIST = URL_PATH + "project/";

    /**
     * 发币记录列表
     */
    public static final String PUBLISH_DETAIL = URL_PATH + "project/";

    /**
     * 发币记录列表
     */
    public static final String VALID = URL_PATH + "user/valid";
    /**
     * 获取某个币种余额,
     */
    public static final String ASSET = URL_PATH + "asset/";


//    区块链浏览器

    /**
     * 获取最新的区块信息
     */
    public static final String BLOCK_LAST = URL_BROWSER + "block/last";

    /**
     * 获取区块列表
     */
    public static final String BLOCK_LIST = URL_BROWSER + "block";

    /**
     * 获取最新交易
     */
    public static final String BLOCK_TRANSACTION_LAST = URL_BROWSER + "block/transaction/last";

    /**
     * 查询公钥是否存在,存在则返回公钥,不存在返回空
     */
    public static final String BLOCK_ADDRESS_EXIST = URL_BROWSER + "block/address/exist";

    /**
     * 根据公钥查询资产
     */
    public static final String BLOCK_ADDRESS_BALANCE = URL_BROWSER + "block/address/balance";

    /**
     * 根据公钥查询订单列表
     */
    public static final String BLOCK_ADDRESS_ORDER = URL_BROWSER + "block/address/order";

    /**
     * 根据公钥查询订单详情
     */
    public static final String BLOCK_ADDRESS_ORDER_ID = URL_BROWSER + "block/address/order/";

    /**
     * 获取区块详情交易列表
     */
    public static final String BLOCK_ID_TRANSACTIONS = URL_BROWSER + "block/{blockId}/transactions";

    /**
     * 根据hash查询交易详情
     */
    public static final String BLOCK_TRANSACTION_TX = URL_BROWSER + "block/transaction/tx";
}
