package com.mvc.ttpay_android.presenter

import com.mvc.ttpay_android.base.BasePresenter
import com.mvc.ttpay_android.contract.IFinancialDetailContract
import com.mvc.ttpay_android.model.FinancialDetailModel

class FinancialDetailPresenter : IFinancialDetailContract.FinancialDetailPresenter() {
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

    override fun getModel(): IFinancialDetailContract.FinancialDetailModel {
        return FinancialDetailModel.instance
    }
}