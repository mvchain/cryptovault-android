package com.mvc.cryptovault_android.presenter

import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.contract.IPublicKeyContract
import com.mvc.cryptovault_android.model.PublicKeyModel

class PublicKeyPresenter : IPublicKeyContract.IPublicKeyPresenter() {
    companion object {
        fun newInstance(): BasePresenter<*, *> {
                    return PublicKeyPresenter()
        }
    }

    override fun getModel(): IPublicKeyContract.IPublicKeyModel {
        return PublicKeyModel.instance
    }

    override fun onStart() {
    }
}