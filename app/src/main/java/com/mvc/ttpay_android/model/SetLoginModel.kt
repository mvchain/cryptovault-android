package com.mvc.ttpay_android.model

import com.blankj.utilcode.util.EncryptUtils
import com.blankj.utilcode.util.SPUtils
import com.mvc.ttpay_android.MyApplication
import com.mvc.ttpay_android.api.ApiStore
import com.mvc.ttpay_android.base.BaseModel
import com.mvc.ttpay_android.bean.UpdateBean
import com.mvc.ttpay_android.common.Constant.SP.USER_EMAIL
import com.mvc.ttpay_android.contract.ISetPasswordContract
import com.mvc.ttpay_android.utils.RetrofitUtils
import com.mvc.ttpay_android.utils.RxHelper
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject

class SetLoginModel : BaseModel(), ISetPasswordContract.SetPasswordModel {

    override fun setLoginPassword(password: String, newPassword: String): Observable<UpdateBean> {
        return RetrofitUtils.client(MyApplication.getBaseUrl(), ApiStore::class.java)
                .getUserSalt(MyApplication.getTOKEN(), SPUtils.getInstance().getString(USER_EMAIL))
                .compose(RxHelper.rxSchedulerHelper())
                .flatMap { salt ->
                    var json = JSONObject()
                    json.put("newPassword", EncryptUtils.encryptMD5ToString(salt.data + EncryptUtils.encryptMD5ToString(newPassword)))
                    json.put("password", EncryptUtils.encryptMD5ToString(salt.data + EncryptUtils.encryptMD5ToString(password)))
                    var body = RequestBody.create(MediaType.parse("text/html"), json.toString())
                    RetrofitUtils.client(MyApplication.getBaseUrl(), ApiStore::class.java)
                            .setLoginPassword(body)
                            .compose(RxHelper.rxSchedulerHelper())
                }.map { bean -> bean }
    }

    override fun setPayPassword(password: String, newPassword: String): Observable<UpdateBean> {
        return RetrofitUtils.client(MyApplication.getBaseUrl(), ApiStore::class.java)
                .getUserSalt(MyApplication.getTOKEN(), SPUtils.getInstance().getString(USER_EMAIL))
                .compose(RxHelper.rxSchedulerHelper())
                .flatMap { salt ->
                    var json = JSONObject()
                    json.put("newPassword", EncryptUtils.encryptMD5ToString(salt.data + EncryptUtils.encryptMD5ToString(newPassword)))
                    json.put("password", EncryptUtils.encryptMD5ToString(salt.data + EncryptUtils.encryptMD5ToString(password)))
                    var body = RequestBody.create(MediaType.parse("text/html"), json.toString())
                    RetrofitUtils.client(MyApplication.getBaseUrl(), ApiStore::class.java)
                            .setPayPassword(body)
                            .compose(RxHelper.rxSchedulerHelper())
                }.map { bean -> bean }
    }

    companion object {
        val instance: SetLoginModel
            get() = SetLoginModel()
    }
}