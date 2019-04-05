package com.mvc.cryptovault_android.contract;

import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.base.IBaseFragment;
import com.mvc.cryptovault_android.base.IBaseModel;
import com.mvc.cryptovault_android.bean.AllAssetBean;
import com.mvc.cryptovault_android.bean.AssetListBean;
import com.mvc.cryptovault_android.bean.MsgBean;
import com.mvc.cryptovault_android.bean.UpdateBean;

import io.reactivex.Observable;

public interface IWalletContract {
    abstract class WalletPresenter extends BasePresenter<IWalletModel, IWalletView> {
        public abstract void getAllAsset();

        public abstract void getAssetList();

        public abstract void getMsg(long timestamp, int type, int pagesize);
    }

    interface IWalletModel extends IBaseModel {
        /**
         * remove All asset
         *
         * @return
         */
        Observable<AllAssetBean> getAllAsset();

        /**
         * remove asset list
         *
         * @return
         */
        Observable<AssetListBean> getAssetList();

        /**
         * 请求数据
         *
         * @return
         */
        Observable<MsgBean> getMsg(long timestamp, int type, int pagesize);


    }

    interface IWalletView extends IBaseFragment {
        void refreshAssetList(AssetListBean asset);

        void refreshAllAsset(AllAssetBean allAssetBean);

        void refreshMsg(MsgBean msgBean);

        void showNullAsset();

        void serverError();
    }
}