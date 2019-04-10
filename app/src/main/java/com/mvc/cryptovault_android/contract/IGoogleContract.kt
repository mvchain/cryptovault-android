package com.mvc.cryptovault_android.contract

import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.base.IBaseActivity
import com.mvc.cryptovault_android.base.IBaseFragment
import com.mvc.cryptovault_android.base.IBaseModel
import com.mvc.cryptovault_android.bean.LoginBean
import com.mvc.cryptovault_android.bean.PairTickersBean
import com.mvc.cryptovault_android.bean.TrandChildBean
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