package com.mvc.cryptovault_android.presenter;

import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.contract.WallteContract;
import com.mvc.cryptovault_android.model.WalletModel;

import java.util.List;

import io.reactivex.functions.Consumer;

public class WalletPresenter extends WallteContract.WalletPresenter {

    public static BasePresenter newIntance() {
        return new WalletPresenter();
    }

    @Override
    public void refreshData() {
        rxUtils.register(mIModel.getData().subscribe(new Consumer<List<String>>() {
            @Override
            public void accept(List<String> strings) {
                mIView.refresh(strings);
            }
        },throwable -> {}));
    }

    @Override
    public void loadMoreData(String phone, String pwd) {

    }

    @Override
    protected WalletModel getModel() {
        return WalletModel.getInstance();
    }

    @Override
    public void onStart() {

    }
}
