package com.mvc.cryptovault_android.contract

import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.base.IBaseActivity
import com.mvc.cryptovault_android.base.IBaseModel
import com.mvc.cryptovault_android.bean.BlockOrderOnIdBean
import io.reactivex.Observable

interface AssetsDetailContract {
    abstract class AssetsDetailPresenter : BasePresenter<AssetsDetailModel, AssetsDetailView>() {
        abstract fun getAssetsDetailToId(id: Int)
    }

    interface AssetsDetailModel : IBaseModel {
        fun getAssetsDetailToId(id: Int): Observable<BlockOrderOnIdBean>
    }

    interface AssetsDetailView : IBaseActivity {
        fun detailSuccess(blockOrderOnIdBean: BlockOrderOnIdBean.DataBean)
        fun detailFailed(msg: String)
    }
}