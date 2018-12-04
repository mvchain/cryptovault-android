package com.mvc.cryptovault_android.api;

import com.mvc.cryptovault_android.bean.AllAssetBean;
import com.mvc.cryptovault_android.bean.AssetListBean;
import com.mvc.cryptovault_android.bean.CurrencyBean;
import com.mvc.cryptovault_android.bean.HttpTokenBean;
import com.mvc.cryptovault_android.bean.LoginBean;
import com.mvc.cryptovault_android.bean.MsgBean;
import com.mvc.cryptovault_android.bean.TrandChildBean;
import com.mvc.cryptovault_android.bean.UserInfoBean;
import com.mvc.cryptovault_android.common.HttpUrl;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiStore {
    @POST(HttpUrl.LOGIN)
    Observable<LoginBean> login(@Body RequestBody loginInfo);

    @POST(HttpUrl.TOKEN_REFRESH)
    Call<HttpTokenBean> refreshToken(@Header("Authorization") String token);

    @GET(HttpUrl.GET_USER_INFO)
    Observable<UserInfoBean> getUserInfo(@Header("Authorization") String token);

    @GET(HttpUrl.GET_ASSET_LIST)
    Observable<AssetListBean> getAssetList(@Header("Authorization") String token);

    @GET(HttpUrl.GET_ASSET_ALL)
    Observable<AllAssetBean> getAssetAll(@Header("Authorization") String token);

    @GET(HttpUrl.GET_CURRENCY_ALL)
    Observable<CurrencyBean> getCurrencyAll(@Header("Authorization") String token);

    @GET(HttpUrl.GET_MESSAGE)
    Observable<MsgBean> getMsg(@Header("Authorization") String token, @Query("timestamp") long timestamp, @Query("type") int type, @Query("pageSize") int pageSize);

    @GET(HttpUrl.GET_PAIR)
    Observable<TrandChildBean> getVrtAndBalance(@Header("Authorization") String token, @Query("pairType")int pairType);

}
