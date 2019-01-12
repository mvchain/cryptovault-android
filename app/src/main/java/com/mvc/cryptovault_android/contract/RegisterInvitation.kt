package com.mvc.cryptovault_android.contract

import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.base.IBaseActivity
import com.mvc.cryptovault_android.base.IBaseModel
import com.mvc.cryptovault_android.bean.HttpTokenBean
import io.reactivex.Observable

interface RegisterInvitation {
    abstract class RegisterInvitationPresenter : BasePresenter<InvitationModel, InvitationView>() {
        abstract fun sendInvitationRequest(invitation: String, email: String, code: String)
        abstract fun sendValiCode(email: String)
    }

    interface InvitationModel : IBaseModel {
        fun sendInvitationRequest(invitation: String, email: String, code: String): Observable<HttpTokenBean>
        fun sendValiCode(email: String): Observable<HttpTokenBean>
    }

    interface InvitationView : IBaseActivity {
        fun showValiCode(email: String)
        fun savaTempToken(token: String)
        fun showError(error: String)
    }

}