package com.mvc.cryptovault_android.presenter

import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.contract.SetPasswordContract
import com.mvc.cryptovault_android.model.SetLoginModel

class SetLoginPresenter : SetPasswordContract.SetPasswordPresenter() {

    companion object {
        fun newIntance(): BasePresenter<*, *> {
            return SetLoginPresenter()
        }
    }

    override fun setLoginPassword(password: String, newPassword: String) {
        rxUtils.register(mIModel.setLoginPassword(password, newPassword)
                .subscribe({
                    if (it.code === 200) {
                        mIView.showSuccess("登录密码修改成功")
                    } else {
                        mIView.showError(it.message)
                    }
                }, {
                    mIView.showError(it.message!!)
                }))
    }

    override fun setPayPassword(password: String, newPassword: String) {
        rxUtils.register(mIModel.setPayPassword(password, newPassword)
                .subscribe({
                    if (it.code === 200) {
                        mIView.showSuccess("支付密码修改成功")
                    } else {
                        mIView.showError(it.message)
                    }
                }, {
                    mIView.showError(it.message!!)
                }))
    }

    override fun getModel(): SetPasswordContract.SetPasswordModel {
        return SetLoginModel.instance
    }

    override fun onStart() {
    }
}