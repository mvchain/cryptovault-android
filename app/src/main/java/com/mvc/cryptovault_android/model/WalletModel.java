package com.mvc.cryptovault_android.model;

import android.support.annotation.Nullable;

import com.mvc.cryptovault_android.api.ApiStore;
import com.mvc.cryptovault_android.base.BaseModel;
import com.mvc.cryptovault_android.bean.AllAssetBean;
import com.mvc.cryptovault_android.bean.AssetListBean;
import com.mvc.cryptovault_android.bean.CurrencyBean;
import com.mvc.cryptovault_android.contract.WallteContract;
import com.mvc.cryptovault_android.utils.RetrofitUtils;
import com.mvc.cryptovault_android.utils.RxHelper;

import io.reactivex.Observable;

public class WalletModel extends BaseModel implements WallteContract.IWallteModel {
    @Nullable
    public static WalletModel getInstance() {
        return new WalletModel();
    }

    @Override
    public Observable<AssetListBean> getAssetList(String token) {
        return RetrofitUtils.client(ApiStore.class).getAssetList(token).compose(RxHelper.rxSchedulerHelper()).map(assetListBean -> assetListBean);
    }

    @Override
    public Observable<CurrencyBean> getCurrencyAll(String token) {
        return RetrofitUtils.client(ApiStore.class).getCurrencyAll(token).compose(RxHelper.rxSchedulerHelper()).map(currencyBean -> currencyBean);
    }

    @Override
    public Observable<AllAssetBean> getAllAsset(String token) {
        return RetrofitUtils.client(ApiStore.class).getAssetAll(token).compose(RxHelper.rxSchedulerHelper()).map(allAssetBean -> allAssetBean);
    }
}
