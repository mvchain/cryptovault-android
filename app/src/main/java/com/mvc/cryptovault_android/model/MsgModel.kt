package com.mvc.cryptovault_android.model

import com.mvc.cryptovault_android.MyApplication
import com.mvc.cryptovault_android.api.ApiStore
import com.mvc.cryptovault_android.base.BaseModel
import com.mvc.cryptovault_android.bean.MsgBean
import com.mvc.cryptovault_android.contract.IMsgContract
import com.mvc.cryptovault_android.utils.RetrofitUtils
import com.mvc.cryptovault_android.utils.RxHelper

import io.reactivex.Observable

class MsgModel : BaseModel(), IMsgContract.IMsgModel {

    override fun getMsg(timestamp: Long, type: Int, pageSize: Int): Observable<MsgBean> {
        return RetrofitUtils.client(MyApplication.getBaseUrl(), ApiStore::class.java).getMsg(MyApplication.getTOKEN(), timestamp, type, pageSize).compose(RxHelper.rxSchedulerHelper()).map { msgBean -> msgBean }
    }

    companion object {
        val instance: MsgModel?
            get() = MsgModel()
    }
}
