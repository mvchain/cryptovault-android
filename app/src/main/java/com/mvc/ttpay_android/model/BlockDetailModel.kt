package com.mvc.ttpay_android.model

import com.mvc.ttpay_android.MyApplication
import com.mvc.ttpay_android.api.ApiStore
import com.mvc.ttpay_android.base.BaseModel
import com.mvc.ttpay_android.bean.BlockDetailBean
import com.mvc.ttpay_android.bean.BlockListBean
import com.mvc.ttpay_android.bean.BlockTransactionBean
import com.mvc.ttpay_android.contract.IBlockDetailContract
import com.mvc.ttpay_android.utils.RetrofitUtils
import com.mvc.ttpay_android.utils.RxHelper
import io.reactivex.Observable

class BlockDetailModel : BaseModel(), IBlockDetailContract.IBlockDetailModel {
    override fun getBlockDetail(blockId: String): Observable<BlockDetailBean> {
        return RetrofitUtils.client(MyApplication.getBaseBrowserUrl(),ApiStore::class.java).getBlockDetail(blockId)
                .compose(RxHelper.rxSchedulerHelper())
                .map { blockDetail -> blockDetail }
    }

    override fun getBlockList(blockId: Int, pageSize: Int): Observable<BlockListBean> {
        return RetrofitUtils.client(MyApplication.getBaseBrowserUrl(),ApiStore::class.java).getBlockList(blockId, pageSize)
                .compose(RxHelper.rxSchedulerHelper())
                .map { blist -> blist }
    }

    override fun getBlockAllList(blockId: String, pageSize: Int, transactionId: Int): Observable<BlockTransactionBean> {
        return RetrofitUtils.client(MyApplication.getBaseBrowserUrl(),ApiStore::class.java).getBlockAllList(blockId, pageSize,transactionId)
                .compose(RxHelper.rxSchedulerHelper())
                .map { blist -> blist }
    }

    companion object {
        val instance: BlockDetailModel
            get() = BlockDetailModel()
    }
}