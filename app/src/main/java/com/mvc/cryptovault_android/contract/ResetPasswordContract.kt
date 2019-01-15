package com.mvc.cryptovault_android.contract

import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.base.IBaseActivity
import com.mvc.cryptovault_android.base.IBaseModel
import com.mvc.cryptovault_android.bean.HttpTokenBean
import com.mvc.cryptovault_android.bean.UpsetMnemonicsBean
import io.reactivex.Observable

interface ResetPasswordContract {
    abstract class ResetPasswordPresenter : BasePresenter<ResetModel, ResetView>() {
        abstract fun verification(email: String, type:Int, value:String)

    }

    interface ResetModel : IBaseModel {
        fun verification(email: String, type:Int, value:String) : Observable<HttpTokenBean>
    }

    interface ResetView : IBaseActivity {
        fun showError()
        fun getRequestBody()
    }
}