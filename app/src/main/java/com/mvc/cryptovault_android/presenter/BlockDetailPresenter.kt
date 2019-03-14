package com.mvc.cryptovault_android.presenter

import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.contract.IBlockDetailContract
import com.mvc.cryptovault_android.model.BlockDetailModel

class BlockDetailPresenter :IBlockDetailContract.IBlockDetailPresenter() {
    override fun getBlockList(blockId: Int, pageSize: Int) {
        rxUtils.register(mIModel.getBlockList(blockId, pageSize)
                .subscribe({
                    vList->
                    if(vList.code ===200){
                        mIView.blockListSuccess(vList.data)
                    }else{
                        mIView.blockListFailed(vList.message)
                    }
                },{
                    mIView.blockListFailed(it.message!!)
                }))
    }

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