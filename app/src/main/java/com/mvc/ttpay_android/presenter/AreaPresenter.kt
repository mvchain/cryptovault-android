package com.mvc.ttpay_android.presenter

import com.blankj.utilcode.util.SPUtils
import com.mvc.ttpay_android.base.BasePresenter
import com.mvc.ttpay_android.common.Constant.SP.TRAND_LIST
import com.mvc.ttpay_android.common.Constant.SP.TRAND_VRT_LIST
import com.mvc.ttpay_android.contract.IAreaContract
import com.mvc.ttpay_android.model.AreaModel
import com.mvc.ttpay_android.utils.JsonHelper

class AreaPresenter : IAreaContract.AreaPresenter() {
    override fun getPairTickers(pairId: Int) {
        rxUtils.register(mIModel.getPairTickers(pairId).subscribe(
                { pairBean ->
                    if (pairBean.code == 200) {
                        mIView.pairTickersSuccess(pairBean.data)
                    } else {
                        mIView.pairTickersFailed(pairBean.message)
                    }
                }, { error ->
            mIView.pairTickersFailed(error.message!!)
        }))
    }

    override fun getVrt(pairType: Int) {
        rxUtils.register(mIModel.getVrt(pairType).subscribe(
                { areaBean ->
                    if (areaBean.code == 200) {
                        SPUtils.getInstance().put(TRAND_VRT_LIST, JsonHelper.jsonToString(areaBean))
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