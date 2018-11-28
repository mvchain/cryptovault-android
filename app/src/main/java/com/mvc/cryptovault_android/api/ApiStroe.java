package com.mvc.cryptovault_android.api;

import com.mvc.cryptovault_android.bean.LoginBean;
import com.mvc.cryptovault_android.common.HttpUrl;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiStroe {
    @POST(HttpUrl.LOGIN)
    Observable<LoginBean> login(@Body RequestBody loginInfo);
}
