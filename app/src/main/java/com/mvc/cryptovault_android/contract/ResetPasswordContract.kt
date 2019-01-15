package com.mvc.cryptovault_android.contract

import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.base.IBaseActivity
import com.mvc.cryptovault_android.base.IBaseModel
import com.mvc.cryptovault_android.bean.HttpTokenBean
import com.mvc.cryptovault_android.bean.UpsetMnemonicsBean
import io.reactivex.Observable

interface ResetPasswordContract {
    abstract class ResetPasswordPresenter : BasePresenter<ResetModel, ResetView>() {
        abstract fun verificationEmail(email: String)
        abstract fun verificationPrivateKey(privateKey:String)
        abstract fun verificationMnenonics(mnemonics:String)

    }

    interface ResetModel : IBaseModel {
        fun verificationEmail(email: String) : Observable<HttpTokenBean>
        fun verificationPrivateKey(privateKey: String) : Observable<HttpTokenBean>
        fun verificationMnenonics(mnemonics: String) : Observable<UpsetMnemonicsBean>
    }

    interface ResetView : IBaseActivity {
        fun showError()
        fun getRequestBody()
    }
}