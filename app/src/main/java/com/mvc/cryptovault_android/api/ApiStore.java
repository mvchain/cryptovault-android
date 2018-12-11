package com.mvc.cryptovault_android.api;

import com.mvc.cryptovault_android.base.ExchangeRateBean;
import com.mvc.cryptovault_android.base.VPBalanceBean;
import com.mvc.cryptovault_android.bean.AllAssetBean;
import com.mvc.cryptovault_android.bean.AssetListBean;
import com.mvc.cryptovault_android.bean.CurrencyBean;
import com.mvc.cryptovault_android.bean.DetailBean;
import com.mvc.cryptovault_android.bean.HistroyBean;
import com.mvc.cryptovault_android.bean.HttpTokenBean;
import com.mvc.cryptovault_android.bean.IDToTransferBean;
import com.mvc.cryptovault_android.bean.LoginBean;
import com.mvc.cryptovault_android.bean.MsgBean;
import com.mvc.cryptovault_android.bean.ReceiptBean;
import com.mvc.cryptovault_android.bean.TogeBean;
import com.mvc.cryptovault_android.bean.TrandChildBean;
import com.mvc.cryptovault_android.bean.UpdateBean;
import com.mvc.cryptovault_android.bean.UserInfoBean;
import com.mvc.cryptovault_android.common.HttpUrl;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
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

    @PUT(HttpUrl.GET_ASSET_LIST)
    Observable<UpdateBean> updateAssetList(@Header("Authorization") String token, @Body RequestBody putBody);

    @GET(HttpUrl.GET_ASSET_ALL)
    Observable<AllAssetBean> getAssetAll(@Header("Authorization") String token);

    @GET(HttpUrl.GET_CURRENCY_ALL)
    Observable<CurrencyBean> getCurrencyAll(@Header("Authorization") String token);

    @GET(HttpUrl.GET_MESSAGE)
    Observable<MsgBean> getMsg(@Header("Authorization") String token, @Query("timestamp") long timestamp, @Query("type") int type, @Query("pageSize") int pageSize);

    @GET(HttpUrl.GET_PAIR)
    Observable<TrandChildBean> getVrtAndBalance(@Header("Authorization") String token, @Query("pairType") int pairType);

    @GET(HttpUrl.GET_RATE)
    Observable<ExchangeRateBean> getExchangeRate(@Header("Authorization") String token);

    @GET(HttpUrl.GET_TRANSACTIONS)
    Observable<HistroyBean> getHistroyRecording(@Header("Authorization") String token,
                                                @Query("id") int id,
                                                @Query("pageSize") int pageSize,
                                                @Query("tokenId") int tokenId,
                                                @Query("transactionType") int transactionType,
                                                @Query("type") int type);

    @GET(HttpUrl.GET_CROWDFUNDING)
    Observable<TogeBean> getCrowdfunding(@Header("Authorization") String token,
                                         @Query("pageSize") int pageSize,
                                         @Query("projectId") int projectId,
                                         @Query("projectType") int projectType,
                                         @Query("type") int type);

    @GET(HttpUrl.GET_QCODE)
    Observable<ReceiptBean> getRecriptQCode(@Header("Authorization") String token, @Query("tokenId") int tokenId);

    @GET(HttpUrl.GET_TRANASTION)
    Observable<IDToTransferBean> getTranstion(@Header("Authorization") String token, @Query("tokenId") int tokenId);

    @POST(HttpUrl.GET_TRANASTION)
    Observable<UpdateBean> sendTransferRequest(@Header("Authorization") String token, @Body RequestBody body);


    @GET(HttpUrl.GET_TRANASTION+"/{id}")
    Observable<DetailBean> getDetailOnID(@Header("Authorization") String token, @Path("id") int id);

    @GET(HttpUrl.GET_DEBIT)
    Observable<VPBalanceBean> getBalance(@Header("Authorization") String token);

    @POST(HttpUrl.GET_DEBIT)
    Observable<UpdateBean> sendDebitMsg(@Header("Authorization") String token,@Body RequestBody body);
}
