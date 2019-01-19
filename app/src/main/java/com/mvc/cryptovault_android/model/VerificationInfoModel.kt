package com.mvc.cryptovault_android.model

import com.mvc.cryptovault_android.api.ApiStore
import com.mvc.cryptovault_android.base.BaseModel
import com.mvc.cryptovault_android.bean.HttpTokenBean
import com.mvc.cryptovault_android.contract.VerificationInfoContract
import com.mvc.cryptovault_android.utils.RetrofitUtils
import com.mvc.cryptovault_android.utils.RxHelper
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject

class VerificationInfoModel : BaseModel(), VerificationInfoContract.VerificationInfoModel {
    override fun verification(email: String, type: Int, value: String): Observable<HttpTokenBean> {
        var json = JSONObject()
        json.put("email", email)
        json.put("resetType", type)
        json.put("value", value)
        var body = RequestBody.create(MediaType.parse("text/html"), json.toString())
        return RetrofitUtils.client(ApiStore::class.java)
                .updatePassword(body)
                .compose(RxHelper.rxSchedulerHelper())
                .map { httpToken -> httpToken }
    }

    companion object {
        val instance: VerificationInfoModel
            get() = VerificationInfoModel()
    }

}
