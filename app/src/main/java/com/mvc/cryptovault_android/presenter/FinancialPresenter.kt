package com.mvc.cryptovault_android.presenter

import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.contract.FinancialContract
import com.mvc.cryptovault_android.model.FinancialModel

class FinancialPresenter : FinancialContract.FinancialPresenter() {
    override fun getFinancialBalance() {
        rxUtils.register(mIModel.getFinancialBalance()
                .subscribe({ finaBean ->
                    if (finaBean.code === 200) {
                        mIView.showMeFinanciaSuccess(finaBean.data)
                    } else {
                        mIView.showMeFinanciaError()
                    }
                }, {
                    mIView.showServerError()
                }))
    }

    override fun getFinancialList() {
        rxUtils.register(mIModel.getFinancialList()
                .subscribe({ finaBean ->
                    if (finaBean.code === 200) {
                        mIView.showFinanciaListSuccess(finaBean.data)
                    } else {
                        mIView.showFinanciaListError()
                    }
                }, {
                    mIView.showServerError()
                }))
    }

    companion object {
        fun newIntance(): BasePresenter<*, *> {
            return FinancialPresenter()
        }
    }


    override fun onStart() {
    }

    override fun getModel(): FinancialContract.FinancialModel {
        return FinancialModel.instance
    }
}