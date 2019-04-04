package com.mvc.cryptovault_android.contract

import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.base.IBaseActivity
import com.mvc.cryptovault_android.base.IBaseModel
import com.mvc.cryptovault_android.bean.OptionDailyIncomeBean
import com.mvc.cryptovault_android.bean.OptionDetailBean
import com.mvc.cryptovault_android.bean.UpdateBean
import io.reactivex.Observable
import java.util.ArrayList

interface IOptionDetailContract {
    abstract class OptionDetailPresenter : BasePresenter<OptionDetailModel, OptionDetailView>() {
        abstract fun getOptionDetail(id: Int)
        abstract fun getDailyIncome(id: Int, qId: Int, pageSize: Int)
        abstract fun extractOptionDetail(id: Int)
    }

    interface OptionDetailModel : IBaseModel {
        fun getOptionDetail(id: Int): Observable<OptionDetailBean>
        fun getDailyIncome(id: Int, qId: Int, pageSize: Int): Observable<OptionDailyIncomeBean>
        fun extractOptionDetail(id: Int): Observable<UpdateBean>
    }

    interface OptionDetailView : IBaseActivity {
        fun showDetailSuccess(detail: OptionDetailBean.DataBean)
        fun showDailyIncome(incom: ArrayList<OptionDailyIncomeBean.DataBean>)
        fun showExtractSuccess()
        fun showExtractError(error:String)
        fun showDetailError()
        fun showDailyIncomeError()
    }
}