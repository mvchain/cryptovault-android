package com.mvc.cryptovault_android.contract

import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.base.IBaseActivity
import com.mvc.cryptovault_android.base.IBaseModel
import com.mvc.cryptovault_android.bean.FinancialBean
import com.mvc.cryptovault_android.bean.FinancialListBean
import io.reactivex.Observable
import java.lang.Error

interface IFinancialContract {
    abstract class FinancialPresenter : BasePresenter<FinancialModel, FinancialView>() {
        abstract fun getFinancialBalance()
        abstract fun getFinancialList(id:Int,pageSize:Int)
    }

    interface FinancialModel : IBaseModel {
        fun getFinancialList(id:Int,pageSize:Int): Observable<FinancialListBean>
        fun getFinancialBalance(): Observable<FinancialBean>

    }

    interface FinancialView : IBaseActivity {
        fun showMeFinanciaError()
        fun showMeFinanciaSuccess(financialBean: FinancialBean.DataBean)

        fun showFinanciaListError()
        fun showFinanciaListSuccess(financialListBean: List<FinancialListBean.DataBean>)

        fun showServerError()
    }
}