package com.mvc.ttpay_android.utils;

import android.annotation.SuppressLint;
import android.content.Intent;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.Utils;
import com.mvc.ttpay_android.MyApplication;
import com.mvc.ttpay_android.activity.SelectLoginActivity;
import com.mvc.ttpay_android.api.ApiStore;
import com.mvc.ttpay_android.bean.HttpTokenBean;
import com.mvc.ttpay_android.common.Constant;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import cn.jpush.android.api.JPushInterface;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.mvc.ttpay_android.common.Constant.SP.*;

public class RetrofitUtils {
    private static Retrofit mRetrofit;
    private static Map<String, Retrofit> utilsMap = new HashMap<>();

    private static Retrofit getInstance(String httpUrl) {
        if (utilsMap.get(httpUrl) == null) {
            synchronized (Retrofit.class) {
                mRetrofit = new Retrofit.Builder()
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(httpUrl)
                        .client(getOkhttpUtils()).build();
            }
            utilsMap.put(httpUrl, mRetrofit);
        }
        return utilsMap.get(httpUrl);
    }

    private static OkHttpClient getOkhttpUtils() {
        @SuppressLint("CheckResult") OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request.Builder builder = chain.request().newBuilder();
                    builder.header("Accept-Language", SPUtils.getInstance().getString(Constant.LANGUAGE.DEFAULT_ACCEPT_LANGUAGE));
                    Request request = builder.build();
                    Response response = chain.proceed(request);
                    return response;
                })
               // .addInterceptor(new HttpLoggingInterceptor(message -> LogUtils.e("RetrofitUtils", message))
                 //       .setLevel(HttpLoggingInterceptor.Level.BODY))
                .authenticator((route, response) -> {
                    HttpTokenBean body = RetrofitUtils.client(MyApplication.getBaseUrl(), ApiStore.class).refreshToken(SPUtils.getInstance().getString(REFRESH_TOKEN)).execute().body();
                    if (body.getCode() == 200) {
                        SPUtils.getInstance().put(TOKEN, body.getData());
                        MyApplication.setTOKEN(body.getData());
                        RetrofitUtils.client(MyApplication.getBaseUrl(), ApiStore.class).getPushTag(MyApplication.getTOKEN()).compose(RxHelper.rxSchedulerHelper())
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
                        Request.Builder builder = response.request().newBuilder();
                        builder.header("Authorization", SPUtils.getInstance().getString(TOKEN));
                        builder.header("Accept-Language", SPUtils.getInstance().getString(Constant.LANGUAGE.DEFAULT_ACCEPT_LANGUAGE));
                        return builder.build();
                    } else {
                        SPUtils.getInstance().remove(REFRESH_TOKEN);
                        SPUtils.getInstance().remove(UPDATE_PASSWORD_TYPE);
                        SPUtils.getInstance().remove(TOKEN);
                        JPushInterface.deleteAlias(MyApplication.getAppContext().getApplicationContext(), SPUtils.getInstance().getInt(USER_ID));
                        SPUtils.getInstance().remove(USER_ID);
                        SPUtils.getInstance().remove(USER_PUBLIC_KEY);
                        SPUtils.getInstance().remove(USER_SALT);
                        SPUtils.getInstance().remove(USER_GOOGLE);
                        if (!(ActivityUtils.getTopActivity() instanceof SelectLoginActivity)) {
                            Intent intent = new Intent();
                            intent.setAction(MyApplication.getAppContext().getPackageName() + ".android.login");
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            Utils.getApp().startActivity(intent);
                        }
                        return null;
                    }
                })
                .sslSocketFactory(createSSLSocketFactory())
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)
                .build();
        return client;
    }

    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());
            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }

        return ssfFactory;
    }

    private static class TrustAllCerts implements X509TrustManager {
        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    //信任所有的服务器,返回true
    private static class TrustAllHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    public static <T> T client(String httpUrl, Class<T> clazz) {
        return getInstance(httpUrl).create(clazz);
    }
}
