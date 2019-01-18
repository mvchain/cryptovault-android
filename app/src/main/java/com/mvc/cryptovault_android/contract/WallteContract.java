package com.mvc.cryptovault_android.contract;

import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.base.IBaseFragment;
import com.mvc.cryptovault_android.base.IBaseModel;
import com.mvc.cryptovault_android.bean.AllAssetBean;
import com.mvc.cryptovault_android.bean.AssetListBean;
import com.mvc.cryptovault_android.bean.MsgBean;
import com.mvc.cryptovault_android.bean.UpdateBean;

import io.reactivex.Observable;

public interface WallteContract {
    abstract class WalletPresenter extends BasePresenter<IWallteModel, IWallteView> {
        public abstract void getAllAsset();

        public abstract void getAssetList();

        public abstract void getWhetherToSignIn();

        public abstract void putSignIn();

        public abstract void getMsg(long timestamp, int type, int pagesize);
    }

    interface IWallteModel extends IBaseModel {
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
         * 获取用户是否签到
         *
         * @return
         */
        Observable<UpdateBean> getWhetherToSignIn();

        /**
         * 发起签到请求
         *
         * @return
         */
        Observable<UpdateBean> putSignIn();

        /**
         * 请求数据
         *
         * @return
         */
        Observable<MsgBean> getMsg(long timestamp, int type, int pagesize);


    }

    interface IWallteView extends IBaseFragment {
        void refreshAssetList(AssetListBean asset);

        void refreshAllAsset(AllAssetBean allAssetBean);

        void refreshMsg(MsgBean msgBean);

        void showSignin(boolean isSignin);

        void signRequest(boolean isSignin);

        void showNullAsset();

        void serverError();
    }
}
