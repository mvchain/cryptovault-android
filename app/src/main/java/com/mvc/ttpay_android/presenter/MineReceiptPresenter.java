package com.mvc.ttpay_android.presenter;

import com.blankj.utilcode.util.LogUtils;
import com.mvc.ttpay_android.base.BasePresenter;
import com.mvc.ttpay_android.bean.ReceiptBean;
import com.mvc.ttpay_android.contract.IReceiptQRContract;
import com.mvc.ttpay_android.model.MineReceiptModel;

import io.reactivex.functions.Consumer;

public class MineReceiptPresenter extends IReceiptQRContract.ReceiptQRPresenter {
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
            LogUtils.e(throwable);
            mIView.showError();
        }));
    }

    @Override
    protected IReceiptQRContract.IReceiptQRModel getModel() {
        return MineReceiptModel.getInstance();
    }

    @Override
    public void onStart() {

    }
}