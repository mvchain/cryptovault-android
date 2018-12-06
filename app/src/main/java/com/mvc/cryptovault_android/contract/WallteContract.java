package com.mvc.cryptovault_android.contract;

import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.base.ExchangeRateBean;
import com.mvc.cryptovault_android.base.IBaseFragment;
import com.mvc.cryptovault_android.base.IBaseModel;
import com.mvc.cryptovault_android.bean.AllAssetBean;
import com.mvc.cryptovault_android.bean.AssetListBean;
import com.mvc.cryptovault_android.bean.CurrencyBean;

import io.reactivex.Observable;

public interface WallteContract {
    abstract class WalletPresenter extends BasePresenter<IWallteModel, IWallteView> {
        public abstract void getAllAsset(String token);
        public abstract void getAssetList(String token);
        public abstract void getCurrencyAll(String token);
        public abstract void getExchangeRate(String token);
    }

    interface IWallteModel extends IBaseModel {
        /**
         * remove All asset
         *
         * @return
         */
        Observable<AllAssetBean> getAllAsset(String token);

        /**
         * remove asset list
         * @param token
         * @return
         */
        Observable<AssetListBean> getAssetList(String token);


        /**
         * remove currency list
         * @param token
         * @return
         */
        Observable<CurrencyBean> getCurrencyAll(String token);

        /**
         * remove currency list
         * @param token
         * @return
         */
        Observable<ExchangeRateBean> getExchangeRate(String token);
    }

    interface IWallteView extends IBaseFragment {
        void refreshAssetList(AssetListBean asset);
        void refreshAllAsset(AllAssetBean allAssetBean);
        void savaLocalCurrency(CurrencyBean currencyBean);
        void savaExchangeRate(ExchangeRateBean currencyBean);
        void showNullAsset();
//        void newWorkError();
        void serverError();
    }
}
