package com.mvc.cryptovault_android.presenter

import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.contract.IAreaContract
import com.mvc.cryptovault_android.model.AreaModel

class AreaPresenter :IAreaContract.AreaPresenter() {
    companion object {
        fun newInstance(): BasePresenter<*, *> {
            return AreaPresenter()
        }
    }

    override fun getAreaToId(id: Int) {
    }

    override fun getModel(): IAreaContract.AreaModel {
        return AreaModel.instance
    }

    override fun onStart() {
    }
}