package com.mvc.cryptovault_android.utils;

import android.content.Intent;
import android.util.Log;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.Utils;
import com.mvc.cryptovault_android.MyApplication;
import com.mvc.cryptovault_android.api.ApiStore;
import com.mvc.cryptovault_android.bean.HttpTokenBean;
import com.mvc.cryptovault_android.common.Constant;
import com.mvc.cryptovault_android.common.HttpUrl;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

import cn.jpush.android.api.JPushInterface;
import okhttp3.Authenticator;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.mvc.cryptovault_android.common.Constant.SP.REFRESH_TOKEN;
import static com.mvc.cryptovault_android.common.Constant.SP.TAG_NAME;
import static com.mvc.cryptovault_android.common.Constant.SP.TOKEN;

public class RetrofitUtils {
    private static Retrofit mRetrofit;

    private static Retrofit getInstance() {
        if (mRetrofit == null) {
            synchronized (Retrofit.class) {
                if (mRetrofit == null) {
                    mRetrofit = new Retrofit.Builder()
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .baseUrl(HttpUrl.BASE_URL)
                            .client(getOkhttpUtils()).build();
                }
            }
        }
        return mRetrofit;
    }

    private static OkHttpClient getOkhttpUtils() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request.Builder builder = chain.request().newBuilder();
                    builder.header("Accept-Language", SPUtils.getInstance().getString(Constant.LANGUAGE.DEFAULT_ACCEPT_LANGUAGE));
                    Request request = builder.build();
                    Response response = chain.proceed(request);
                    return response;
                })
                .authenticator((route, response) -> {
                    HttpTokenBean body = RetrofitUtils.client(ApiStore.class).refreshToken(SPUtils.getInstance().getString(REFRESH_TOKEN)).execute().body();
                    if (body.getCode() == 200) {
                        SPUtils.getInstance().put("token", body.getData());
                        MyApplication.setTOKEN(body.getData());
                        RetrofitUtils.client(ApiStore.class).getPushTag(MyApplication.getTOKEN()).compose(RxHelper.rxSchedulerHelper())
                                .subscribe(tagBean -> {
                                    if (tagBean.getCode() == 200 && tagBean.getData() != null) {
                                        SPUtils.getInstance().put(TAG_NAME, tagBean.getData());
                                        String[] tags = tagBean.getData().split(",");
                                        for (int i = 0; i < tags.length; i++) {
                                            if (i == 0) {
                                                JPushInterface.setTags(MyApplication.getAppContext(), Integer.parseInt(tags[i]), new HashSet<>(Arrays.asList(tags[i])));
                                            } else {
                                                JPushInterface.addTags(MyApplication.getAppContext(), Integer.parseInt(tags[i]), new HashSet<>(Arrays.asList(tags[i])));
                                            }
                                        }
                                    }
                                }, throwable -> {
                                    LogUtils.e("ParameterInterceptor", throwable.getMessage());
                                });
                    } else {
                        Intent intent = new Intent();
                        intent.setAction("android.login");
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        Utils.getApp().startActivity(intent);
                    }
                    Request.Builder builder = response.request().newBuilder();
                    builder.header("Authorization", SPUtils.getInstance().getString(TOKEN));
                    builder.header("Accept-Language", SPUtils.getInstance().getString(Constant.LANGUAGE.DEFAULT_ACCEPT_LANGUAGE));
                    return builder.build();
                })
                .addInterceptor(new HttpLoggingInterceptor(message -> LogUtils.e("RetrofitUtils", message))
                        .setLevel(HttpLoggingInterceptor.Level.BODY))
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)
                .build();
        return client;
    }

    public static <T> T client(Class<T> clazz) {
        return getInstance().create(clazz);
    }
}
