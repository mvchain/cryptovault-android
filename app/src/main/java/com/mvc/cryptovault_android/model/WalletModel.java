package com.mvc.cryptovault_android.model;

import android.support.annotation.Nullable;

import com.blankj.utilcode.util.SPUtils;
import com.mvc.cryptovault_android.api.ApiStore;
import com.mvc.cryptovault_android.base.BaseModel;
import com.mvc.cryptovault_android.base.ExchangeRateBean;
import com.mvc.cryptovault_android.bean.AllAssetBean;
import com.mvc.cryptovault_android.bean.AssetListBean;
import com.mvc.cryptovault_android.bean.CurrencyBean;
import com.mvc.cryptovault_android.contract.WallteContract;
import com.mvc.cryptovault_android.utils.JsonHelper;
import com.mvc.cryptovault_android.utils.RetrofitUtils;
import com.mvc.cryptovault_android.utils.RxHelper;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

public class WalletModel extends BaseModel implements WallteContract.IWallteModel {
    @Nullable
    public static WalletModel getInstance() {
        return new WalletModel();
    }

    @Override
    public Observable<AssetListBean> getAssetList(String token) {
        return RetrofitUtils.client(ApiStore.class).getExchangeRate(token)
                .compose(RxHelper.rxSchedulerHelper())
                .flatMap((Function<ExchangeRateBean, ObservableSource<CurrencyBean>>) exchangeRateBean -> {
                    //查看是否有默认汇率设置，没有的话保存一份  有的话忽略
                    //保存总汇率列表  用作POPWindow显示
                    ExchangeRateBean.DataBean dataBean = exchangeRateBean.getData().get(0);
                    String default_rate = SPUtils.getInstance().getString("default_rate");
                    if (default_rate.equals("")) {
                        SPUtils.getInstance().put("default_rate", JsonHelper.jsonToString(dataBean));
                    }
                    SPUtils.getInstance().put("rate_list", JsonHelper.jsonToString(exchangeRateBean));
                    return RetrofitUtils.client(ApiStore.class).getCurrencyAll(token);
                }).flatMap((Function<CurrencyBean, ObservableSource<AssetListBean>>) currencyBean -> {
                    //保存全部令牌
                    SPUtils.getInstance().put("default_rate", JsonHelper.jsonToString(currencyBean));
                    return RetrofitUtils.client(ApiStore.class).getAssetList(token);
                }).map(assetListBean -> assetListBean);
    }

    @Override
    public Observable<CurrencyBean> getCurrencyAll(String token) {
        return RetrofitUtils.client(ApiStore.class).getCurrencyAll(token).compose(RxHelper.rxSchedulerHelper()).map(currencyBean -> currencyBean);
    }

    @Override
    public Observable<ExchangeRateBean> getExchangeRate(String token) {
        return RetrofitUtils.client(ApiStore.class).getExchangeRate(token).compose(RxHelper.rxSchedulerHelper()).map(exchangRate -> exchangRate);
    }

    @Override
    public Observable<AllAssetBean> getAllAsset(String token) {
        return RetrofitUtils.client(ApiStore.class).getAssetAll(token).compose(RxHelper.rxSchedulerHelper()).map(allAssetBean -> allAssetBean);
    }

}
