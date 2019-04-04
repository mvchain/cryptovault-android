package com.mvc.cryptovault_android.model

import com.mvc.cryptovault_android.MyApplication
import com.mvc.cryptovault_android.api.ApiStore
import com.mvc.cryptovault_android.base.BaseModel
import com.mvc.cryptovault_android.bean.PairTickersBean
import com.mvc.cryptovault_android.bean.TrandChildBean
import com.mvc.cryptovault_android.contract.IAreaContract
import com.mvc.cryptovault_android.utils.RetrofitUtils
import com.mvc.cryptovault_android.utils.RxHelper
import io.reactivex.Observable

class AreaModel : BaseModel(), IAreaContract.AreaModel {
    override fun getPairTickers(pairId: Int): Observable<PairTickersBean> {
       return RetrofitUtils.client(MyApplication.getBaseUrl(), ApiStore::class.java)
               .getPairTickers(MyApplication.getTOKEN(), pairId)
                .compose(RxHelper.rxSchedulerHelper())
                .map { pairBean -> pairBean }
    }

    override fun getVrt(pairType: Int): Observable<TrandChildBean> {
        return RetrofitUtils.client(MyApplication.getBaseUrl(), ApiStore::class.java)
                .getVrtAndBalance(MyApplication.getTOKEN(), pairType)
                .compose(RxHelper.rxSchedulerHelper()).map { areaBean -> areaBean }
    }

    override fun getAllVrtAndBalance(): Observable<TrandChildBean> {
        return RetrofitUtils.client(MyApplication.getBaseUrl(), ApiStore::class.java).getAllVrtAndBalance(MyApplication.getTOKEN())
                .compose(RxHelper.rxSchedulerHelper())
                .map { child -> child }
    }

    companion object {
        val instance: AreaModel
            get() = AreaModel()
    }
}