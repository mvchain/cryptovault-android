package com.mvc.cryptovault_android.presenter

import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.contract.FinancialContract
import com.mvc.cryptovault_android.contract.FinancialDetailContract
import com.mvc.cryptovault_android.model.FinancialDetailModel
import com.mvc.cryptovault_android.model.FinancialModel

class FinancialDetailPresenter : FinancialDetailContract.FinancialDetailPresenter() {
    override fun getFinancialDetail(id: Int) {
        rxUtils.register(mIModel.getFinancialDetail(id)
                .subscribe({ detail ->
                    if (detail.code === 200) {
                        mIView.showSuccess(detail.data)
                    } else {
                        mIView.showError(detail.message)
                    }
                }, { error ->
                    mIView.showError(error.message!!)
                }))

    }

    companion object {
        fun newIntance(): BasePresenter<*, *> {
            return FinancialDetailPresenter()
        }
    }


    override fun onStart() {
    }

    override fun getModel(): FinancialDetailContract.FinancialDetailModel {
        return FinancialDetailModel.instance
    }
}