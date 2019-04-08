package com.mvc.cryptovault_android.presenter

import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.contract.IFinancialContract
import com.mvc.cryptovault_android.model.FinancialModel

class FinancialPresenter : IFinancialContract.FinancialPresenter() {
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

    override fun getFinancialList(id:Int,pageSize:Int) {
        rxUtils.register(mIModel.getFinancialList(id, pageSize)
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
        fun newInstance(): BasePresenter<*, *> {
            return FinancialPresenter()
        }
    }


    override fun onStart() {
    }

    override fun getModel(): IFinancialContract.FinancialModel {
        return FinancialModel.instance
    }
}