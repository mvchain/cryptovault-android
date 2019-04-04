package com.mvc.cryptovault_android.presenter

import com.mvc.cryptovault_android.base.BasePresenter
import com.mvc.cryptovault_android.contract.IAssetsDetailContract
import com.mvc.cryptovault_android.model.AssetsDetailModel

class AssetsDetailPresenter : IAssetsDetailContract.AssetsDetailPresenter() {
    companion object {
        fun newInstance(): BasePresenter<*, *> {
                    return AssetsDetailPresenter()
        }
    }

    override fun getAssetsDetailToId(id: Int) {
        rxUtils.register(mIModel.getAssetsDetailToId(id)
                .subscribe({
                    idBean->
                    if (idBean.code ==200) {
                        mIView.detailSuccess(idBean.data)
                    }else{
                        mIView.detailFailed(idBean.message)
                    }
                },{
                    mIView.detailFailed(it.message!!)
                }))
    }

    override fun getModel(): IAssetsDetailContract.AssetsDetailModel {
        return AssetsDetailModel.instance
    }

    override fun onStart() {
    }
}