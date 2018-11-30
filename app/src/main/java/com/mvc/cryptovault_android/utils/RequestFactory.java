package com.mvc.cryptovault_android.utils;

import android.support.annotation.Nullable;

import com.blankj.utilcode.util.LogUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class RequestFactory extends Converter.Factory {

    public static RequestFactory create(){
        return new RequestFactory();
    }

    @Nullable
    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        LogUtils.e("RequestFactory", type.toString());
        return super.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit);
    }

    @Nullable
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        LogUtils.e("RequestFactory", type.toString());
        return super.responseBodyConverter(type, annotations, retrofit);
    }

}
