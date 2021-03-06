package com.mvc.cryptovault_android.utils;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.Utils;
import com.mvc.cryptovault_android.MyApplication;
import com.mvc.cryptovault_android.api.ApiStore;
import com.mvc.cryptovault_android.bean.HttpTokenBean;
import com.mvc.cryptovault_android.bean.TagBean;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

import cn.jpush.android.api.JPushInterface;
import io.reactivex.functions.Consumer;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static com.mvc.cryptovault_android.common.Constant.SP.REFRESH_TOKEN;
import static com.mvc.cryptovault_android.common.Constant.SP.TAG_NAME;
import static com.mvc.cryptovault_android.common.Constant.SP.TOKEN;

/**
 * 判断token是否过期
 */
public class ParameterInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        if (response.code() == 403) {//根据和服务端的约定判断token过期
            LogUtils.e("静默自动刷新Token,然后重新请求数据");
            //同步请求方式,获取最新的Token
//            HttpTokenBean httpTokenBean = getNewToken();
//            if (httpTokenBean != null) {
//                if (httpTokenBean.getCode() == 401) {
//                    Intent intent = new Intent();
//                    intent.setAction("android.login");
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                    Utils.getApp().startActivity(intent);
//                } else {
//                    //使用新的Token,创建新的请求
//                    //重新请求
//                    SPUtils.getInstance().put(TOKEN, httpTokenBean.getData());
//                    String token = SPUtils.getInstance().getString(TOKEN);
//                    refreshPushTag(token);
//                    Request newRequest = chain.request()
//                            .newBuilder()
//                            .header("Authorization", token)
//                            .header("Accept-Language", "zh-cn")
//                            .build();
//                    return chain.proceed(newRequest);
//                }
//            }
        } else if (response.code() == 401) {
            SPUtils.getInstance().remove(REFRESH_TOKEN);
            SPUtils.getInstance().remove(TOKEN);
            Intent intent = new Intent();
            intent.setAction("android.login");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            Utils.getApp().startActivity(intent);
        }
        return response;
    }

    /**
     * 刷新推送tag  只有刷新令牌的时候会调用
     *
     * @param token
     */
    @SuppressLint("CheckResult")
    private void refreshPushTag(String token) {
        RetrofitUtils.client(MyApplication.getBaseUrl(),ApiStore.class).getPushTag(token).compose(RxHelper.rxSchedulerHelper())
                .subscribe(tagBean -> {
                    if (tagBean.getCode() == 200) {
                        SPUtils.getInstance().put(TAG_NAME, tagBean.getData());
                    }
                    String[] tags = tagBean.getData().split(",");
                    for (int i = 0; i < tags.length; i++) {
                        if (i == 0) {
                            JPushInterface.setTags(Utils.getApp().getApplicationContext(), Integer.parseInt(tags[i]), new HashSet<>(Arrays.asList(tags[i])));
                        } else {
                            JPushInterface.addTags(Utils.getApp().getApplicationContext(), Integer.parseInt(tags[i]), new HashSet<>(Arrays.asList(tags[i])));
                        }
                    }
                }, throwable -> {
                    LogUtils.e("ParameterInterceptor", throwable.getMessage());
                });

    }

//    /**
//     * 同步请求方式,获取最新的Token
//     *
//     * @return
//     */
//    private HttpTokenBean getNewToken() throws IOException {
//        // 通过一个特定的接口获取新的token,此处要用到同步的retrofit请求
////        Response_Login loginInfo = CacheManager.restoreLoginInfo(BaseApplication.getContext());
////        String username = loginInfo.getUserName();
////        String password = loginInfo.getPassword();
////        LogUtils.e("loginInfo=" + loginInfo.toString());
////        Call<Response_Login> call = WebHelper.getSyncInterface().synclogin(new Request_Login(username, password));
////        loginInfo = call.execute().body();
////        LogUtils.e("loginInfo=" + loginInfo.toString());
////        loginInfo.setPassword(password);
////        CacheManager.saveLoginInfo(loginInfo);
////        JSONObject object = new JSONObject();
//        String refreshToken = SPUtils.getInstance().getString(REFRESH_TOKEN);
////        LogUtils.e("ParameterInterceptor", refreshToken);
////        try {
////            object.put("Authorization", refreshToken);
////        } catch (JSONException e) {
////            LogUtils.d("ParameterInterceptor", e.getMessage());
////            e.printStackTrace();
////        }
////        RequestBody token = RequestBody.create(MediaType.parse("text/html"), object.toString());
//        return RetrofitUtils.client(ApiStore.class).refreshToken(refreshToken).execute().body();
//    }
}
