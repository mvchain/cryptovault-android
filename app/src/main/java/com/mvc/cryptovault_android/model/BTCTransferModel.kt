package com.mvc.cryptovault_android.model

import com.blankj.utilcode.util.EncryptUtils
import com.blankj.utilcode.util.SPUtils
import com.mvc.cryptovault_android.MyApplication
import com.mvc.cryptovault_android.api.ApiStore
import com.mvc.cryptovault_android.base.BaseModel
import com.mvc.cryptovault_android.bean.IDToTransferBean
import com.mvc.cryptovault_android.bean.UpdateBean
import com.mvc.cryptovault_android.contract.IBTCTransferContract
import com.mvc.cryptovault_android.utils.RetrofitUtils
import com.mvc.cryptovault_android.utils.RxHelper

import org.json.JSONException
import org.json.JSONObject

import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.RequestBody

import com.mvc.cryptovault_android.common.Constant.SP.USER_EMAIL

class BTCTransferModel : BaseModel(), IBTCTransferContract.BTCTransferModel {
    override fun getTransFee(address: String): Observable<UpdateBean> {
        return RetrofitUtils.client(MyApplication.getBaseUrl(),ApiStore::class.java).getTransFee(MyApplication.getTOKEN(), address)
                .compose(RxHelper.rxSchedulerHelper())
                .map { updateBean -> updateBean }
    }

    override fun getDetail(id: Int): Observable<IDToTransferBean> {
        return RetrofitUtils.client(MyApplication.getBaseUrl(), ApiStore::class.java).getTranstion(MyApplication.getTOKEN(), id).compose(RxHelper.rxSchedulerHelper()).map { idbean -> idbean }
    }

    override fun sendTransferMsg(address: String, password: String, tokenId: Int, value: String): Observable<UpdateBean> {
        return RetrofitUtils.client(MyApplication.getBaseUrl(), ApiStore::class.java)
                .getUserSalt(MyApplication.getTOKEN(), SPUtils.getInstance().getString(USER_EMAIL))
                .compose(RxHelper.rxSchedulerHelper())
                .flatMap { salt ->
                    val `object` = JSONObject()
                    try {
                        `object`.put("token", MyApplication.getTOKEN())
                        `object`.put("address", address)
                        `object`.put("password", EncryptUtils.encryptMD5ToString(salt.data + EncryptUtils.encryptMD5ToString(password)))
                        `object`.put("tokenId", tokenId)
                        `object`.put("value", value)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }


                    val body = RequestBody.create(MediaType.parse("text/html"), `object`.toString())
                    RetrofitUtils.client(MyApplication.getBaseUrl(), ApiStore::class.java).sendTransferRequest(MyApplication.getTOKEN(), body).compose(RxHelper.rxSchedulerHelper())
                }.map { updateBean -> updateBean }
    }

    companion object {
        val instance: BTCTransferModel
            get() = BTCTransferModel()
    }
}
