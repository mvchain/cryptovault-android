package com.mvc.cryptovault_android.model;

import android.support.annotation.Nullable;

import com.mvc.cryptovault_android.api.ApiStroe;
import com.mvc.cryptovault_android.base.BaseModel;
import com.mvc.cryptovault_android.bean.AllAssetBean;
import com.mvc.cryptovault_android.bean.AssetListBean;
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
        return RetrofitUtils.client(ApiStroe.class).getAssetList(token).compose(RxHelper.rxSchedulerHelper()).map(assetListBean -> assetListBean);
    }

    @Override
    public Observable<AllAssetBean> getAllAsset(String token) {
        return RetrofitUtils.client(ApiStroe.class).getAssetAll(token).compose(RxHelper.rxSchedulerHelper()).map(allAssetBean -> allAssetBean);
    }
}
