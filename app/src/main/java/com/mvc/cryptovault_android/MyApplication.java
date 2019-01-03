package com.mvc.cryptovault_android;

import android.app.Application;
import android.content.Context;

import com.blankj.utilcode.util.Utils;

import cn.jpush.android.api.JPushInterface;

public class MyApplication extends Application {
    private static Context application;

    public static Context getAppContext() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        Utils.init(this);
        JPushInterface.init(this);
    }

}
