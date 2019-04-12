package com.mvc.ttpay_android.api

import com.mvc.ttpay_android.bean.*
import com.mvc.ttpay_android.common.HttpUrl

import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiStore {


    @get:GET(HttpUrl.BLOCK_LAST)
    val blockLast: Observable<BlockLastBean>

    @POST(HttpUrl.LOGIN)
    fun login(@Body loginInfo: RequestBody): Observable<LoginBean>

    @POST(HttpUrl.TOKEN_REFRESH)
    fun refreshToken(@Header("Authorization") token: String): Call<HttpTokenBean>

    @GET(HttpUrl.GET_USER_INFO)
    fun getUserInfo(@Header("Authorization") token: String): Observable<UserInfoBean>

    @GET(HttpUrl.GET_ASSET_LIST)
    fun getAssetList(@Header("Authorization") token: String): Observable<AssetListBean>

    @PUT(HttpUrl.GET_ASSET_LIST)
    fun updateAssetList(@Header("Authorization") token: String, @Body putBody: RequestBody): Observable<UpdateBean>

    @GET(HttpUrl.GET_ASSET_ALL)
    fun getAssetAll(@Header("Authorization") token: String): Observable<AllAssetBean>

    @GET(HttpUrl.GET_CURRENCY_ALL)
    fun getCurrencyAll(@Header("Authorization") token: String): Observable<CurrencyBean>

    @GET(HttpUrl.GET_MESSAGE)
    fun getMsg(@Header("Authorization") token: String, @Query("timestamp") timestamp: Long, @Query("type") type: Int, @Query("pageSize") pageSize: Int): Observable<MsgBean>

    @GET(HttpUrl.GET_PAIR)
    fun getVrtAndBalance(@Header("Authorization") token: String, @Query("pairType") pairType: Int): Observable<TrandChildBean>

    @GET(HttpUrl.GET_PAIR)
    fun getAllVrtAndBalance(@Header("Authorization") token: String): Observable<TrandChildBean>

    @GET(HttpUrl.GET_RATE)
    fun getExchangeRate(@Header("Authorization") token: String): Observable<ExchangeRateBean>


    @GET(HttpUrl.GET_TRANSACTIONS)
    fun getHistroyRecording(@Header("Authorization") token: String,
                            @Query("classify") classify: Int,
                            @Query("id") id: Int,
                            @Query("pageSize") pageSize: Int,
                            @Query("tokenId") tokenId: Int,
                            @Query("transactionType") transactionType: Int,
                            @Query("type") type: Int): Observable<HistroyBean>


    @GET(HttpUrl.GET_CROWDFUNDING)
    fun getCrowdfunding(@Header("Authorization") token: String,
                        @Query("pageSize") pageSize: Int,
                        @Query("projectId") projectId: Int,
                        @Query("projectType") projectType: Int,
                        @Query("type") type: Int): Observable<TogeBean>


    @GET(HttpUrl.GET_QCODE)
    fun getRecriptQCode(@Header("Authorization") token: String, @Query("tokenId") tokenId: Int): Observable<ReceiptBean>


    @GET(HttpUrl.GET_TRANASTION)
    fun getTranstion(@Header("Authorization") token: String, @Query("tokenId") tokenId: Int): Observable<IDToTransferBean>


    @GET(HttpUrl.ASSETS_INNER)
    fun getTransFee(@Header("Authorization") token: String, @Query("address") address: String): Observable<UpdateBean>


    @POST(HttpUrl.GET_TRANASTION)
    fun sendTransferRequest(@Header("Authorization") token: String, @Body body: RequestBody): Observable<UpdateBean>


    @GET(HttpUrl.GET_TRANASTION + "/{id}")
    fun getDetailOnID(@Header("Authorization") token: String, @Path("id") id: Int): Observable<DetailBean>


    @GET(HttpUrl.GET_DEBIT)
    fun getBalance(@Header("Authorization") token: String): Observable<VPBalanceBean>


    @POST(HttpUrl.GET_DEBIT)
    fun sendDebitMsg(@Header("Authorization") token: String, @Body body: RequestBody): Observable<UpdateBean>


    @GET(HttpUrl.GET_RESERVATION)
    fun getReservation(@Header("Authorization") token: String,
                       @Query("id") id: Int,
                       @Query("pageSize") pageSize: Int,
                       @Query("projectName") projectName: String,
                       @Query("type") type: Int): Observable<TogeHisBean>


    @GET(HttpUrl.URL_PATH + "/project/{id}/purchase")
    fun getPurchaseOnID(@Header("Authorization") token: String, @Path("id") id: Int): Observable<PurchaseBean>


    @POST(HttpUrl.URL_PATH + "/project/{id}/purchase")
    fun sendReservationRequest(@Header("Authorization") token: String, @Body body: RequestBody, @Path("id") id: Int): Observable<UpdateBean>


    @GET(HttpUrl.GET_RECORDING)
    fun getRecording(@Header("Authorization") token: String,
                     @Query("id") id: Int,
                     @Query("pageSize") pageSize: Int,
                     @Query("pairId") pairId: Int,
                     @Query("transactionType") transactionType: Int,
                     @Query("type") type: Int): Observable<RecorBean>

    /**
     * 发布挂单
     *
     * @param token
     * @return
     */

    @POST(HttpUrl.GET_RECORDING)
    fun releaseOrder(@Header("Authorization") token: String,
                     @Body body: RequestBody): Observable<UpdateBean>


    @GET(HttpUrl.GET_TRANSACTIONINFO)
    fun getTransactionInfo(@Header("Authorization") token: String,
                           @Query("pairId") pairId: Int,
                           @Query("transactionType") transactionType: Int): Observable<TrandPurhBean>


    @GET(HttpUrl.GET_TPARTAKE)
    fun getTpartake(@Header("Authorization") token: String,
                    @Query("id") id: Int,
                    @Query("pageSize") pageSize: Int,
                    @Query("pairId") pairId: String,
                    @Query("status") status: Int,
                    @Query("transactionType") transactionType: String,
                    @Query("type") type: Int): Observable<TrandOrderBean>

    /**
     * 取消挂单
     *
     * @param token
     * @return
     */

    @DELETE(HttpUrl.GET_RECORDING + "/{id}")
    fun cancleOrder(@Header("Authorization") token: String,
                    @Path("id") id: Int): Observable<UpdateBean>


    @GET(HttpUrl.GET_KLINE)
    fun getKLine(@Header("Authorization") token: String,
                 @Query("pairId") pairId: Int): Observable<KLineBean>


    @GET(HttpUrl.GET_PAIR_TICKERS)
    fun getPairTickers(@Header("Authorization") token: String,
                       @Query("pairId") pairId: Int): Observable<PairTickersBean>

    /**
     * 获取验证码
     *
     * @param cellphone
     * @return
     */

    @GET(HttpUrl.SEND_CODE)
    fun sendCode(@Query("cellphone") cellphone: String): Observable<UpdateBean>

    /**
     * 获取用户推送分组
     *
     * @param token
     * @return
     */

    @GET(HttpUrl.GET_PUSH_TAG)
    fun getPushTag(@Header("Authorization") token: String): Observable<TagBean>


    @POST(HttpUrl.REGSITER_EMAIL)
    fun sendInvitationRequest(@Body body: RequestBody): Observable<HttpTokenBean>

    /**
     * 获取邮箱验证码
     *
     * @param email
     * @return
     */

    @GET(HttpUrl.SEND_EMAIL_CODE)
    fun sendValiCode(@Query("email") email: String): Observable<HttpTokenBean>


    @POST(HttpUrl.USER_REGISTER)
    fun userRegister(@Body email: RequestBody): Observable<LoginBean>


    @GET(HttpUrl.USER_MNEMONICS)
    fun getUserMnemonic(@Query("email") email: String): Observable<UpsetMnemonicsBean>


    @POST(HttpUrl.USER_MNEMONICS)
    fun postUserMnemonic(@Body mne: RequestBody): Observable<LoginBean>


    @POST(HttpUrl.USER_RESET_VERRFICATION)
    fun updatePassword(@Body mne: RequestBody): Observable<HttpTokenBean>


    @PUT(HttpUrl.USER_FORGET)
    fun userForget(@Body mne: RequestBody): Observable<UpdateBean>


    @PUT(HttpUrl.USER_PASSWORD)
    fun setLoginPassword(@Body mne: RequestBody): Observable<UpdateBean>


    @PUT(HttpUrl.USER_TRANSACTIONPASSWORD)
    fun setPayPassword(@Body mne: RequestBody): Observable<UpdateBean>


    @GET(HttpUrl.USER_EMAIL)
    fun sendEmail(@Header("Authorization") token: String): Observable<HttpTokenBean>


    @POST(HttpUrl.USER_EMAIL)
    fun verificationCode(@Header("Authorization") token: String, @Body code: RequestBody): Observable<HttpTokenBean>


    @PUT(HttpUrl.USER_EMAIL)
    fun bindNewsEmail(@Header("Authorization") token: String, @Body code: RequestBody): Observable<UpdateBean>


    @GET(HttpUrl.USER_INVITATION)
    fun getInvitation(@Header("Authorization") token: String): Observable<HttpTokenBean>


    @GET(HttpUrl.USER_RECOMMEND)
    fun getRecommendInvatation(@Header("Authorization") token: String, @Query("inviteUserId") inviteUserId: Int, @Query("pageSize") pageSize: Int): Observable<InvatationBean>


    @GET(HttpUrl.USER_SIGN)
    fun getWhetherToSignIn(@Header("Authorization") token: String): Observable<UpdateBean>


    @PUT(HttpUrl.USER_SIGN)
    fun putSignIn(@Header("Authorization") token: String): Observable<UpdateBean>


    @GET(HttpUrl.FINANCIAL)
    fun getFinancialList(@Header("Authorization") token: String, @Query("id") id: Int, @Query("pageSize") pageSize: Int): Observable<FinancialListBean>


    @GET(HttpUrl.FINANCIAL_BALANCE)
    fun getFinancialBalance(@Header("Authorization") token: String): Observable<FinancialBean>


    @GET(HttpUrl.FINANCIAL + "/{id}")
    fun getFinancialDetail(@Header("Authorization") token: String, @Path("id") id: Int): Observable<FinancialDetailBean>


    @POST(HttpUrl.FINANCIAL + "/{id}")
    fun depositFinancial(@Header("Authorization") token: String, @Body body: RequestBody, @Path("id") id: Int): Observable<UpdateBean>


    @GET(HttpUrl.FINANCIAL_PARTAKE)
    fun getFinancialPartake(@Header("Authorization") token: String, @Query("financialType") financialType: Int, @Query("id") id: Int, @Query("pageSize") pageSize: Int): Observable<OptionBean>


    @GET(HttpUrl.FINANCIAL_PARTAKE + "/{id}")
    fun getOptionDetail(@Header("Authorization") token: String, @Path("id") id: Int): Observable<OptionDetailBean>


    @GET(HttpUrl.FINANCIAL_PARTAKE + "/{id}/detail")
    fun getDailyIncome(@Header("Authorization") token: String, @Path("id") id: Int, @Query("id") qId: Int, @Query("pageSize") pageSize: Int): Observable<OptionDailyIncomeBean>


    @POST(HttpUrl.FINANCIAL_PARTAKE + "/{id}")
    fun extractOptionDetail(@Header("Authorization") token: String, @Path("id") id: Int): Observable<UpdateBean>


    @GET(HttpUrl.UPDATE_APP)
    fun updateApk(@Header("Authorization") token: String, @Query("appType") appType: String): Observable<InstallApkBean>


    @GET(HttpUrl.PUBLISH)
    fun getPublishAll(@Header("Authorization") token: String, @Query("pageSize") pageSize: Int, @Query("projectId") projectId: Int): Observable<PublishBean>


    @GET(HttpUrl.PUBLISH_LIST + "{projectId}/publish/list")
    fun getPublishList(@Header("Authorization") token: String, @Path("projectId") projectId: Int, @Query("id") id: Int, @Query("pageSize") pageSize: Int): Observable<PublishDetailListBean>


    @GET(HttpUrl.PUBLISH_DETAIL + "{projectId}/publish")
    fun getPublishDetail(@Header("Authorization") token: String, @Path("projectId") projectId: Int): Observable<PublishDetailBean>


    @GET(HttpUrl.VALID)
    fun getValid(@Query("email") email: String): Observable<LoginValidBean>


    @POST(HttpUrl.VALID)
    fun postValid(@Body body: RequestBody): Observable<HttpTokenBean>


    @GET(HttpUrl.ASSET + "{tokenId}")
    fun getAssets(@Header("Authorization") token: String, @Path("tokenId") tokenId: Int): Observable<AssetsBean>


    @GET(HttpUrl.BLOCK_LIST)
    fun getBlockList(@Query("blockId") blockId: Int, @Query("pageSize") pageSize: Int): Observable<BlockListBean>


    @GET(HttpUrl.BLOCK_LIST + "/{blockId}")
    fun getBlockDetail(@Path("blockId") blockId: String): Observable<BlockDetailBean>


    @GET(HttpUrl.BLOCK_TRANSACTION_TX + "/{hash}")
    fun getTransferDetail(@Path("hash") hash: String): Observable<BlockTransferDetailBean>


    @GET(HttpUrl.BLOCK_ID_TRANSACTIONS)
    fun getBlockAllList(@Path("blockId") blockId: String, @Query("pageSize") pageSize: Int, @Query("transactionId") transactionId: Int): Observable<BlockTransactionBean>


    @GET(HttpUrl.BLOCK_TRANSACTION_LAST)
    fun getBlockTransactionLast(@Query("pageSize") pageSize: Int): Observable<BlockTransactionBean>


    @GET(HttpUrl.BLOCK_ADDRESS_EXIST)
    fun getBlockAddressExist(@Query("publicKey") publicKey: String): Observable<HttpTokenBean>


    @GET(HttpUrl.BLOCK_ADDRESS_BALANCE)
    fun getBlockBalance(@Query("publicKey") publicKey: String): Observable<BlockBalanceBean>


    @GET(HttpUrl.BLOCK_ADDRESS_ORDER)
    fun getBlockOrder(@Query("id") id: Int, @Query("pageSize") pageSize: Int, @Query("publicKey") publicKey: String): Observable<BlockOrderBean>


    @GET(HttpUrl.BLOCK_ADDRESS_ORDER_ID + "{id}")
    fun getBlockDetailOnId(@Path("id") id: Int): Observable<BlockOrderOnIdBean>

    @GET(HttpUrl.USER_GOOGLE)
    fun getGoogleInfo(@Header("Authorization") token: String): Observable<GoogleInfoBean>

    @PUT(HttpUrl.USER_GOOGLE)
    fun changeGoogleVerification(@Header("Authorization") token: String, @Body googleInfo: RequestBody): Observable<LoginBean>

    @POST(HttpUrl.USER_GOOGLE)
    fun verificationGoogle(@Header("Authorization") token: String, @Query("googleCode") googleCode: String): Observable<LoginBean>

    @POST(HttpUrl.USER_SALT)
    fun getUserSalt(@Header("Authorization") token: String, @Query("email") email: String): Observable<HttpTokenBean>

    @GET(HttpUrl.BANNER)
    fun getBanner(@Header("Authorization") token: String): Observable<BannerBean>
}
