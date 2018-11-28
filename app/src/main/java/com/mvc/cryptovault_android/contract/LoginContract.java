package com.mvc.cryptovault_android.contract;

import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.base.IBaseModel;
import com.mvc.cryptovault_android.bean.LoginBean;

import io.reactivex.Observable;

public interface LoginContract {
    abstract class LoginPresenter extends BasePresenter<ILoginModel,ILoginView> {
        public abstract void login(String phone,String pwd);
    }
    interface ILoginModel extends IBaseModel {
        /**
         * 请求登陆
         * @param phone
         * @param pwd
         * @return
         */
        Observable<LoginBean> getLoginStatus(String phone, String pwd);
    }
    interface ILoginView extends IBaseModel {
        /**
         * 登录是否成功
         * @param msg
         */
        void showLoginStauts(String msg);

        /**
         * 跳转页面
         */
        void startActivity();
    }
}
