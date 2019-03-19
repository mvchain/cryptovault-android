package com.mvc.cryptovault_android.presenter

import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.contract.OptionContract
import com.mvc.cryptovault_android.model.OptionModel

class OptionPresenter : OptionContract.OptionPresenter() {
    companion object {
        fun newIntance(): BasePresenter<*, *> {
            return OptionPresenter()
        }
    }

    override fun getOptionInfo(financialType: Int, id: Int, pageSize: Int) {
        rxUtils.register(mIModel.getOptionInfo(financialType, id, pageSize)
                .subscribe({ option ->
                    if (option.code === 200) {
                        mIView.showSuccess(option.data)
                    } else {
                        mIView.showNull()
                    }
                }, {
                    mIView.showError()
                }))
    }

    override fun getModel(): OptionContract.OptionModel {
        return OptionModel.instance
    }

    override fun onStart() {
    }
}