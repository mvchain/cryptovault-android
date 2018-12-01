package com.mvc.cryptovault_android.utils;

import com.blankj.utilcode.util.LogUtils;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public class ResponseBodyConverter<T> implements Converter<T, ResponseBody> {
    @Override
    public ResponseBody convert(T value) throws IOException {
        LogUtils.e("OkHttpFactory", "value:" + value);
        return null;
    }
}
