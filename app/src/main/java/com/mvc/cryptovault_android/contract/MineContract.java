package com.mvc.cryptovault_android.contract;

import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.base.IBaseFragment;
import com.mvc.cryptovault_android.base.IBaseModel;
import com.mvc.cryptovault_android.bean.UserInfoBean;

import io.reactivex.Observable;

public interface MineContract {
    abstract class MinePresenter extends BasePresenter<IMineModel, IMineView> {
      public abstract void getUserInfo();
    }

    interface IMineModel extends IBaseModel {
        Observable<UserInfoBean> getUserInfo();
    }

    interface IMineView extends IBaseFragment {
        void setUser(UserInfoBean user);
        void serverError();
    }
}
