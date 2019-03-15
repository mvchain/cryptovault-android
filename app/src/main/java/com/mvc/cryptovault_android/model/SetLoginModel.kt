package com.mvc.cryptovault_android.model

import com.mvc.cryptovault_android.MyApplication
import com.mvc.cryptovault_android.R.id.code
import com.mvc.cryptovault_android.api.ApiStore
import com.mvc.cryptovault_android.base.BaseModel
import com.mvc.cryptovault_android.bean.UpdateBean
import com.mvc.cryptovault_android.contract.SetPasswordContract
import com.mvc.cryptovault_android.utils.RetrofitUtils
import com.mvc.cryptovault_android.utils.RxHelper
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject

class SetLoginModel : BaseModel(), SetPasswordContract.SetPasswordModel {

    override fun setLoginPassword(password: String, newPassword: String): Observable<UpdateBean> {
        var json = JSONObject()
        json.put("newPassword", newPassword)
        json.put("password", password)
        var body = RequestBody.create(MediaType.parse("text/html"), json.toString())
        return RetrofitUtils.client(MyApplication.getBaseUrl(),ApiStore::class.java)
                .setLoginPassword(body)
                .compose(RxHelper.rxSchedulerHelper())
                .map { bean -> bean }
    }

    override fun setPayPassword(password: String, newPassword: String): Observable<UpdateBean> {
        var json = JSONObject()
        json.put("newPassword", newPassword)
        json.put("password", password)
        var body = RequestBody.create(MediaType.parse("text/html"), json.toString())
        return RetrofitUtils.client(MyApplication.getBaseUrl(),ApiStore::class.java)
                .setPayPassword(body)
                .compose(RxHelper.rxSchedulerHelper())
                .map { bean -> bean }
    }

    companion object {
        val instance: SetLoginModel
            get() = SetLoginModel()
    }
}