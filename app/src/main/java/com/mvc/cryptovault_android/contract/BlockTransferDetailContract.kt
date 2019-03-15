package com.mvc.cryptovault_android.contract

import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.base.IBaseActivity
import com.mvc.cryptovault_android.base.IBaseModel
import com.mvc.cryptovault_android.bean.BlockTransferDetailBean
import io.reactivex.Observable

interface BlockTransferDetailContract {
    abstract class BlockTransferDetailPresenter : BasePresenter<BlockTransferDetailModel, BlockTransferDetailView>() {
        abstract fun getTransferDetail(hash: String)
    }

    interface BlockTransferDetailModel : IBaseModel {
        fun getTransferDetail(hash: String): Observable<BlockTransferDetailBean>
    }

    interface BlockTransferDetailView : IBaseActivity {
        fun transferDetailSuccess(blockTransferDetailBean: BlockTransferDetailBean.DataBean)
        fun transferDetailFailed(msg: String)

    }
}