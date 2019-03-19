package com.mvc.cryptovault_android.presenter

import android.annotation.SuppressLint
import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.contract.IBrowserContract
import com.mvc.cryptovault_android.model.BrowserModel

class BrowserPresenter : IBrowserContract.IBrowserPresenter() {
    override fun getBlockAddressExist(address: String) {
        rxUtils.register(mIModel.getBlockAddressExist(address)
                .subscribe { addressBean ->
                    if (addressBean.code == 200 && addressBean.data != "") {
                        mIView.addressSuccess(addressBean.data)
                    } else {
                        mIView.addressFailed(addressBean.message)
                    }
                })
    }

    @SuppressLint("CheckResult")
    override fun getBlockNode() {
        mIModel.getBlockNode()
                .subscribe {
                    mIView.blockNodeSuccess(it)
                }
    }

    override fun getBlockBrowserData() {
        rxUtils.register(mIModel.getBlockBrowserData()
                .subscribe {
                    mIView.blockSuccess(it)
                })
    }

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