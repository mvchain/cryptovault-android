package com.mvc.cryptovault_android.presenter

import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.contract.IOptionDetailContract
import com.mvc.cryptovault_android.model.OptionDetailModel
import java.net.SocketTimeoutException

class OptionDetailPresenter : IOptionDetailContract.OptionDetailPresenter() {


    companion object {
        fun getInstance(): BasePresenter<*, *> {
            return OptionDetailPresenter()
        }
    }

    override fun getDailyIncome(id: Int, qId: Int, pageSize: Int) {
        rxUtils.register(mIModel.getDailyIncome(id, qId, pageSize)
                .subscribe({ dailt ->
                    if (dailt.code === 200) {
                        mIView.showDailyIncome(dailt.data)
                    } else {
                        mIView.showDailyIncomeError()
                    }
                }, {
                    mIView.showDailyIncomeError()
                }))
    }

    override fun getOptionDetail(id: Int) {
        rxUtils.register(mIModel.getOptionDetail(id)
                .subscribe({ detail ->
                    if (detail.code === 200) {
                        mIView.showDetailSuccess(detail.data)
                    } else {
                        mIView.showDetailError()
                    }
                }, {
                    mIView.showDetailError()
                }))
    }

    override fun extractOptionDetail(id: Int) {
        rxUtils.register(mIModel.extractOptionDetail(id)
                .subscribe({ updataBean ->
                    if (updataBean.code == 200) {
                        mIView.showExtractSuccess()
                    } else {
                        mIView.showExtractError(updataBean.message)
                    }
                }, {
                    if (it is SocketTimeoutException) {
                        mIView.showExtractError("连接超时")
                    } else {
                        mIView.showExtractError(it.message!!)
                    }
                }))
    }

    override fun getModel(): IOptionDetailContract.OptionDetailModel {
        return OptionDetailModel.instance
    }

    override fun onStart() {
    }
}