package com.mvc.cryptovault_android;

import android.app.Application;
import android.content.Context;

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

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        Utils.init(this);
        JPushInterface.init(this);
    }

}
