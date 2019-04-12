package com.mvc.ttpay_android.model

import com.mvc.ttpay_android.MyApplication
import com.mvc.ttpay_android.api.ApiStore
import com.mvc.ttpay_android.base.BaseModel
import com.mvc.ttpay_android.bean.BlockTransferDetailBean
import com.mvc.ttpay_android.contract.IBlockTransferDetailContract
import com.mvc.ttpay_android.utils.RetrofitUtils
import com.mvc.ttpay_android.utils.RxHelper
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