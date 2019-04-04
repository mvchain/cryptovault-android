package com.mvc.cryptovault_android.presenter

import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.contract.IRegisterInvitationConstrat
import com.mvc.cryptovault_android.model.RegisterInvitationModel
import java.net.SocketTimeoutException

class RegisterInvitationPresenter : IRegisterInvitationConstrat.RegisterInvitationPresenter() {


    companion object {
        fun newIntance(): BasePresenter<*, *> {
            return RegisterInvitationPresenter()
        }
    }

    override fun onStart() {
    }

    override fun sendValiCode(email: String) {
        rxUtils.register(mIModel.sendValiCode(email).subscribe({ httpBean ->
            if (httpBean.code === 200) {
                mIView.showValiCode("验证码发送成功")
            } else {
                mIView.showError(httpBean.message)
            }
        }, {
            if (it is SocketTimeoutException) {
                mIView.showError("连接超时")
            } else {
                mIView.showError(it.message!!)
            }
        }))
    }

    override fun sendInvitationRequest(invitation: String, email: String, code: String) {
        rxUtils.register(mIModel.sendInvitationRequest(invitation, email, code).subscribe({ httpBean ->
            if (httpBean.code === 200) {
                mIView.savaTempToken(httpBean.data)
                mIView.startActivity()
            } else {
                mIView.showError(httpBean.message)
            }
        }, {
            if (it is SocketTimeoutException) {
                mIView.showError("连接超时")
            } else {
                mIView.showError(it.message!!)
            }
        }))

    }

    override fun getModel(): IRegisterInvitationConstrat.InvitationModel {
        return RegisterInvitationModel.instance
    }
}