package com.mvc.ttpay_android.model

import com.mvc.ttpay_android.MyApplication
import com.mvc.ttpay_android.api.ApiStore
import com.mvc.ttpay_android.base.BaseModel
import com.mvc.ttpay_android.bean.BlockOrderOnIdBean
import com.mvc.ttpay_android.contract.IAssetsDetailContract
import com.mvc.ttpay_android.utils.RetrofitUtils
import com.mvc.ttpay_android.utils.RxHelper
import io.reactivex.Observable

class AssetsDetailModel:BaseModel(),IAssetsDetailContract.AssetsDetailModel {
    companion object {
        val instance: AssetsDetailModel
              get() = AssetsDetailModel()
    }

    override fun getAssetsDetailToId(id: Int): Observable<BlockOrderOnIdBean> {
        return RetrofitUtils.client(MyApplication.getBaseBrowserUrl(),ApiStore::class.java).getBlockDetailOnId(id)
                .compose(RxHelper.rxSchedulerHelper())
                .map { idBean->idBean }
    }
}