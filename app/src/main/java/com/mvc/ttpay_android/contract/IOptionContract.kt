package com.mvc.ttpay_android.contract

import com.mvc.ttpay_android.base.BasePresenter
import com.mvc.ttpay_android.base.IBaseFragment
import com.mvc.ttpay_android.base.IBaseModel
import com.mvc.ttpay_android.bean.OptionBean
import io.reactivex.Observable

interface IOptionContract {
    abstract class OptionPresenter : BasePresenter<OptionModel, OptionView>() {
        abstract fun getOptionInfo(financialType: Int, id: Int, pageSize: Int)
    }

    interface OptionModel : IBaseModel {
        fun getOptionInfo(financialType: Int, id: Int, pageSize: Int): Observable<OptionBean>
    }

    interface OptionView : IBaseFragment {
        fun showSuccess(bean: List<OptionBean.DataBean>)
        fun showNull()
        fun showError()
    }
}