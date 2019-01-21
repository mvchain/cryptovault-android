package com.mvc.cryptovault_android.api;

import com.mvc.cryptovault_android.bean.ExchangeRateBean;
import com.mvc.cryptovault_android.bean.FinancialBean;
import com.mvc.cryptovault_android.bean.FinancialDetailBean;
import com.mvc.cryptovault_android.bean.FinancialListBean;
import com.mvc.cryptovault_android.bean.InvatationBean;
import com.mvc.cryptovault_android.bean.MnemonicsBean;
import com.mvc.cryptovault_android.bean.OptionBean;
import com.mvc.cryptovault_android.bean.TagBean;
import com.mvc.cryptovault_android.bean.UpsetMnemonicsBean;
import com.mvc.cryptovault_android.bean.VPBalanceBean;
import com.mvc.cryptovault_android.bean.AllAssetBean;
import com.mvc.cryptovault_android.bean.AssetListBean;
import com.mvc.cryptovault_android.bean.CurrencyBean;
import com.mvc.cryptovault_android.bean.DetailBean;
import com.mvc.cryptovault_android.bean.HistroyBean;
import com.mvc.cryptovault_android.bean.HttpTokenBean;
import com.mvc.cryptovault_android.bean.IDToTransferBean;
import com.mvc.cryptovault_android.bean.KLineBean;
import com.mvc.cryptovault_android.bean.LoginBean;
import com.mvc.cryptovault_android.bean.MsgBean;
import com.mvc.cryptovault_android.bean.PurchaseBean;
import com.mvc.cryptovault_android.bean.ReceiptBean;
import com.mvc.cryptovault_android.bean.RecorBean;
import com.mvc.cryptovault_android.bean.TogeBean;
import com.mvc.cryptovault_android.bean.TogeHisBean;
import com.mvc.cryptovault_android.bean.TrandChildBean;
import com.mvc.cryptovault_android.bean.TrandOrderBean;
import com.mvc.cryptovault_android.bean.TrandPurhBean;
import com.mvc.cryptovault_android.bean.UpdateBean;
import com.mvc.cryptovault_android.bean.UserInfoBean;
import com.mvc.cryptovault_android.common.HttpUrl;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiStore {

    @Headers("Accept-Language: zh-cn")
    @POST(HttpUrl.LOGIN)
    Observable<LoginBean> login(@Body RequestBody loginInfo);

    @Headers("Accept-Language: zh-cn")
    @POST(HttpUrl.TOKEN_REFRESH)
    Call<HttpTokenBean> refreshToken(@Header("Authorization") String token);

    @Headers("Accept-Language: zh-cn")
    @GET(HttpUrl.GET_USER_INFO)
    Observable<UserInfoBean> getUserInfo(@Header("Authorization") String token);

    @Headers("Accept-Language: zh-cn")
    @GET(HttpUrl.GET_ASSET_LIST)
    Observable<AssetListBean> getAssetList(@Header("Authorization") String token);

    @Headers("Accept-Language: zh-cn")
    @PUT(HttpUrl.GET_ASSET_LIST)
    Observable<UpdateBean> updateAssetList(@Header("Authorization") String token, @Body RequestBody putBody);

    @Headers("Accept-Language: zh-cn")
    @GET(HttpUrl.GET_ASSET_ALL)
    Observable<AllAssetBean> getAssetAll(@Header("Authorization") String token);

    @Headers("Accept-Language: zh-cn")
    @GET(HttpUrl.GET_CURRENCY_ALL)
    Observable<CurrencyBean> getCurrencyAll(@Header("Authorization") String token);

    @Headers("Accept-Language: zh-cn")
    @GET(HttpUrl.GET_MESSAGE)
    Observable<MsgBean> getMsg(@Header("Authorization") String token, @Query("timestamp") long timestamp, @Query("type") int type, @Query("pageSize") int pageSize);

    @Headers("Accept-Language: zh-cn")
    @GET(HttpUrl.GET_PAIR)
    Observable<TrandChildBean> getVrtAndBalance(@Header("Authorization") String token, @Query("pairType") int pairType);

    @Headers("Accept-Language: zh-cn")
    @GET(HttpUrl.GET_PAIR)
    Observable<TrandChildBean> getAllVrtAndBalance(@Header("Authorization") String token);

    @Headers("Accept-Language: zh-cn")
    @GET(HttpUrl.GET_RATE)
    Observable<ExchangeRateBean> getExchangeRate(@Header("Authorization") String token);

    @Headers("Accept-Language: zh-cn")
    @GET(HttpUrl.GET_TRANSACTIONS)
    Observable<HistroyBean> getHistroyRecording(@Header("Authorization") String token,
                                                @Query("id") int id,
                                                @Query("pageSize") int pageSize,
                                                @Query("tokenId") int tokenId,
                                                @Query("transactionType") int transactionType,
                                                @Query("type") int type);

    @Headers("Accept-Language: zh-cn")
    @GET(HttpUrl.GET_CROWDFUNDING)
    Observable<TogeBean> getCrowdfunding(@Header("Authorization") String token,
                                         @Query("pageSize") int pageSize,
                                         @Query("projectId") int projectId,
                                         @Query("projectType") int projectType,
                                         @Query("type") int type);

    @Headers("Accept-Language: zh-cn")
    @GET(HttpUrl.GET_QCODE)
    Observable<ReceiptBean> getRecriptQCode(@Header("Authorization") String token, @Query("tokenId") int tokenId);

    @Headers("Accept-Language: zh-cn")
    @GET(HttpUrl.GET_TRANASTION)
    Observable<IDToTransferBean> getTranstion(@Header("Authorization") String token, @Query("tokenId") int tokenId);

    @Headers("Accept-Language: zh-cn")
    @POST(HttpUrl.GET_TRANASTION)
    Observable<UpdateBean> sendTransferRequest(@Header("Authorization") String token, @Body RequestBody body);

    @Headers("Accept-Language: zh-cn")
    @GET(HttpUrl.GET_TRANASTION + "/{id}")
    Observable<DetailBean> getDetailOnID(@Header("Authorization") String token, @Path("id") int id);

    @Headers("Accept-Language: zh-cn")
    @GET(HttpUrl.GET_DEBIT)
    Observable<VPBalanceBean> getBalance(@Header("Authorization") String token);

    @Headers("Accept-Language: zh-cn")
    @POST(HttpUrl.GET_DEBIT)
    Observable<UpdateBean> sendDebitMsg(@Header("Authorization") String token, @Body RequestBody body);

    @Headers("Accept-Language: zh-cn")
    @GET(HttpUrl.GET_RESERVATION)
    Observable<TogeHisBean> getReservation(@Header("Authorization") String token,
                                           @Query("id") int id,
                                           @Query("pageSize") int pageSize,
                                           @Query("projectName") String projectName,
                                           @Query("type") int type);

    @Headers("Accept-Language: zh-cn")
    @GET("/project/{id}/purchase")
    Observable<PurchaseBean> getPurchaseOnID(@Header("Authorization") String token, @Path("id") int id);

    @Headers("Accept-Language: zh-cn")
    @POST("/project/{id}/purchase")
    Observable<UpdateBean> sendReservationRequest(@Header("Authorization") String token, @Body RequestBody body, @Path("id") int id);

    @Headers("Accept-Language: zh-cn")
    @GET(HttpUrl.GET_RECORDING)
    Observable<RecorBean> getRecording(@Header("Authorization") String token,
                                       @Query("id") int id,
                                       @Query("pageSize") int pageSize,
                                       @Query("pairId") int pairId,
                                       @Query("transactionType") int transactionType,
                                       @Query("type") int type);

    /**
     * 发布挂单
     *
     * @param token
     * @return
     */
    @Headers("Accept-Language: zh-cn")
    @POST(HttpUrl.GET_RECORDING)
    Observable<UpdateBean> releaseOrder(@Header("Authorization") String token,
                                        @Body RequestBody body);

    @Headers("Accept-Language: zh-cn")
    @GET(HttpUrl.GET_TRANSACTIONINFO)
    Observable<TrandPurhBean> getTransactionInfo(@Header("Authorization") String token,
                                                 @Query("pairId") int pairId,
                                                 @Query("transactionType") int transactionType);

    @Headers("Accept-Language: zh-cn")
    @GET(HttpUrl.GET_TPARTAKE)
    Observable<TrandOrderBean> getTpartake(@Header("Authorization") String token,
                                           @Query("id") int id,
                                           @Query("pageSize") int pageSize,
                                           @Query("pairId") String pairId,
                                           @Query("status") int status,
                                           @Query("transactionType") String transactionType,
                                           @Query("type") int type);

    /**
     * 取消挂单
     *
     * @param token
     * @return
     */
    @Headers("Accept-Language: zh-cn")
    @DELETE(HttpUrl.GET_RECORDING + "/{id}")
    Observable<UpdateBean> cancleOrder(@Header("Authorization") String token,
                                       @Path("id") int id);

    @Headers("Accept-Language: zh-cn")
    @GET(HttpUrl.GET_KLINE)
    Observable<KLineBean> getKLine(@Header("Authorization") String token,
                                   @Query("pairId") int pairId);

    /**
     * 获取验证码
     *
     * @param cellphone
     * @return
     */
    @Headers("Accept-Language: zh-cn")
    @GET(HttpUrl.SEND_CODE)
    Observable<UpdateBean> sendCode(@Query("cellphone") String cellphone);

    /**
     * 获取用户推送分组
     *
     * @param token
     * @return
     */
    @Headers("Accept-Language: zh-cn")
    @GET(HttpUrl.GET_PUSH_TAG)
    Observable<TagBean> getPushTag(@Header("Authorization") String token);

    @Headers("Accept-Language: zh-cn")
    @POST(HttpUrl.REGSITER_EMAIL)
    Observable<HttpTokenBean> sendInvitationRequest(@Body RequestBody body);

    /**
     * 获取邮箱验证码
     *
     * @param email
     * @return
     */
    @Headers("Accept-Language: zh-cn")
    @GET(HttpUrl.SEND_EMAIL_CODE)
    Observable<HttpTokenBean> sendValiCode(@Query("email") String email);

    @Headers("Accept-Language: zh-cn")
    @POST(HttpUrl.USER_REGISTER)
    Observable<MnemonicsBean> userRegister(@Body RequestBody email);

    @Headers("Accept-Language: zh-cn")
    @GET(HttpUrl.USER_MNEMONICS)
    Observable<UpsetMnemonicsBean> getUserMnemonic(@Query("email") String email);

    @Headers("Accept-Language: zh-cn")
    @POST(HttpUrl.USER_MNEMONICS)
    Observable<LoginBean> postUserMnemonic(@Body RequestBody mne);

    @Headers("Accept-Language: zh-cn")
    @POST(HttpUrl.USER_RESET_VERRFICATION)
    Observable<HttpTokenBean> updatePassword(@Body RequestBody mne);

    @Headers("Accept-Language: zh-cn")
    @PUT(HttpUrl.USER_FORGET)
    Observable<UpdateBean> userForget(@Body RequestBody mne);


    @Headers("Accept-Language: zh-cn")
    @PUT(HttpUrl.USER_PASSWORD)
    Observable<UpdateBean> setLoginPassword(@Body RequestBody mne);

    @Headers("Accept-Language: zh-cn")
    @PUT(HttpUrl.USER_TRANSACTIONPASSWORD)
    Observable<UpdateBean> setPayPassword(@Body RequestBody mne);

    @Headers("Accept-Language: zh-cn")
    @GET(HttpUrl.USER_EMAIL)
    Observable<HttpTokenBean> sendEmail(@Header("Authorization") String token);

    @Headers("Accept-Language: zh-cn")
    @POST(HttpUrl.USER_EMAIL)
    Observable<HttpTokenBean> verificationCode(@Header("Authorization") String token, @Body RequestBody code);

    @Headers("Accept-Language: zh-cn")
    @PUT(HttpUrl.USER_EMAIL)
    Observable<UpdateBean> bindNewsEmail(@Header("Authorization") String token, @Body RequestBody code);

    @Headers("Accept-Language: zh-cn")
    @GET(HttpUrl.USER_INVITATION)
    Observable<HttpTokenBean> getInvitation(@Header("Authorization") String token);

    @Headers("Accept-Language: zh-cn")
    @GET(HttpUrl.USER_RECOMMEND)
    Observable<InvatationBean> getRecommendInvatation(@Header("Authorization") String token, @Query("inviteUserId") int inviteUserId, @Query("pageSize") int pageSize);

    @Headers("Accept-Language: zh-cn")
    @GET(HttpUrl.USER_SIGN)
    Observable<UpdateBean> getWhetherToSignIn(@Header("Authorization") String token);

    @Headers("Accept-Language: zh-cn")
    @PUT(HttpUrl.USER_SIGN)
    Observable<UpdateBean> putSignIn(@Header("Authorization") String token);

    @Headers("Accept-Language: zh-cn")
    @GET(HttpUrl.FINANCIAL)
    Observable<FinancialListBean> getFinancialList(@Header("Authorization") String token);

    @Headers("Accept-Language: zh-cn")
    @GET(HttpUrl.FINANCIAL_BALANCE)
    Observable<FinancialBean> getFinancialBalance(@Header("Authorization") String token);

    @Headers("Accept-Language: zh-cn")
    @GET(HttpUrl.FINANCIAL + "/{id}")
    Observable<FinancialDetailBean> getFinancialDetail(@Header("Authorization") String token, @Path("id") int id);

    @Headers("Accept-Language: zh-cn")
    @POST(HttpUrl.FINANCIAL + "/{id}")
    Observable<UpdateBean> depositFinancial(@Header("Authorization") String token, @Body RequestBody body, @Path("id") int id);

    @Headers("Accept-Language: zh-cn")
    @GET(HttpUrl.FINANCIAL_PARTAKE)
    Observable<OptionBean> getFinancialPartake(@Header("Authorization") String token, @Query("financialType") int financialType, @Query("id") int id, @Query("pageSize") int pageSize);
}
