package com.mvc.cryptovault_android.contract

import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.base.IBaseActivity
import com.mvc.cryptovault_android.base.IBaseModel
import com.mvc.cryptovault_android.bean.BlockBalanceBean
import com.mvc.cryptovault_android.bean.BlockOrderBean
import com.mvc.cryptovault_android.bean.UpdateBean
import com.mvc.cryptovault_android.bean.VPBalanceBean

import io.reactivex.Observable

interface BlockAssetsContract {
    abstract class BlockAssetsPresenter : BasePresenter<BlockAssetsModel, BlockAssetsView>() {
        abstract fun getBlockBalance(publicKey: String)
        abstract fun getBlockOrder(id: Int, pageSize: Int, publicKey: String)
    }

    interface BlockAssetsModel : IBaseModel {
        fun getBlockBalance(publicKey: String): Observable<BlockBalanceBean>
        fun getBlockOrder(id: Int, pageSize: Int, publicKey: String): Observable<BlockOrderBean>
    }

    interface BlockAssetsView : IBaseActivity {
        fun balanceSuccess(blockBalanceBean: ArrayList<BlockBalanceBean.DataBean>)
        fun orderSuccess(orderBalanceBean: ArrayList<BlockOrderBean.DataBean>)
        fun balanceFailed(msg: String)
        fun orderFailed(msg: String)

    }
}
