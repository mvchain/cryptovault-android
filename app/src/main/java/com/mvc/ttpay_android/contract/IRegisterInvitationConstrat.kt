package com.mvc.ttpay_android.contract

import com.mvc.ttpay_android.base.BasePresenter
import com.mvc.ttpay_android.base.IBaseActivity
import com.mvc.ttpay_android.base.IBaseModel
import com.mvc.ttpay_android.bean.HttpTokenBean
import io.reactivex.Observable

interface IRegisterInvitationConstrat {
    abstract class RegisterInvitationPresenter : BasePresenter<InvitationModel, InvitationView>() {
        abstract fun sendInvitationRequest(email: String, code: String)
        abstract fun sendValiCode(email: String)
    }

    interface InvitationModel : IBaseModel {
        fun sendInvitationRequest(email: String, code: String): Observable<HttpTokenBean>
        fun sendValiCode(email: String): Observable<HttpTokenBean>
    }

    interface InvitationView : IBaseActivity {
        fun showValiCode(email: String)
        fun savaTempToken(token: String)
        fun showError(error: String)
    }

}