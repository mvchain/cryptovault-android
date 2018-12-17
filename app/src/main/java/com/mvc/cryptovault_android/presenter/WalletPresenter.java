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
                                if (asset.getData().size() > 0) {
                                    mIView.refreshAssetList(asset);
                                } else {
                                    mIView.showNullAsset();
                                }
                            }
                        }
                        , throwable -> {
                            mIView.serverError();
                            LogUtils.e("WalletPresenter", throwable.getMessage());
                        }));
    }

    @Override
    public void getMsg(String token, long timestamp, int type, int pagesize) {
        rxUtils.register(mIModel.getMsg(token, timestamp, type, pagesize)
                .subscribe(msgBean -> {
                            if (msgBean.getCode() == 200) {
                                mIView.refreshMsg(msgBean);
                            }
                        }
                        , throwable -> {
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
