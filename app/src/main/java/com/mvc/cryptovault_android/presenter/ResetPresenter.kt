package com.mvc.cryptovault_android.presenter

import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.contract.ResetPasswordContract
import com.mvc.cryptovault_android.model.ResetModel

class ResetPresenter : ResetPasswordContract.ResetPasswordPresenter(){
    override fun verification(email: String, type: Int, value: String) {

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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
