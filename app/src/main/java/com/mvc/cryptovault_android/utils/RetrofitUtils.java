package com.mvc.cryptovault_android.utils;

import android.util.Log;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.mvc.cryptovault_android.common.Constant;
import com.mvc.cryptovault_android.common.HttpUrl;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

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
                .addInterceptor(new HttpLoggingInterceptor(message -> LogUtils.e("RetrofitUtils", message))
                        .setLevel(HttpLoggingInterceptor.Level.BODY))
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build();
        return client;
    }

    public static <T> T client(Class<T> clazz) {
        return getInstance().create(clazz);
    }
}
