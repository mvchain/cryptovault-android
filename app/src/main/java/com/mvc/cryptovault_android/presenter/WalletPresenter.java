package com.mvc.cryptovault_android.presenter;

import com.blankj.utilcode.util.LogUtils;
import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.contract.IWalletContract;
import com.mvc.cryptovault_android.model.WalletModel;

public class WalletPresenter extends IWalletContract.WalletPresenter {

    public static BasePresenter newInstance() {
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
    public void getWhetherToSignIn() {
        rxUtils.register(mIModel.getWhetherToSignIn().subscribe(
                updateBean -> {
                    if (updateBean.getCode() == 200) {
                        mIView.showSignin(updateBean.isData());
                    }
                }
                ,
                throwable -> {
                    mIView.showSignin(true);
                }
        ));
    }

    @Override
    public void putSignIn() {
        rxUtils.register(mIModel.putSignIn().subscribe(
                updateBean -> {
                    if (updateBean.getCode() == 200) {
                        mIView.signRequest(updateBean.isData());
                    }
                }
                ,
                throwable -> {
                    mIView.signRequest(false);
                }
        ));
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

    @Override
    public void getBanner() {
        rxUtils.register(mIModel.getBanner()
                .subscribe(banner -> {
                            if (banner.getCode() == 200) {
                                if (banner.getData().size() > 0) {
                                    mIView.bannerList(banner.getData());
                                }
                            }
                        }
                        , throwable -> {
                            LogUtils.e("WalletPresenter", throwable.getMessage());
                        }));
    }
}
