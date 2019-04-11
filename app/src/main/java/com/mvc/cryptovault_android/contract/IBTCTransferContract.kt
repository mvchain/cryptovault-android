package com.mvc.cryptovault_android.contract

import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.base.IBaseActivity
import com.mvc.cryptovault_android.base.IBaseModel
import com.mvc.cryptovault_android.bean.IDToTransferBean
import com.mvc.cryptovault_android.bean.UpdateBean

import io.reactivex.Observable

interface IBTCTransferContract {
    abstract class BTCTransferPresenter : BasePresenter<BTCTransferModel, BTCTransferView>() {
        abstract fun getDetail(id: Int)
        abstract fun getTransFee(address: String)
        abstract fun sendTransferMsg(address: String, password: String, tokenId: Int, value: String)
    }

    interface BTCTransferModel : IBaseModel {
        fun getDetail(id: Int): Observable<IDToTransferBean>
        fun sendTransferMsg(address: String, password: String, tokenId: Int, value: String): Observable<UpdateBean>
        fun getTransFee(address: String): Observable<UpdateBean>
    }

    interface BTCTransferView : IBaseActivity {
        fun showSuccess(data: IDToTransferBean.DataBean)
        fun transferCallBack(bean: UpdateBean)
        fun transFeeStatus(isStation:Boolean)

    }
}
