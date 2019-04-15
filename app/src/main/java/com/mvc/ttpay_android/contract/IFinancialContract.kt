package com.mvc.ttpay_android.contract

import com.mvc.ttpay_android.base.BasePresenter
import com.mvc.ttpay_android.base.IBaseActivity
import com.mvc.ttpay_android.base.IBaseModel
import com.mvc.ttpay_android.bean.FinancialBean
import com.mvc.ttpay_android.bean.FinancialListBean
import io.reactivex.Observable

interface IFinancialContract {
    abstract class FinancialPresenter : BasePresenter<FinancialModel, FinancialView>() {
        abstract fun getFinancialBalance()
        abstract fun getFinancialList(id:Int,pageSize:Int)
        abstract fun getLoadFinancialList(id:Int,pageSize:Int)
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


        fun showLoadFinanciaListError()
        fun showLoadFinanciaListSuccess(financialListBean: List<FinancialListBean.DataBean>)

        fun showServerError()
    }
}