package com.mvc.ttpay_android.presenter

import com.mvc.ttpay_android.base.BasePresenter
import com.mvc.ttpay_android.contract.IPublicKeyContract
import com.mvc.ttpay_android.model.PublicKeyModel

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