package com.mvc.cryptovault_android.presenter

import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.contract.RegisterInvitationConstrat
import com.mvc.cryptovault_android.model.RegisterInvitationModel

class RegisterInvitationPresenter : RegisterInvitationConstrat.RegisterInvitationPresenter() {


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
            mIView.showError("服务器繁忙，请重试")
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
            mIView.showError("服务器繁忙，请重试")
        }))

    }

    override fun getModel(): RegisterInvitationConstrat.InvitationModel {
        return RegisterInvitationModel.instance
    }
}