package com.mvc.cryptovault_android.presenter

import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.contract.ResetPasswordContract

class ResetPresenter : ResetPasswordContract.ResetPasswordPresenter(){

    companion object {
        fun newIntance(): BasePresenter<*, *> {
            return ResetPresenter()
        }
    }

    override fun verificationEmail(email: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun verificationPrivateKey(privateKey: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun verificationMnenonics(privateKey: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getModel(): ResetPasswordContract.ResetModel {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStart() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
