package com.mvc.cryptovault_android.contract

import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.base.IBaseFragment
import com.mvc.cryptovault_android.base.IBaseModel
import com.mvc.cryptovault_android.bean.TrandChildBean
import io.reactivex.Observable

interface IAreaContract {
    abstract class AreaPresenter : BasePresenter<AreaModel, AreaView>() {
        abstract fun getAllVrtAndBalance()
        abstract fun getVrt(pairType: Int)
    }

    interface AreaModel : IBaseModel {
        fun getAllVrtAndBalance(): Observable<TrandChildBean>
        fun getVrt(pairType: Int): Observable<TrandChildBean>
    }

    interface AreaView : IBaseFragment {
        fun vrtSuccess(trandChildBean: ArrayList<TrandChildBean.DataBean>)
        fun vrtFailed(msg:String)
    }
}