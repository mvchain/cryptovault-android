package com.mvc.ttpay_android.presenter

import com.mvc.ttpay_android.base.BasePresenter
import com.mvc.ttpay_android.contract.IBlockTransferDetailContract
import com.mvc.ttpay_android.model.BlockTransferDetailModel

class BlockTransferDetailPresenter : IBlockTransferDetailContract.BlockTransferDetailPresenter() {
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

    override fun getModel(): IBlockTransferDetailContract.BlockTransferDetailModel {
        return BlockTransferDetailModel.instance
    }

    override fun onStart() {
    }
}