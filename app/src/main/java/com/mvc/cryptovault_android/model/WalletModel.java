package com.mvc.cryptovault_android.model;

import android.support.annotation.Nullable;

import com.blankj.utilcode.util.SPUtils;
import com.mvc.cryptovault_android.api.ApiStore;
import com.mvc.cryptovault_android.base.BaseModel;
import com.mvc.cryptovault_android.base.ExchangeRateBean;
import com.mvc.cryptovault_android.bean.AllAssetBean;
import com.mvc.cryptovault_android.bean.AssetListBean;
import com.mvc.cryptovault_android.bean.CurrencyBean;
import com.mvc.cryptovault_android.bean.MsgBean;
import com.mvc.cryptovault_android.contract.WallteContract;
import com.mvc.cryptovault_android.utils.JsonHelper;
import com.mvc.cryptovault_android.utils.RetrofitUtils;
import com.mvc.cryptovault_android.utils.RxHelper;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

import static com.mvc.cryptovault_android.common.Constant.SP.CURRENCY_LIST;
import static com.mvc.cryptovault_android.common.Constant.SP.DEFAULE_RATE;
import static com.mvc.cryptovault_android.common.Constant.SP.DEFAULE_SYMBOL;
import static com.mvc.cryptovault_android.common.Constant.SP.RATE_LIST;
import static com.mvc.cryptovault_android.common.Constant.SP.SET_RATE;

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
                    String default_rate = SPUtils.getInstance().getString(SET_RATE); //获取默认汇率
                    String defaule_symbol = SPUtils.getInstance().getString(DEFAULE_SYMBOL);
                    SPUtils.getInstance().put(DEFAULE_RATE, JsonHelper.jsonToString(dataBean));
                    if (default_rate.equals("")) {
                        SPUtils.getInstance().put(SET_RATE, JsonHelper.jsonToString(dataBean));
                    } else {
                        for (ExchangeRateBean.DataBean bean : exchangeRateBean.getData()) {
                            ExchangeRateBean.DataBean setDataBean = (ExchangeRateBean.DataBean) JsonHelper.stringToJson(default_rate, ExchangeRateBean.DataBean.class);
                            if (bean.equals(setDataBean.getName())) {
                                SPUtils.getInstance().put(SET_RATE, JsonHelper.jsonToString(setDataBean));
                                break;
                            }
                        }
                    }
                    //保存默认的法币符号
                    if (defaule_symbol.equals("")) {
                        String symbol = dataBean.getName();
                        String newSymbol = symbol.substring(0, 1);
                        SPUtils.getInstance().put(DEFAULE_SYMBOL, newSymbol);
                    }
                    SPUtils.getInstance().put(RATE_LIST, JsonHelper.jsonToString(exchangeRateBean));
                    return RetrofitUtils.client(ApiStore.class).getCurrencyAll(token).compose(RxHelper.rxSchedulerHelper());
                })
                .flatMap((Function<CurrencyBean, ObservableSource<AssetListBean>>) currencyBean -> {
                    //保存全部令牌
                    SPUtils.getInstance().put(CURRENCY_LIST, JsonHelper.jsonToString(currencyBean));
                    return RetrofitUtils.client(ApiStore.class).getAssetList(token).compose(RxHelper.rxSchedulerHelper());
                })
                .map(assetListBean -> assetListBean);
    }

    @Override
    public Observable<MsgBean> getMsg(String token, long timestamp, int type, int pagesize) {
        return RetrofitUtils.client(ApiStore.class).getMsg(token, timestamp, type, pagesize).compose(RxHelper.rxSchedulerHelper()).map(msgBean -> msgBean);
    }


    @Override
    public Observable<AllAssetBean> getAllAsset(String token) {
        return RetrofitUtils.client(ApiStore.class).getAssetAll(token).compose(RxHelper.rxSchedulerHelper()).map(allAssetBean -> allAssetBean);
    }

}
