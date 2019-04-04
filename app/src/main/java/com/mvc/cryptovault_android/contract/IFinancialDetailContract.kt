package com.mvc.cryptovault_android.contract

import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.base.IBaseActivity
import com.mvc.cryptovault_android.base.IBaseModel
import com.mvc.cryptovault_android.bean.FinancialDetailBean
import com.mvc.cryptovault_android.bean.HttpTokenBean
import io.reactivex.Observable

interface IFinancialDetailContract {
    abstract class FinancialDetailPresenter : BasePresenter<FinancialDetailModel, FinancialDetailfoView>() {
        abstract fun getFinancialDetail(id: Int)

    }

    interface FinancialDetailModel : IBaseModel {
        fun getFinancialDetail(id: Int): Observable<FinancialDetailBean>
    }

    interface FinancialDetailfoView : IBaseActivity {
        fun showError(error: String)
        fun showSuccess(bean: FinancialDetailBean.DataBean)
    }
}