package com.mvc.cryptovault_android.contract;

import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.base.IBaseFragment;
import com.mvc.cryptovault_android.base.IBaseModel;
import com.mvc.cryptovault_android.bean.UserInfoBean;

import io.reactivex.Observable;

public interface MineContract {
    abstract class MinePresenter extends BasePresenter<IMineModel, IMineView> {
      public abstract void getUserInfo(String token);
    }

    interface IMineModel extends IBaseModel {
        Observable<UserInfoBean> getUserInfo(String token);
    }

    interface IMineView extends IBaseFragment {
        void setUser(UserInfoBean user);
    }
}
