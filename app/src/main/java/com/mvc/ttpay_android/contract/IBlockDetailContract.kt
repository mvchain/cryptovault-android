package com.mvc.ttpay_android.contract

import com.mvc.ttpay_android.base.BasePresenter
import com.mvc.ttpay_android.base.IBaseActivity
import com.mvc.ttpay_android.base.IBaseModel
import com.mvc.ttpay_android.bean.BlockDetailBean
import com.mvc.ttpay_android.bean.BlockListBean
import com.mvc.ttpay_android.bean.BlockTransactionBean
import io.reactivex.Observable

interface IBlockDetailContract {
    abstract class IBlockDetailPresenter : BasePresenter<IBlockDetailModel, IBlockDetailView>() {
        abstract fun getBlockAllList(blockId: String, pageSize: Int, transactionId: Int)
        abstract fun getBlockList(blockId: Int, pageSize: Int)
        abstract fun getBlockDetail(blockId: String)
    }

    interface IBlockDetailModel : IBaseModel {
        fun getBlockAllList(blockId: String, pageSize: Int, transactionId: Int): Observable<BlockTransactionBean>
        fun getBlockList(blockId: Int, pageSize: Int): Observable<BlockListBean>
        fun getBlockDetail(blockId: String): Observable<BlockDetailBean>
    }

    interface IBlockDetailView : IBaseActivity {
        fun blockAllListSuccess(blockListBean: List<BlockTransactionBean.DataBean>)
        fun blockAllListFailed(msg: String)
        fun blockListSuccess(blockListBean: List<BlockListBean.DataBean>)
        fun blockListFailed(msg: String)
        fun blockDetailSuccess(blockListBean: BlockDetailBean.DataBean)
        fun blockDetailFailed(msg: String)
    }
}