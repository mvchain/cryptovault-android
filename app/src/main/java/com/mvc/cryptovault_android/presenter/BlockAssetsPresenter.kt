package com.mvc.cryptovault_android.presenter

import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.contract.BlockAssetsContract
import com.mvc.cryptovault_android.model.BlockAssetsModel

class BlockAssetsPresenter : BlockAssetsContract.BlockAssetsPresenter() {
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