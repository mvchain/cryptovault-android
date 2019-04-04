package com.mvc.cryptovault_android.contract

import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.base.IBaseActivity
import com.mvc.cryptovault_android.base.IBaseModel
import com.mvc.cryptovault_android.bean.HttpTokenBean
import io.reactivex.Observable

interface IVerificationInfoContract {
    abstract class VerificationInfoPresenter : BasePresenter<VerificationInfoModel, VerificationInfoView>() {
        abstract fun verification(email: String, type:Int, value:String)

    }

    interface VerificationInfoModel : IBaseModel {
        fun verification(email: String, type:Int, value:String) : Observable<HttpTokenBean>
    }

    interface VerificationInfoView : IBaseActivity {
        fun showError(error:String)
        fun showSuccess(token:String)
    }
}