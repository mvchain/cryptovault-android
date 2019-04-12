package com.mvc.ttpay_android.contract

import com.mvc.ttpay_android.base.BasePresenter
import com.mvc.ttpay_android.base.IBaseActivity
import com.mvc.ttpay_android.base.IBaseModel
import com.mvc.ttpay_android.bean.LoginBean
import io.reactivex.Observable

interface IGoogleContract {
    abstract class GooglePresenter : BasePresenter<GoogleModel, GoogleView>() {
        abstract fun changeGoogleVerification(googleCode: String, googleSecret: String, password: String, status: Int)
    }

    interface GoogleModel : IBaseModel {
        fun changeGoogleVerification(googleCode: String, googleSecret: String, password: String, status: Int): Observable<LoginBean>
    }

    interface GoogleView : IBaseActivity {
        fun changeSuccess(loginBean: LoginBean)
        fun changeFailed(msg: String)
    }
}