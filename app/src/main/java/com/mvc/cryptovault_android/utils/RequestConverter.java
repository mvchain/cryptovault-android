package com.mvc.cryptovault_android.utils;

import com.blankj.utilcode.util.LogUtils;

import java.io.IOException;

import okhttp3.RequestBody;
import retrofit2.Converter;

public class RequestConverter<T> implements Converter<T, RequestBody> {
    @Override
    public RequestBody convert(T value) throws IOException {
        LogUtils.e("RequestConverter", "value:" + value);
        return null;
    }
}
