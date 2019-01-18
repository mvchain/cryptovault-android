package com.mvc.cryptovault_android.contract

import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.base.IBaseActivity
import com.mvc.cryptovault_android.base.IBaseModel
import com.mvc.cryptovault_android.bean.FinancialBean
import com.mvc.cryptovault_android.bean.FinancialListBean
import io.reactivex.Observable
import java.lang.Error

interface FinancialContract {
    abstract class FinancialPresenter : BasePresenter<FinancialModel, FinancialView>() {
        abstract fun getFinancialBalance()
        abstract fun getFinancialList()
    }

    interface FinancialModel : IBaseModel {
        fun getFinancialList(): Observable<FinancialListBean>
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