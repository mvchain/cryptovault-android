package com.mvc.cryptovault_android.presenter

import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.contract.ResetPasswordContract
import com.mvc.cryptovault_android.model.ResetModel
import com.mvc.cryptovault_android.utils.RxHelper

class ResetPresenter : ResetPasswordContract.ResetPasswordPresenter() {
    override fun verification(email: String, type: Int, value: String) {
        rxUtils.register(mIModel.verification(email, type, value)
                .compose(RxHelper.rxSchedulerHelper())
                .subscribe({ httpToken ->
                    if (httpToken.code === 200) {
                        mIView.getRequestBody(httpToken.data)
                    } else {
                        mIView.showError(httpToken.message)
                    }
                }, { error ->
                    mIView.showError(error.message!!)
                }))
    }

    companion object {
        fun newIntance(): BasePresenter<*, *> {
            return ResetPresenter()
        }
    }

    override fun getModel(): ResetPasswordContract.ResetModel {
        return ResetModel.instance
    }

    override fun onStart() {

    }

}
