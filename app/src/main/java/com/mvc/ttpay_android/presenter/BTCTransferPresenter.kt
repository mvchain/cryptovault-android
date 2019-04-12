package com.mvc.ttpay_android.presenter

import com.blankj.utilcode.util.LogUtils
import com.mvc.ttpay_android.base.BasePresenter
import com.mvc.ttpay_android.contract.IBTCTransferContract
import com.mvc.ttpay_android.model.BTCTransferModel

class BTCTransferPresenter : IBTCTransferContract.BTCTransferPresenter() {

    override fun getModel(): IBTCTransferContract.BTCTransferModel {
        return BTCTransferModel.instance
    }

    override fun onStart() {

    }


    override fun getDetail(id: Int) {
        rxUtils.register(mIModel.getDetail(id).subscribe({ idToTransferBean ->
            if (idToTransferBean.code == 200) {
                mIView.showSuccess(idToTransferBean.data)
            } else {

            }
        }, { throwable -> LogUtils.e("BTCTransferPresenter", throwable.message) }))
    }

    override fun sendTransferMsg(address: String, password: String, tokenId: Int, value: String) {
        rxUtils.register(mIModel.sendTransferMsg(address, password, tokenId, value).subscribe({ updateBean -> mIView.transferCallBack(updateBean) }, { throwable -> LogUtils.e("BTCTransferPresenter", throwable.message) }))
    }

    companion object {
        fun newIntance(): BasePresenter<*, *> {
            return BTCTransferPresenter()
        }
    }

    override fun getTransFee(address: String) {
        rxUtils.register(mIModel!!.getTransFee(address)
                .subscribe({ idToBean ->
                    mIView!!.transFeeStatus(idToBean.isData)
                }, {
                    mIView!!.transFeeStatus(false)
                }))
    }
}
