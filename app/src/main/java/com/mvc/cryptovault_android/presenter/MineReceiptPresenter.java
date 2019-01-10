package com.mvc.cryptovault_android.presenter;

import com.blankj.utilcode.util.LogUtils;
import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.bean.ReceiptBean;
import com.mvc.cryptovault_android.contract.ReceiptQRContract;
import com.mvc.cryptovault_android.model.MineReceiptModel;

import io.reactivex.functions.Consumer;

public class MineReceiptPresenter extends ReceiptQRContract.ReceiptQRPresenter {
     public static BasePresenter newIntance() {
             return new MineReceiptPresenter();
     }
    @Override
    public void getMineQcode(int tokenId) {
        rxUtils.register(mIModel.getMineQcode(tokenId).subscribe(new Consumer<ReceiptBean>() {
            @Override
            public void accept(ReceiptBean receiptBean) throws Exception {
                if(!receiptBean.getData().equals("")){
                    mIView.showSuccess(receiptBean);
                }
            }
        },throwable -> {
            LogUtils.e("MineReceiptPresenter", throwable.getMessage());
            mIView.showError();
        }));
    }

    @Override
    protected ReceiptQRContract.IReceiptQRModel getModel() {
        return MineReceiptModel.getInstance();
    }

    @Override
    public void onStart() {

    }
}
