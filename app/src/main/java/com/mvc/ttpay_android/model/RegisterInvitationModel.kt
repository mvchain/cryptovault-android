package com.mvc.ttpay_android.model

import com.mvc.ttpay_android.MyApplication
import com.mvc.ttpay_android.api.ApiStore
import com.mvc.ttpay_android.base.BaseModel
import com.mvc.ttpay_android.bean.HttpTokenBean
import com.mvc.ttpay_android.contract.IRegisterInvitationConstrat
import com.mvc.ttpay_android.utils.RetrofitUtils
import com.mvc.ttpay_android.utils.RxHelper
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject

class RegisterInvitationModel : BaseModel(), IRegisterInvitationConstrat.InvitationModel {


    companion object {
        val instance: RegisterInvitationModel
            get() = RegisterInvitationModel()
    }
    override fun sendValiCode(email: String): Observable<HttpTokenBean> {
        return RetrofitUtils.client(MyApplication.getBaseUrl(),ApiStore::class.java).sendValiCode(email)
                .compose(RxHelper.rxSchedulerHelper())
                .map { httpTokenBean -> httpTokenBean }
    }
    override fun sendInvitationRequest(email: String, code: String): Observable<HttpTokenBean> {
        var bodyJson = JSONObject()
        bodyJson.put("email", email)
        bodyJson.put("valiCode", code)
        var body = RequestBody.create(MediaType.parse("text/html"), bodyJson.toString())
        return RetrofitUtils.client(MyApplication.getBaseUrl(),ApiStore::class.java).sendInvitationRequest(body)
                .compose(RxHelper.rxSchedulerHelper())
                .map { httpTokenBean -> httpTokenBean }
    }
}