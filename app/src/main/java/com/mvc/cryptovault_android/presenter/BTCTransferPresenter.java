package com.mvc.cryptovault_android.presenter;

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
}
