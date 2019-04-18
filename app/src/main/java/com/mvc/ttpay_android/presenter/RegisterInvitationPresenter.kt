package com.mvc.ttpay_android.presenter

import com.mvc.ttpay_android.MyApplication
import com.mvc.ttpay_android.R
import com.mvc.ttpay_android.base.BasePresenter
import com.mvc.ttpay_android.contract.IRegisterInvitationConstrat
import com.mvc.ttpay_android.model.RegisterInvitationModel
import java.net.SocketTimeoutException

class RegisterInvitationPresenter : IRegisterInvitationConstrat.RegisterInvitationPresenter() {


    companion object {
        fun newInstance(): BasePresenter<*, *> {
            return RegisterInvitationPresenter()
        }
    }

    override fun onStart() {
    }

    override fun sendValiCode(email: String) {
        rxUtils.register(mIModel.sendValiCode(email).subscribe({ httpBean ->
            if (httpBean.code === 200) {
                mIView.showValiCode( MyApplication.getAppContext().getString(R.string.send_successfully) )
            } else {
                mIView.showError(httpBean.message)
            }
        }, {
            if (it is SocketTimeoutException) {
                mIView.showError(MyApplication.getAppContext().getString(R.string.connection_timed_out))
            } else {
                mIView.showError(it.message!!)
            }
        }))
    }

    override fun sendInvitationRequest(email: String, code: String) {
        rxUtils.register(mIModel.sendInvitationRequest(email, code).subscribe({ httpBean ->
            if (httpBean.code == 200) {
                mIView.savaTempToken(httpBean.data)
                mIView.startActivity()
            } else {
                mIView.showError(httpBean.message)
            }
        }, {
            if (it is SocketTimeoutException) {
                mIView.showError(MyApplication.getAppContext().getString(R.string.connection_timed_out))
            } else {
                mIView.showError(it.message!!)
            }
        }))

    }

    override fun getModel(): IRegisterInvitationConstrat.InvitationModel {
        return RegisterInvitationModel.instance
    }
}