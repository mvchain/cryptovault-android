package com.mvc.cryptovault_android.contract

import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.base.IBaseActivity
import com.mvc.cryptovault_android.base.IBaseModel
import com.mvc.cryptovault_android.bean.BlockListBean
import io.reactivex.Observable

interface IBlockDetailContract {
    abstract class IBlockDetailPresenter : BasePresenter<IBlockDetailModel, IBlockDetailView>() {
        abstract fun getBlockList(blockId:Int,pageSize:Int)
    }
    interface IBlockDetailModel : IBaseModel {
        fun getBlockList(blockId:Int,pageSize:Int) :Observable<BlockListBean>
    }
    interface IBlockDetailView : IBaseActivity {
        fun blockListSuccess(blockListBean: List<BlockListBean.DataBean>)
        fun blockListFailed(msg: String)
    }
}