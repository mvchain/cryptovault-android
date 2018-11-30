package com.mvc.cryptovault_android.contract;

import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.base.IBaseActivity;
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
    interface ILoginView extends IBaseActivity {
        /**
         * 登录是否成功
         * @param msg
         */
        void showLoginStauts(String msg);

        /**
         * 保存用户token
         * @param loginBean
         */
        void saveUserInfo(LoginBean loginBean);

        /**
         * 显示dialog
         */
        void show();

        /**
         * 关闭dialog
         */
        void dismiss();
    }
}
