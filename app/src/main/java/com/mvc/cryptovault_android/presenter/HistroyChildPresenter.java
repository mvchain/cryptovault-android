package com.mvc.cryptovault_android.presenter;

import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.bean.HistroyBean;
import com.mvc.cryptovault_android.contract.HistroyChildContract;
import com.mvc.cryptovault_android.model.HistroyChildModel;

import io.reactivex.functions.Consumer;

public class HistroyChildPresenter extends HistroyChildContract.HistroyChildPrecenter {
    public static BasePresenter newIntance() {
        return new HistroyChildPresenter();
    }

    @Override
    protected HistroyChildModel getModel() {
        return HistroyChildModel.getInstance();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void getAll(int classify, int id, int pageSize, int tokenId, int transactionType, int type) {
        rxUtils.register(mIModel.getAll(classify, id, pageSize, tokenId, transactionType, type)
                .subscribe(histroyBean -> {
                    if (histroyBean.getCode() == 200) {
                        if (histroyBean.getData().size() > 0) {
                            mIView.showSuccess(histroyBean.getData());
                        } else {
                            mIView.showNull();
                        }
                    } else {
                        mIView.showNull();
                    }
                }, throwable -> {
                    mIView.showNull();
                }));
    }
}
