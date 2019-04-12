package com.mvc.ttpay_android.contract

import com.mvc.ttpay_android.base.BasePresenter
import com.mvc.ttpay_android.base.IBaseFragment
import com.mvc.ttpay_android.base.IBaseModel
import com.mvc.ttpay_android.bean.PairTickersBean
import com.mvc.ttpay_android.bean.TrandChildBean
import io.reactivex.Observable

interface IAreaContract {
    abstract class AreaPresenter : BasePresenter<AreaModel, AreaView>() {
        abstract fun getAllVrtAndBalance()
        abstract fun getVrt(pairType: Int)
        abstract fun getPairTickers(pairId: Int)

    }

    interface AreaModel : IBaseModel {
        fun getAllVrtAndBalance(): Observable<TrandChildBean>
        fun getVrt(pairType: Int): Observable<TrandChildBean>
        fun getPairTickers(pairId: Int) :Observable<PairTickersBean>
    }

    interface AreaView : IBaseFragment {
        fun vrtSuccess(trandChildBean: ArrayList<TrandChildBean.DataBean>)
        fun vrtFailed(msg:String)

        fun pairTickersSuccess(pairTickersBean: PairTickersBean.DataBean)
        fun pairTickersFailed(msg:String)
    }
}