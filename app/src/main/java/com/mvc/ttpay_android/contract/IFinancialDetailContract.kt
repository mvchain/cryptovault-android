package com.mvc.ttpay_android.contract

import com.mvc.ttpay_android.base.BasePresenter
import com.mvc.ttpay_android.base.IBaseActivity
import com.mvc.ttpay_android.base.IBaseModel
import com.mvc.ttpay_android.bean.FinancialDetailBean
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