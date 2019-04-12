package com.mvc.ttpay_android;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.Utils;
import com.mvc.ttpay_android.common.Constant;

import cn.jpush.android.api.JPushInterface;

public class MyApplication extends Application {
    private static Context application;
    private static String TOKEN;

    public static Context getAppContext() {
        return application;
    }

    public static String getTOKEN() {
        return TOKEN == null ? SPUtils.getInstance().getString(Constant.SP.TOKEN) : TOKEN;
    }

    public static void setTOKEN(String TOKEN) {
        MyApplication.TOKEN = TOKEN;
    }

    public static String getBaseUrl() {
        return getAppContext().getString(R.string.base_url);
    }
    public static String getBaseBrowserUrl() {
        return getAppContext().getString(R.string.base_browser_url);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        Utils.init(this);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        MultiDex.install(this);
//        if (SPUtils.getInstance().getString(DEFAULT_ENGLUSH).equals("")) {
//            Locale locale = new Locale(ENGLISH);
//            Locale.setDefault(locale);
//            Configuration config = getResources().getConfiguration();
//            DisplayMetrics metrics = getResources().getDisplayMetrics();
//            config.locale = Locale.SIMPLIFIED_CHINESE;
//            getResources().updateConfiguration(config, metrics);
//            SPUtils.getInstance().put(DEFAULT_LANGUAGE, ENGLISH);
//            SPUtils.getInstance().put(DEFAULT_ACCEPT_LANGUAGE, ACCEPT_ENGLISH);
//        }
    }
}
