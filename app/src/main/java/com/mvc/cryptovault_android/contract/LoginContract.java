package com.mvc.cryptovault_android.contract;

import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.base.IBaseActivity;
import com.mvc.cryptovault_android.base.IBaseModel;
import com.mvc.cryptovault_android.bean.HttpTokenBean;
import com.mvc.cryptovault_android.bean.LoginBean;
import com.mvc.cryptovault_android.bean.UpdateBean;

import io.reactivex.Observable;

public interface LoginContract {
    abstract class LoginPresenter extends BasePresenter<ILoginModel, ILoginView> {
        public abstract void login(String phone, String pwd, String code);

        public abstract void sendCode(String cellphone);
    }

    interface ILoginModel extends IBaseModel {
        /**
         * 请求登陆
         *
         * @param phone
         * @param pwd
         * @return
         */
        Observable<LoginBean> getLoginStatus(String phone, String pwd, String code);

        Observable<HttpTokenBean> sendCode(String cellphone);

    }

    interface ILoginView extends IBaseActivity {
        /**
         * 登录是否成功
         *
         * @param msg
         */
        void showLoginStauts(boolean isSuccess,String msg);

        /**
         * 保存用户token
         *
         * @param loginBean
         */
        void saveUserInfo(LoginBean loginBean);
        /**
         * 用户未激活
         *
         * @param mnemonicss
         */
        void userNotRegister(String mnemonicss);

        /**
         * 验证码是否发送成功
         *
         * @param msg
         */
        void showSendCode(boolean isSuccess,String msg);

        /**
         * 显示dialog
         */
        void show();
    }
}
