package com.mvc.cryptovault_android.model

import com.mvc.cryptovault_android.api.ApiStore
import com.mvc.cryptovault_android.base.BaseModel
import com.mvc.cryptovault_android.bean.*
import com.mvc.cryptovault_android.contract.IBrowserContract
import com.mvc.cryptovault_android.utils.RetrofitUtils
import com.mvc.cryptovault_android.utils.RxHelper
import io.reactivex.Observable

class BrowserModel : BaseModel(), IBrowserContract.IBrowserModel {
    override fun getBlockAddressExist(address: String): Observable<HttpTokenBean> {
        return RetrofitUtils.client(ApiStore::class.java)
                .getBlockAddressExist(address)
                .compose(RxHelper.rxSchedulerHelper())
                .map { keyBean -> keyBean }
    }

    override fun getBlockNode(): Observable<ArrayList<BlockNodeBean>> {
        var nodeArray = ArrayList<BlockNodeBean>()
        nodeArray.add(BlockNodeBean("CN", 1546795))
        nodeArray.add(BlockNodeBean("US", 1546795))
        nodeArray.add(BlockNodeBean("HK", 1546795))
        nodeArray.add(BlockNodeBean("UK", 1546795))
        nodeArray.add(BlockNodeBean("JP", 1546795))
        return Observable.just(nodeArray)
    }

    lateinit var blockLastBean: BlockLastBean
    lateinit var blockListBean: BlockListBean
    override fun getBlockBrowserData(): Observable<BlockBrowserDataBean> {
        return RetrofitUtils.client(ApiStore::class.java)
                .blockLast
                .compose(RxHelper.rxSchedulerHelper())
                .flatMap {
                    blockLastBean = it
                    RetrofitUtils.client(ApiStore::class.java).getBlockList(0, 10).compose(RxHelper.rxSchedulerHelper())
                }.flatMap {
                    blockListBean = it
                    RetrofitUtils.client(ApiStore::class.java).getBlockTransactionLast(10).compose(RxHelper.rxSchedulerHelper())
                }.flatMap {
                    Observable.just(BlockBrowserDataBean(blockLastBean, blockListBean, it))
                }
    }

    companion object {
        val instance: BrowserModel
            get() = BrowserModel()
    }
}