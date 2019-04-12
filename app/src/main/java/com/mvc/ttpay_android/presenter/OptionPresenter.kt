package com.mvc.ttpay_android.presenter

import com.mvc.ttpay_android.base.BasePresenter
import com.mvc.ttpay_android.contract.IOptionContract
import com.mvc.ttpay_android.model.OptionModel

class OptionPresenter : IOptionContract.OptionPresenter() {
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

    override fun getModel(): IOptionContract.OptionModel {
        return OptionModel.instance
    }

    override fun onStart() {
    }
}