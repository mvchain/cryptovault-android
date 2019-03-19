package com.mvc.cryptovault_android.presenter

import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.contract.IBlockDetailContract
import com.mvc.cryptovault_android.model.BlockDetailModel

class BlockDetailPresenter :IBlockDetailContract.IBlockDetailPresenter() {
    override fun getBlockDetail(blockId: String) {
        rxUtils.register(mIModel.getBlockDetail(blockId)
                .subscribe({
                    vList->
                    if(vList.code ===200){
                        mIView.blockDetailSuccess(vList.data)
                    }else{
                        mIView.blockDetailFailed(vList.message)
                    }
                },{
                    mIView.blockListFailed(it.message!!)
                }))
    }

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

    override fun getBlockAllList(blockId: String, pageSize: Int, transactionId: Int) {
        rxUtils.register(mIModel.getBlockAllList(blockId, pageSize,transactionId)
                .subscribe({
                    vList->
                    if(vList.code ===200){
                        mIView.blockAllListSuccess(vList.data)
                    }else{
                        mIView.blockAllListFailed(vList.message)
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