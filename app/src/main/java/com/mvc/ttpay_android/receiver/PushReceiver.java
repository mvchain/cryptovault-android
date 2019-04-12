package com.mvc.ttpay_android.receiver;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.blankj.utilcode.util.LogUtils;
import com.mvc.ttpay_android.R;
import com.mvc.ttpay_android.activity.MsgActivity;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

public class PushReceiver extends BroadcastReceiver {
    private static final String TAG = "PushReceiver";
    private NotificationManager nm;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (null == nm) {
            nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            LogUtils.e(TAG, "JPush用户注册成功");
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            LogUtils.e(TAG, "接受到推送下来的自定义消息");
            try {
                createNotification(context, intent);
            } catch (JSONException e) {
                LogUtils.e(TAG, e.getMessage());
                e.printStackTrace();
            }
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            LogUtils.e(TAG, "接受到推送下来的通知");
//            receivingNotification(context,bundle);
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            LogUtils.e(TAG, "用户点击打开了通知");
            //      openNotification(context,bundle);
//            openLaikan(context);
        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            Log.e(TAG, "[MyReceiver]" + intent.getAction() + " connected:" + connected);
        } else {
            LogUtils.e(TAG, "Unhandled intent - " + intent.getAction());
        }
    }

    private void createNotification(Context context, Intent intent) throws JSONException {
        Bundle bundle = intent.getExtras();
        String title = bundle.getString(JPushInterface.EXTRA_TITLE);
        String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
        JSONObject extraJson = new JSONObject(extra);
        LogUtils.e(TAG, extraJson.toString() + "========" + message);
        int orderId = extraJson.getInt("orderId");
        NotificationCompat.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = new NotificationCompat.Builder(context, "price_update");
            nm.createNotificationChannel(new NotificationChannel("price_update", "新消息通知", NotificationManager.IMPORTANCE_HIGH));
        } else {
            builder = new NotificationCompat.Builder(context);
        }
        Intent msgIntent = new Intent(context, MsgActivity.class);
        PendingIntent mPendingIntent = PendingIntent.getActivity(context, 1, msgIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        //设置通知栏标题
        builder.setContentTitle("消息提醒")
                //设置通知栏显示内容
                .setContentText(message)
                ////设置通知栏点击意图
                .setContentIntent(mPendingIntent)
                //通知首次出现在通知栏，带上升动画效果的
                .setTicker("您有新的消息")
                //通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                .setWhen(System.currentTimeMillis())
                //设置该通知优先级
                .setPriority(Notification.PRIORITY_DEFAULT)
                //设置这个标志当用户单击面板就可以让通知将自动取消
                .setAutoCancel(true)
                //使用当前的用户默认设置
                .setDefaults(Notification.DEFAULT_VIBRATE)
                //设置通知小ICON(应用默认图标)
                .setSmallIcon(R.mipmap.vp_logo);
        nm.notify(orderId, builder.build());
    }
}
