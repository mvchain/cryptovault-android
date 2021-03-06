package com.mvc.cryptovault_android.contract

import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.base.IBaseActivity
import com.mvc.cryptovault_android.base.IBaseModel
import com.mvc.cryptovault_android.bean.BlockBrowserDataBean
import com.mvc.cryptovault_android.bean.BlockNodeBean
import com.mvc.cryptovault_android.bean.HttpTokenBean
import io.reactivex.Observable

interface IBrowserContract {
    abstract class IBrowserPresenter : BasePresenter<IBrowserModel, IBrowserView>() {
        abstract fun getBlockBrowserData()
        abstract fun getBlockNode()
        abstract fun getBlockAddressExist(address: String)
    }

    interface IBrowserModel : IBaseModel {
        fun getBlockBrowserData(): Observable<BlockBrowserDataBean>
        fun getBlockNode(): Observable<ArrayList<BlockNodeBean>>
        fun getBlockAddressExist(address: String): Observable<HttpTokenBean>
    }

    interface IBrowserView : IBaseActivity {
        fun blockSuccess(blockBrowserDataBean: BlockBrowserDataBean)
        fun blockNodeSuccess(blockNodeBean: ArrayList<BlockNodeBean>)
        fun blockFailed(msg: String)
        fun addressSuccess(address: String)
        fun addressFailed(address: String)
    }
}