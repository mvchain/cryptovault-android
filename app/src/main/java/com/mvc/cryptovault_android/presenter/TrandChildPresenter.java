package com.mvc.cryptovault_android.presenter;

import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.contract.TrandChildContract;
import com.mvc.cryptovault_android.model.TrandChildModel;

public class TrandChildPresenter extends TrandChildContract.TrandChildPresenter {

    public static BasePresenter newIntance() {
        return new TrandChildPresenter();
    }

    @Override
    public void getVrt() {
        rxUtils.register(mIModel.getVrt().subscribe(strings -> mIView.showSuccess(strings), throwable -> {}));

    }

    @Override
    public void getBalanceTransactions() {
    }

    @Override
    protected TrandChildModel getModel() {
        return TrandChildModel.getInstance();
    }

    @Override
    public void onStart() {

    }
}
