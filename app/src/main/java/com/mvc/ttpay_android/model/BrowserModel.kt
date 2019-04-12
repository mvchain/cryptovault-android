package com.mvc.ttpay_android.model

import com.mvc.ttpay_android.MyApplication
import com.mvc.ttpay_android.api.ApiStore
import com.mvc.ttpay_android.base.BaseModel
import com.mvc.ttpay_android.bean.*
import com.mvc.ttpay_android.contract.IBrowserContract
import com.mvc.ttpay_android.utils.RetrofitUtils
import com.mvc.ttpay_android.utils.RxHelper
import io.reactivex.Observable

class BrowserModel : BaseModel(), IBrowserContract.IBrowserModel {
    override fun getBlockAddressExist(address: String): Observable<HttpTokenBean> {
        return RetrofitUtils.client(MyApplication.getBaseBrowserUrl(), ApiStore::class.java)
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
    lateinit var blockNodeBean: ArrayList<BlockNodeBean>
    override fun getBlockBrowserData(): Observable<BlockBrowserDataBean> {
        blockNodeBean = ArrayList()
        return RetrofitUtils.client(MyApplication.getBaseBrowserUrl(), ApiStore::class.java).blockLast.compose(RxHelper.rxSchedulerHelper())
                .flatMap {
                    blockLastBean = it
                    RetrofitUtils.client(MyApplication.getBaseBrowserUrl(), ApiStore::class.java).getBlockList(0, 10).compose(RxHelper.rxSchedulerHelper())
                }.flatMap {
                    blockListBean = it
                    RetrofitUtils.client(MyApplication.getBaseBrowserUrl(), ApiStore::class.java).getBlockTransactionLast(10).compose(RxHelper.rxSchedulerHelper())
                }.flatMap {
                    var height = blockLastBean.data.blockId
                    blockNodeBean.add(BlockNodeBean("CN",height ))
                    blockNodeBean.add(BlockNodeBean("US", height))
                    blockNodeBean.add(BlockNodeBean("HK", height))
                    blockNodeBean.add(BlockNodeBean("UK", height))
                    blockNodeBean.add(BlockNodeBean("JP", height))
                    Observable.just(BlockBrowserDataBean(blockLastBean, blockListBean, it,blockNodeBean))
                }
    }

    companion object {
        val instance: BrowserModel
            get() = BrowserModel()
    }
}