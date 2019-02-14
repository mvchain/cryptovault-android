package com.mvc.cryptovault_android;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.Utils;
import com.mvc.cryptovault_android.common.Constant;

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

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        Utils.init(this);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
}
