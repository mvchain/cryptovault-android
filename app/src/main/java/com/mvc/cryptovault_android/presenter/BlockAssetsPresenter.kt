package com.mvc.cryptovault_android.presenter

import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.contract.BlockAssetsContract
import com.mvc.cryptovault_android.model.BlockAssetsModel

class BlockAssetsPresenter : BlockAssetsContract.BlockAssetsPresenter() {
    override fun getBlockBalance(publicKey: String) {
        rxUtils.register(mIModel.getBlockBalance(publicKey)
                .subscribe({ balance ->
                    if (balance.code == 200) {
                        mIView.balanceSuccess(balance.data)
                    } else {
                        mIView.balanceFailed(balance.message)
                    }
                }, {
                    mIView.balanceFailed(it.message!!)
                }))
    }

    override fun getBlockOrder(id: Int, pageSize: Int, publicKey: String) {
        rxUtils.register(mIModel.getBlockOrder(id, pageSize, publicKey)
                .subscribe({ orderBean ->
                    if (orderBean.code == 200) {
                        mIView.orderSuccess(orderBean.data)
                    } else {
                        mIView.balanceFailed(orderBean.message)
                    }
                }, {
                    mIView.balanceFailed(it.message!!)
                }))
    }

    companion object {
        fun newInstance(): BasePresenter<*, *> {
            return BlockAssetsPresenter()
        }
    }

    override fun getModel(): BlockAssetsContract.BlockAssetsModel {
        return BlockAssetsModel.instance
    }

    override fun onStart() {
    }
}