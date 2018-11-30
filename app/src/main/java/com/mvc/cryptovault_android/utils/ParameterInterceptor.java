package com.mvc.cryptovault_android.utils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 可能需要自定义head 暂时没用到
 */
public class ParameterInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
//        Request.Builder builder = request.newBuilder();
//        String token = request.header("token");
        return chain.proceed(request);
    }
}
