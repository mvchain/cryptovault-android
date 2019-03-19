package com.mvc.cryptovault_android.model

import com.mvc.cryptovault_android.MyApplication
import com.mvc.cryptovault_android.api.ApiStore
import com.mvc.cryptovault_android.base.BaseModel
import com.mvc.cryptovault_android.bean.BlockOrderOnIdBean
import com.mvc.cryptovault_android.contract.AssetsDetailContract
import com.mvc.cryptovault_android.utils.RetrofitUtils
import com.mvc.cryptovault_android.utils.RxHelper
import io.reactivex.Observable

class AssetsDetailModel:BaseModel(),AssetsDetailContract.AssetsDetailModel {
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