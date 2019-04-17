package com.mvc.ttpay_android.presenter

import com.mvc.ttpay_android.MyApplication
import com.mvc.ttpay_android.R
import com.mvc.ttpay_android.base.BasePresenter
import com.mvc.ttpay_android.contract.ISetPasswordContract
import com.mvc.ttpay_android.model.SetLoginModel
import java.net.SocketTimeoutException

class SetLoginPresenter : ISetPasswordContract.SetPasswordPresenter() {

    companion object {
        fun newInstance(): BasePresenter<*, *> {
            return SetLoginPresenter()
        }
    }

    override fun setLoginPassword(password: String, newPassword: String) {
        rxUtils.register(mIModel.setLoginPassword(password, newPassword)
                .subscribe({
                    if (it.code == 200) {
                        mIView.showSuccess(MyApplication.getAppContext().getString(R.string.the_login_password_was_successfully_modified))
                    } else {
                        mIView.showError(it.message)
                    }
                }, {
                    if (it is SocketTimeoutException) {
                        mIView.showError(MyApplication.getAppContext().getString(R.string.connection_timed_out))
                    } else {
                        mIView.showError(it.message!!)
                    }
                }))
    }

    override fun setPayPassword(password: String, newPassword: String) {
        rxUtils.register(mIModel.setPayPassword(password, newPassword)
                .subscribe({
                    if (it.code == 200) {
                        mIView.showSuccess(MyApplication.getAppContext().getString(R.string.the_payment_password_was_successfully_modified))
                    } else {
                        mIView.showError(it.message)
                    }
                }, {
                    if (it is SocketTimeoutException) {
                        mIView.showError(MyApplication.getAppContext().getString(R.string.connection_timed_out))
                    } else {
                        mIView.showError(it.message!!)
                    }
                }))
    }

    override fun getModel(): ISetPasswordContract.SetPasswordModel {
        return SetLoginModel.instance
    }

    override fun onStart() {
    }
}