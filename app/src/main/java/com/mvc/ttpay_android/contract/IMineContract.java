package com.mvc.ttpay_android.contract;

import com.mvc.ttpay_android.base.BasePresenter;
import com.mvc.ttpay_android.base.IBaseFragment;
import com.mvc.ttpay_android.base.IBaseModel;
import com.mvc.ttpay_android.bean.UserInfoBean;

import io.reactivex.Observable;

public interface IMineContract {
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
