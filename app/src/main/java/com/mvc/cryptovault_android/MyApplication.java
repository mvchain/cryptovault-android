package com.mvc.cryptovault_android;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.support.multidex.MultiDex;
import android.util.DisplayMetrics;
import android.util.Log;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.Utils;
import com.mvc.cryptovault_android.common.Constant;
import com.mvc.cryptovault_android.utils.LanguageUtils;

import java.util.Locale;

import cn.jpush.android.api.JPushInterface;

import static com.mvc.cryptovault_android.common.Constant.LANGUAGE.ACCEPT_ENGLISH;
import static com.mvc.cryptovault_android.common.Constant.LANGUAGE.CHINESE;
import static com.mvc.cryptovault_android.common.Constant.LANGUAGE.DEFAULT_ACCEPT_LANGUAGE;
import static com.mvc.cryptovault_android.common.Constant.LANGUAGE.DEFAULT_ENGLUSH;
import static com.mvc.cryptovault_android.common.Constant.LANGUAGE.DEFAULT_LANGUAGE;
import static com.mvc.cryptovault_android.common.Constant.LANGUAGE.ENGLISH;

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
        if (SPUtils.getInstance().getString(DEFAULT_ENGLUSH).equals("")) {
            Locale locale = new Locale(ENGLISH);
            Locale.setDefault(locale);
            Configuration config = getResources().getConfiguration();
            DisplayMetrics metrics = getResources().getDisplayMetrics();
            config.locale = Locale.SIMPLIFIED_CHINESE;
            getResources().updateConfiguration(config, metrics);
            SPUtils.getInstance().put(DEFAULT_LANGUAGE, ENGLISH);
            SPUtils.getInstance().put(DEFAULT_ACCEPT_LANGUAGE, ACCEPT_ENGLISH);
        }
    }
}
