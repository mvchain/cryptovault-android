package com.mvc.cryptovault_android.presenter

import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.contract.BlockTransferDetailContract
import com.mvc.cryptovault_android.model.BlockTransferDetailModel

class BlockTransferDetailPresenter : BlockTransferDetailContract.BlockTransferDetailPresenter() {
    override fun getTransferDetail(hash: String) {
        rxUtils.register(mIModel.getTransferDetail(hash)
                .subscribe({
                    tranfer->
                    if (tranfer.code ==200) {
                        mIView.transferDetailSuccess(tranfer.data)
                    }else{
                        mIView.transferDetailFailed(tranfer.message)
                    }

                },{
                    mIView.transferDetailFailed(it.message!!)
                }))
    }

    companion object {
        fun newInstance(): BasePresenter<*, *> {
                    return BlockTransferDetailPresenter()
        }
    }

    override fun getModel(): BlockTransferDetailContract.BlockTransferDetailModel {
        return BlockTransferDetailModel.instance
    }

    override fun onStart() {
    }
}