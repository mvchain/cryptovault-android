package com.mvc.cryptovault_android.contract;

import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.base.IBaseFragment;
import com.mvc.cryptovault_android.base.IBaseModel;
import com.mvc.cryptovault_android.bean.AllAssetBean;
import com.mvc.cryptovault_android.bean.AssetListBean;
import com.mvc.cryptovault_android.bean.MsgBean;

import io.reactivex.Observable;

public interface WallteContract {
    abstract class WalletPresenter extends BasePresenter<IWallteModel, IWallteView> {
        public abstract void getAllAsset(String token);
        public abstract void getAssetList(String token);
        public abstract void getMsg(String token,long timestamp,int type,int pagesize);
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
         * 请求数据
         *
         * @return
         */
        Observable<MsgBean> getMsg(String token, long timestamp, int type, int pagesize);


    }

    interface IWallteView extends IBaseFragment {
        void refreshAssetList(AssetListBean asset);
        void refreshAllAsset(AllAssetBean allAssetBean);
        void refreshMsg(MsgBean msgBean);
        void showNullAsset();
        void serverError();
    }
}
