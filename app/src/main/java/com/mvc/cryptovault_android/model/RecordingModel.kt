package com.mvc.cryptovault_android.model

import com.mvc.cryptovault_android.MyApplication
import com.mvc.cryptovault_android.api.ApiStore
import com.mvc.cryptovault_android.base.BaseModel
import com.mvc.cryptovault_android.bean.RecorBean
import com.mvc.cryptovault_android.contract.RecordingContract
import com.mvc.cryptovault_android.utils.RetrofitUtils
import com.mvc.cryptovault_android.utils.RxHelper

import io.reactivex.Observable

class RecordingModel : BaseModel(), RecordingContract.IRecordingModel {

    override fun getRecorList(id: Int, pageSize: Int, pairId: Int, transactionType: Int, type: Int): Observable<RecorBean> {
        return RetrofitUtils.client(MyApplication.getBaseUrl(),ApiStore::class.java)
                .getRecording(MyApplication.getTOKEN(), id, pageSize, pairId, transactionType, type)
                .compose(RxHelper.rxSchedulerHelper())
                .map { recorBean -> recorBean }
    }

    companion object {
        val instance: RecordingModel
            get() = RecordingModel()
    }
}
