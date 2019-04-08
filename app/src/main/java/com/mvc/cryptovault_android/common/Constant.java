package com.mvc.cryptovault_android.common;

public class Constant {
    public static final String[] WEAK_PASSWORD = {"","","","","","","","","",""};

    public static class SP {
        public static final String DEFAULT_RATE = "default_rate";
        public static final String DEFAULT_SYMBOL = "defaule_symbol";
        public static final String SET_RATE = "set_rate";
        public static final String ASSETS_LIST = "assets_list";
        public static final String ALLASSETS = "all_assets";
        public static final String RATE_LIST = "rate_list";
        public static final String CURRENCY_LIST = "currency_list";
        public static final String REFRESH_TOKEN = "refreshToken";
        public static final String TOKEN = "token";
        public static final String USER_ID = "user_id";
        public static final String USER_EMAIL = "user_email";
        public static final String USER_PUBLIC_KEY = "user_public_key";
        public static final String USER_SALT = "user_salt";
        public static final String RECORDING_UNIT = "recording_unit";
        public static final String TRAND_LIST = "trand_list";
        public static final String TRAND_VRT_LIST = "vrt_list";
        public static final String TRAND_BALANCE_LIST = "balance_list";
        public static final String MSG_TIME = "msg_time";
        public static final String READ_MSG = "read_msg";
        public static final String USER_INFO = "user_info";
        public static final String RECORDING_TYPE = "recording_type";
        public static final String TAG_NAME = "tag_name";
        public static final String UPDATE_PASSWORD_TYPE = "update_password_type";
        //  保存注册的时候用户信息   临时， 当注册且激活成功之后就移除这个SP
        public static final String REG_INVITATION = "reg_invitation";
        public static final String REG_EMAIL = "reg_email";
        public static final String REG_TEMPTOKEN = "reg_temptoken";
        public static final String REG_MINEMNEMONICS = "reg_minemnemonics";
        public static final String REG_PRIVATEKEY = "reg_privatekey";
        public static final String REG_REGISTER= "reg_register";
        public static final String REG_LOGINPWD = "reg_loginpwd";
        public static final String REG_PAYLOGIN = "reg_paylogin";
        public static final String TRADING_PAIRID = "trading_pairid";


    }

    public static class LANGUAGE {
        //APP语言
        public static final String DEFAULT_LANGUAGE = "zh_CN";
        public static final String CHINESE = "zh_CN";
        public static final String ENGLISH = "en";
        public static final String DEFAULT_ENGLUSH = "default_englush";
        //设置接口国际化
        public static final String DEFAULT_ACCEPT_LANGUAGE = "default_accept_language";
        public static final String ACCEPT_CHINESE = "zh-cn";
        public static final String ACCEPT_ENGLISH = "en-US";
    }
}
