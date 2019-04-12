package com.mvc.ttpay_android.model

import com.blankj.utilcode.util.EncryptUtils
import com.mvc.ttpay_android.MyApplication
import com.mvc.ttpay_android.api.ApiStore
import com.mvc.ttpay_android.base.BaseModel
import com.mvc.ttpay_android.bean.*
import com.mvc.ttpay_android.contract.ILoginContract
import com.mvc.ttpay_android.utils.RetrofitUtils
import com.mvc.ttpay_android.utils.RxHelper

import org.json.JSONException
import org.json.JSONObject

import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.RequestBody

class LoginModel : BaseModel(), ILoginContract.ILoginModel {

    override fun getLoginStatus(token: String, email: String, pwd: String, code: String): Observable<LoginBean> {
        return RetrofitUtils.client(MyApplication.getBaseUrl(), ApiStore::class.java).getUserSalt(MyApplication.getTOKEN(), email)
                .compose(RxHelper.rxSchedulerHelper())
                .flatMap{ salt ->
                    val jsonObject = JSONObject()
                    try {
                        jsonObject.put("imageToken", token)
                        jsonObject.put("username", email)
                        jsonObject.put("password", EncryptUtils.encryptMD5ToString("${salt.data}${EncryptUtils.encryptMD5ToString(pwd)}"))
                        jsonObject.put("validCode", code)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }

                    val loginInfo = jsonObject.toString()
                    val requestBody = RequestBody.create(MediaType.parse("text/html"), loginInfo)
                    RetrofitUtils.client(MyApplication.getBaseUrl(), ApiStore::class.java).login(requestBody).compose(RxHelper.rxSchedulerHelper())
                }.map { loginBean -> loginBean }
    }

    override fun sendCode(cellphone: String): Observable<HttpTokenBean> {
        return RetrofitUtils.client(MyApplication.getBaseUrl(), ApiStore::class.java).sendValiCode(cellphone)
                .compose(RxHelper.rxSchedulerHelper())
                .map { httpTokenBean -> httpTokenBean }
    }

    override fun getValid(email: String): Observable<LoginValidBean> {
        return RetrofitUtils.client(MyApplication.getBaseUrl(), ApiStore::class.java).getValid(email).compose(RxHelper.rxSchedulerHelper())
                .map { loginValidBean -> loginValidBean }
    }

    override fun postValid(geetest_challenge: String, geetest_seccode: String, geetest_validate: String, status: Int, uid: String): Observable<HttpTokenBean> {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("geetest_challenge", geetest_challenge)
            jsonObject.put("geetest_seccode", geetest_seccode)
            jsonObject.put("geetest_validate", geetest_validate)
            jsonObject.put("status", status)
            jsonObject.put("uid", uid)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val loginInfo = jsonObject.toString()
        val requestBody = RequestBody.create(MediaType.parse("text/html"), loginInfo)
        return RetrofitUtils.client(MyApplication.getBaseUrl(), ApiStore::class.java).postValid(requestBody)
                .compose(RxHelper.rxSchedulerHelper())
                .map { pstValid -> pstValid }
    }

    companion object {

        val instance: LoginModel?
            get() = LoginModel()
    }
}
