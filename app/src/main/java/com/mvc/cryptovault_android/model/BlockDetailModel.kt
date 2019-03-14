package com.mvc.cryptovault_android.model

import com.mvc.cryptovault_android.api.ApiStore
import com.mvc.cryptovault_android.base.BaseModel
import com.mvc.cryptovault_android.bean.BlockListBean
import com.mvc.cryptovault_android.contract.IBlockDetailContract
import com.mvc.cryptovault_android.utils.RetrofitUtils
import com.mvc.cryptovault_android.utils.RxHelper
import io.reactivex.Observable

class BlockDetailModel : BaseModel(), IBlockDetailContract.IBlockDetailModel {
    override fun getBlockList(blockId: Int, pageSize: Int): Observable<BlockListBean> {
        return RetrofitUtils.client(ApiStore::class.java).getBlockList(blockId, pageSize)
                .compose(RxHelper.rxSchedulerHelper())
                .map { blist -> blist }
    }

    companion object {
        val instance: BlockDetailModel
            get() = BlockDetailModel()
    }
}