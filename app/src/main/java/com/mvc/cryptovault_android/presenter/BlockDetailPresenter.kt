package com.mvc.cryptovault_android.presenter

import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.contract.IBlockDetailContract
import com.mvc.cryptovault_android.model.BlockDetailModel

class BlockDetailPresenter :IBlockDetailContract.IBlockDetailPresenter() {
    companion object {
        fun newInstance(): BasePresenter<*, *> {
                    return BlockDetailPresenter()
        }
    }
    override fun getModel(): IBlockDetailContract.IBlockDetailModel {
        return BlockDetailModel.instance
    }

    override fun onStart() {
    }
}