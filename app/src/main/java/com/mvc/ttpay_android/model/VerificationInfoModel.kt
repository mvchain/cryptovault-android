package com.mvc.ttpay_android.model

import com.mvc.ttpay_android.MyApplication
import com.mvc.ttpay_android.api.ApiStore
import com.mvc.ttpay_android.base.BaseModel
import com.mvc.ttpay_android.bean.HttpTokenBean
import com.mvc.ttpay_android.contract.IVerificationInfoContract
import com.mvc.ttpay_android.utils.RetrofitUtils
import com.mvc.ttpay_android.utils.RxHelper
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject

class VerificationInfoModel : BaseModel(), IVerificationInfoContract.VerificationInfoModel {
    override fun verification(email: String, type: Int, value: String): Observable<HttpTokenBean> {
        var json = JSONObject()
        json.put("email", email)
        json.put("resetType", type)
        json.put("value", value)
        var body = RequestBody.create(MediaType.parse("text/html"), json.toString())
        return RetrofitUtils.client(MyApplication.getBaseUrl(),ApiStore::class.java)
                .updatePassword(body)
                .compose(RxHelper.rxSchedulerHelper())
                .map { httpToken -> httpToken }
    }

    companion object {
        val instance: VerificationInfoModel
            get() = VerificationInfoModel()
    }

}
