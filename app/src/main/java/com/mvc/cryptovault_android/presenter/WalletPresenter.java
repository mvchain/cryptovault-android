package com.mvc.cryptovault_android.presenter;

import com.blankj.utilcode.util.LogUtils;
import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.contract.WallteContract;
import com.mvc.cryptovault_android.model.WalletModel;

public class WalletPresenter extends WallteContract.WalletPresenter {

    public static BasePresenter newIntance() {
        return new WalletPresenter();
    }

    @Override
    public void getAssetList(String token) {
        rxUtils.register(mIModel.getAssetList(token)
                .subscribe(asset -> {
                            if (asset.getCode() == 200) {
                                mIView.refreshAssetList(asset);
                            }
                        }
                        , throwable -> {
                            mIView.serverError();
                            LogUtils.e("WalletPresenter", throwable.getMessage());
                        }));
    }

    @Override
    public void getCurrencyAll(String token) {
        rxUtils.register(mIModel.getCurrencyAll(token)
                .subscribe(currency -> {
                            if (currency.getCode() == 200) {
                                mIView.savaLocalCurrency(currency);
                            }
                        }
                        , throwable -> {
                            mIView.serverError();
                            LogUtils.e("WalletPresenter", throwable.getMessage());
                        }));
    }

    @Override
    public void getExchangeRate(String token) {
        rxUtils.register(mIModel.getExchangeRate(token)
                .subscribe(rate -> {
                            if (rate.getCode() == 200) {
                                mIView.savaExchangeRate(rate);
                            }
                        }
                        , throwable -> {
                            mIView.serverError();
                            LogUtils.e("WalletPresenter", throwable.getMessage());
                        }));
    }

    @Override
    public void getAllAsset(String token) {
        rxUtils.register(mIModel.getAllAsset(token)
                .subscribe(asset -> {
                            if (asset.getCode() == 200) {
                                mIView.refreshAllAsset(asset);
                            }
                        }
                        , throwable -> {
                            mIView.serverError();
                            LogUtils.e("WalletPresenter", throwable.getMessage());
                        }));
    }


    @Override
    protected WalletModel getModel() {
        return WalletModel.getInstance();
    }

    @Override
    public void onStart() {

    }
}
