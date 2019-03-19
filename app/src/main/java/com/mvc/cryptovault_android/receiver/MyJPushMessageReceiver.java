package com.mvc.cryptovault_android.receiver;

import android.content.Context;
import android.util.Log;

import com.blankj.utilcode.util.LogUtils;

import cn.jpush.android.api.JPushMessage;
import cn.jpush.android.service.JPushMessageReceiver;

public class MyJPushMessageReceiver extends JPushMessageReceiver {
    @Override
    public void onTagOperatorResult(Context context, JPushMessage jPushMessage) {
        //tag增删查改的操作会在此方法中回调结果。
        LogUtils.e("MyJPushMessageReceiver", jPushMessage.getTags());
        super.onTagOperatorResult(context, jPushMessage);
    }
    @Override
    public void onCheckTagOperatorResult(Context context,JPushMessage jPushMessage){
        //查询某个tag与当前用户的绑定状态的操作会在此方法中回调结果。
        LogUtils.e("MyJPushMessageReceiver", jPushMessage.getTags());
        super.onCheckTagOperatorResult(context, jPushMessage);
    }
    @Override
    public void onAliasOperatorResult(Context context, JPushMessage jPushMessage) {
        //alias相关的操作会在此方法中回调结果。
        LogUtils.e("MyJPushMessageReceiver", "jPushMessage.getErrorCode():" + jPushMessage.getErrorCode());
        LogUtils.e("MyJPushMessageReceiver", jPushMessage.getAlias());
        super.onAliasOperatorResult(context, jPushMessage);
    }

    @Override
    public void onMobileNumberOperatorResult(Context context, JPushMessage jPushMessage) {
        //设置手机号码会在此方法中回调结果。
        LogUtils.e("MyJPushMessageReceiver", jPushMessage.getMobileNumber());
        super.onMobileNumberOperatorResult(context, jPushMessage);
    }
}
