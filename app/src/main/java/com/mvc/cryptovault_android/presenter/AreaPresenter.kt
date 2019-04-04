package com.mvc.cryptovault_android.presenter

import com.blankj.utilcode.util.SPUtils
import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.common.Constant.SP.TRAND_LIST
import com.mvc.cryptovault_android.contract.IAreaContract
import com.mvc.cryptovault_android.model.AreaModel
import com.mvc.cryptovault_android.utils.JsonHelper

class AreaPresenter : IAreaContract.AreaPresenter() {
    override fun getVrt(pairType: Int) {
        rxUtils.register(mIModel.getVrt(pairType).subscribe(
                { areaBean ->
                    if (areaBean.code == 200) {
                        mIView.vrtSuccess(areaBean.data)
                    } else {
                        mIView.vrtFailed(areaBean.message)
                    }
                }, { error ->
            mIView.vrtFailed(error.message!!)
        }))
    }

    override fun getAllVrtAndBalance() {
        rxUtils.register(mIModel.getAllVrtAndBalance().subscribe(
                { childBean ->
                    SPUtils.getInstance().put(TRAND_LIST, JsonHelper.jsonToString(childBean))
                }, { error ->
        }))
    }

    companion object {
        fun newInstance(): BasePresenter<*, *> {
            return AreaPresenter()
        }
    }

    override fun getModel(): IAreaContract.AreaModel {
        return AreaModel.instance
    }

    override fun onStart() {
    }
}