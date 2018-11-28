package com.mvc.cryptovault_android.contract;

import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.base.IBaseFragment;
import com.mvc.cryptovault_android.base.IBaseModel;

import java.util.List;

import io.reactivex.Observable;

public interface WallteContract {
    abstract class WalletPresenter extends BasePresenter<IWallteModel, IWallteView> {
        public abstract void refreshData();

        public abstract void loadMoreData(String phone, String pwd);
    }

    interface IWallteModel extends IBaseModel {
        /**
         * 请求数据
         *
         * @return
         */
        Observable<List<String>> getData();
    }

    interface IWallteView extends IBaseFragment {
        void refresh(List<String> string);
    }
}
