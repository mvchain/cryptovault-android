package com.mvc.cryptovault_android.contract

import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.base.IBaseActivity
import com.mvc.cryptovault_android.base.IBaseModel
import com.mvc.cryptovault_android.bean.UpdateBean
import io.reactivex.Observable

interface SetPasswordContract {
    abstract class SetPasswordPresenter : BasePresenter<SetPasswordModel, SetPasswordView>() {
        abstract fun setLoginPassword(password: String, newPassword: String)
        abstract fun setPayPassword(password: String, newPassword: String)
    }

    interface SetPasswordModel : IBaseModel {
        fun setLoginPassword(password: String, newPassword: String): Observable<UpdateBean>
        fun setPayPassword(password: String, newPassword: String): Observable<UpdateBean>
    }

    interface SetPasswordView : IBaseActivity {
        fun showError(error: String)
        fun showSuccess(msg: String)
    }
}