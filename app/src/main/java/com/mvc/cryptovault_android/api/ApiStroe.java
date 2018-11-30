package com.mvc.cryptovault_android.api;

import com.mvc.cryptovault_android.bean.AllAssetBean;
import com.mvc.cryptovault_android.bean.AssetListBean;
import com.mvc.cryptovault_android.bean.LoginBean;
import com.mvc.cryptovault_android.bean.UserInfoBean;
import com.mvc.cryptovault_android.common.HttpUrl;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiStroe {
    @POST(HttpUrl.LOGIN)
    Observable<LoginBean> login(@Body RequestBody loginInfo);

    @GET(HttpUrl.GET_USER_INFO)
    Observable<UserInfoBean> getUserInfo(@Header("Authorization") String token);


    @GET(HttpUrl.GET_ASSET_LIST)
    Observable<AssetListBean> getAssetList(@Header("Authorization") String token);

    @GET(HttpUrl.GET_ASSET_ALL)
    Observable<AllAssetBean> getAssetAll(@Header("Authorization") String token);
}
