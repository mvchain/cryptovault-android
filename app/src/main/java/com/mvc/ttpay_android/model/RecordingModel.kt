package com.mvc.ttpay_android.model

import com.mvc.ttpay_android.base.BaseModel
import com.mvc.ttpay_android.contract.IRecordingContract

class RecordingModel : BaseModel(), IRecordingContract.IRecordingModel {

//    override fun getRecorList(id: Int, pageSize: Int, pairId: Int, transactionType: Int, type: Int): Observable<RecorBean> {
//        return RetrofitUtils.client(MyApplication.getBaseUrl(),ApiStore::class.java)
//                .getRecording(MyApplication.getTOKEN(), id, pageSize, pairId, transactionType, type)
//                .compose(RxHelper.rxSchedulerHelper())
//                .map { recorBean -> recorBean }
//    }

    companion object {
        val instance: RecordingModel
            get() = RecordingModel()
    }
}
