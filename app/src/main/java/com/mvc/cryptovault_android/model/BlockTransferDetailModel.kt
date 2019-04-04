package com.mvc.cryptovault_android.model

import com.mvc.cryptovault_android.MyApplication
import com.mvc.cryptovault_android.api.ApiStore
import com.mvc.cryptovault_android.base.BaseModel
import com.mvc.cryptovault_android.bean.BlockTransferDetailBean
import com.mvc.cryptovault_android.contract.IBlockTransferDetailContract
import com.mvc.cryptovault_android.utils.RetrofitUtils
import com.mvc.cryptovault_android.utils.RxHelper
import io.reactivex.Observable

class BlockTransferDetailModel : BaseModel(), IBlockTransferDetailContract.BlockTransferDetailModel {
    override fun getTransferDetail(hash: String): Observable<BlockTransferDetailBean> {
        return RetrofitUtils.client(MyApplication.getBaseBrowserUrl(),ApiStore::class.java)
                .getTransferDetail(hash)
                .compose(RxHelper.rxSchedulerHelper())
                .map { transition -> transition }
    }

    companion object {
        val instance: BlockTransferDetailModel
            get() = BlockTransferDetailModel()
    }

}