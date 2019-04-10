package com.mvc.cryptovault_android.presenter

import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.contract.IGoogleContract
import com.mvc.cryptovault_android.model.GoogleModel

class GooglePresenter : IGoogleContract.GooglePresenter() {
    companion object {
        fun newInstance(): BasePresenter<*, *> {
            return GooglePresenter()
        }
    }

    override fun changeGoogleVerification(googleCode: String, googleSecret: String, password: String, status: Int) {
        rxUtils.register(mIModel.changeGoogleVerification(googleCode,googleSecret, password, status)
                .subscribe({ login ->
                    if (login.code == 200) {
                        mIView.changeSuccess(login)
                    }else{
                        mIView.changeFailed(login.message)
                    }
                }, { error ->
                    mIView.changeFailed(error.message!!)
                }))
    }

    override fun getModel(): IGoogleContract.GoogleModel {
        return GoogleModel.instance
    }

    override fun onStart() {

    }
}