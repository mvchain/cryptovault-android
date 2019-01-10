package com.mvc.cryptovault_android.presenter;

import com.blankj.utilcode.util.LogUtils;
import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.contract.WallteContract;
import com.mvc.cryptovault_android.model.WalletModel;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

public class WalletPresenter extends WallteContract.WalletPresenter {

    public static BasePresenter newIntance() {
        return new WalletPresenter();
    }

    @Override
    public void getAssetList() {
        rxUtils.register(mIModel.getAssetList()
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
    public void getMsg(long timestamp, int type, int pagesize) {
        rxUtils.register(mIModel.getMsg(timestamp, type, pagesize)
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
    public void getAllAsset() {
        rxUtils.register(mIModel.getAllAsset()
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
