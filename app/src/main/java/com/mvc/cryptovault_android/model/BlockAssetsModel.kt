package com.mvc.cryptovault_android.model

import com.mvc.cryptovault_android.MyApplication
import com.mvc.cryptovault_android.api.ApiStore
import com.mvc.cryptovault_android.base.BaseModel
import com.mvc.cryptovault_android.bean.BlockBalanceBean
import com.mvc.cryptovault_android.bean.BlockOrderBean
import com.mvc.cryptovault_android.contract.BlockAssetsContract
import com.mvc.cryptovault_android.utils.RetrofitUtils
import com.mvc.cryptovault_android.utils.RxHelper
import io.reactivex.Observable

class BlockAssetsModel : BaseModel(), BlockAssetsContract.BlockAssetsModel {
    override fun getBlockBalance(publicKey: String): Observable<BlockBalanceBean> {
        return RetrofitUtils.client(MyApplication.getBaseBrowserUrl(),ApiStore::class.java).getBlockBalance(publicKey)
                .compose(RxHelper.rxSchedulerHelper())
                .map { blockBalanceBean -> blockBalanceBean }
    }

    override fun getBlockOrder(id: Int, pageSize: Int, publicKey: String): Observable<BlockOrderBean> {
        return RetrofitUtils.client(MyApplication.getBaseBrowserUrl(),ApiStore::class.java).getBlockOrder(id,pageSize,publicKey)
                .compose(RxHelper.rxSchedulerHelper())
                .map { orderBean -> orderBean }
    }

    companion object {
        val instance: BlockAssetsModel
            get() = BlockAssetsModel()
    }
}