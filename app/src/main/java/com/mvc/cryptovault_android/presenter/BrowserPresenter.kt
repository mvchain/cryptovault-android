package com.mvc.cryptovault_android.presenter

import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.contract.IBrowserContract
import com.mvc.cryptovault_android.model.BrowserModel

class BrowserPresenter : IBrowserContract.IBrowserPresenter() {
    companion object {
        fun newInstance(): BasePresenter<*, *> {
            return BrowserPresenter()
        }
    }

    override fun getModel(): IBrowserContract.IBrowserModel {
        return BrowserModel.instance
    }

    override fun onStart() {
    }
}