package com.mvc.cryptovault_android.model

import com.blankj.utilcode.util.EncryptUtils
import com.blankj.utilcode.util.SPUtils
import com.mvc.cryptovault_android.MyApplication
import com.mvc.cryptovault_android.api.ApiStore
import com.mvc.cryptovault_android.base.BaseModel
import com.mvc.cryptovault_android.bean.LoginBean
import com.mvc.cryptovault_android.common.Constant.SP.USER_EMAIL
import com.mvc.cryptovault_android.contract.IGoogleContract
import com.mvc.cryptovault_android.utils.RetrofitUtils
import com.mvc.cryptovault_android.utils.RxHelper
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONException
import org.json.JSONObject

class GoogleModel : BaseModel(), IGoogleContract.GoogleModel {
    companion object {
        val instance: GoogleModel
            get() = GoogleModel()
    }

    override fun changeGoogleVerification(googleCode: String, password: String, status: Int): Observable<LoginBean> {
        return RetrofitUtils.client(MyApplication.getBaseUrl(), ApiStore::class.java).getUserSalt(MyApplication.getTOKEN()
                , SPUtils.getInstance().getString(USER_EMAIL))
                .compose(RxHelper.rxSchedulerHelper())
                .flatMap { dataBean ->
                    val jsonObject = JSONObject()
                    try {
                        jsonObject.put("googleCode", googleCode)
                        jsonObject.put("password", EncryptUtils.encryptMD5ToString(dataBean.data + EncryptUtils.encryptMD5ToString(password)))
                        jsonObject.put("status", status)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                    val loginInfo = jsonObject.toString()
                    val requestBody = RequestBody.create(MediaType.parse("text/html"), loginInfo)
                    RetrofitUtils.client(MyApplication.getBaseUrl(), ApiStore::class.java)
                            .changeGoogleVerification(MyApplication.getTOKEN(), requestBody)
                            .compose(RxHelper.rxSchedulerHelper())

                }
                .map { loginBean -> loginBean }
    }
}