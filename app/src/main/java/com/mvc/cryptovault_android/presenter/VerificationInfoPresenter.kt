package com.mvc.cryptovault_android.presenter

import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.contract.VerificationInfoContract
import com.mvc.cryptovault_android.model.VerificationInfoModel

class VerificationInfoPresenter : VerificationInfoContract.VerificationInfoPresenter() {
    override fun verification(email: String, type: Int, value: String) {
        rxUtils.register(mIModel.verification(email, type, value)
                .subscribe({ httpToken ->
                    if (httpToken.code === 200) {
                        mIView.showSuccess(httpToken.data)
                    } else {
                        mIView.showError(httpToken.message)
                    }
                }, { error ->
                    mIView.showError(error.message!!)
                }))
    }

    companion object {
        fun newIntance(): BasePresenter<*, *> {
            return VerificationInfoPresenter()
        }
    }

    override fun getModel(): VerificationInfoContract.ResetModel {
        return VerificationInfoModel.instance
    }

    override fun onStart() {

    }

}
