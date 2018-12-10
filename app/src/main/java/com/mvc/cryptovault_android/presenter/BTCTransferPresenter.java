package com.mvc.cryptovault_android.presenter;

import com.blankj.utilcode.util.LogUtils;
import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.contract.BTCTransferContract;
import com.mvc.cryptovault_android.model.BTCTransferModel;

public class BTCTransferPresenter extends BTCTransferContract.BTCTransferPresenter {
     public static BasePresenter newIntance() {
             return new BTCTransferPresenter();
     }
    @Override
    protected BTCTransferContract.BTCTransferModel getModel() {
        return BTCTransferModel.getInstance();
    }

    @Override
    public void onStart() {

    }


    @Override
    public void getDetail(String token, int id) {
        rxUtils.register(mIModel.getDetail(token, id).subscribe(idToTransferBean -> {
            if (idToTransferBean.getCode()==200) {
                mIView.showSuccess(idToTransferBean.getData());
            }else{

            }
        }, throwable -> {
            LogUtils.e("BTCTransferPresenter", throwable.getMessage());
        }));
    }
}
