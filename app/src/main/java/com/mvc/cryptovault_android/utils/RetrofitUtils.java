package com.mvc.cryptovault_android.utils;

import com.blankj.utilcode.util.LogUtils;
import com.example.mvc.cvaji.common.HttpUrl;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
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
                .addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        LogUtils.e("RetrofitUtils", message);
                    }
                })).writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .build();
        return client;
    }
    public static <T> T client(Class<T> clazz){
        return getInstance().create(clazz);
    }
}
