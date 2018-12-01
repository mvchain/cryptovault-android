package com.mvc.cryptovault_android.utils;

import android.support.annotation.Nullable;

import com.blankj.utilcode.util.LogUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class OkHttpFactory extends Converter.Factory {

    public static OkHttpFactory create() {
        return new OkHttpFactory();
    }

    @Nullable
    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        LogUtils.e("OkHttpFactory", "requestBodyConverter" + type.toString());
        return new RequestConverter<>();
    }

    @Nullable
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        LogUtils.e("OkHttpFactory", "responseBodyConverter" + type.toString());
        return new ResponseBodyConverter<>();
    }

}
