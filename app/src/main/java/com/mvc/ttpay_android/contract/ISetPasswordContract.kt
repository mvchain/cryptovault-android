package com.mvc.ttpay_android.contract

import com.mvc.ttpay_android.base.BasePresenter
import com.mvc.ttpay_android.base.IBaseActivity
import com.mvc.ttpay_android.base.IBaseModel
import com.mvc.ttpay_android.bean.UpdateBean
import io.reactivex.Observable

interface ISetPasswordContract {
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