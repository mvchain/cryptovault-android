package com.mvc.cryptovault_android.contract;

import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.base.IBaseActivity;
import com.mvc.cryptovault_android.base.IBaseModel;
import com.mvc.cryptovault_android.bean.HttpTokenBean;
import com.mvc.cryptovault_android.bean.LoginBean;
import com.mvc.cryptovault_android.bean.LoginValidBean;

import org.json.JSONException;

import io.reactivex.Observable;

public interface ILoginContract {
    abstract class LoginPresenter extends BasePresenter<ILoginModel, ILoginView> {
        public abstract void login(String imageToken,String email, String pwd, String code);

        public abstract void sendCode(String cellphone);


        public abstract void getValid(String email);
        public abstract void postValid(String geetest_challenge,String geetest_seccode,String geetest_validate,int status,String uid);
    }

    interface ILoginModel extends IBaseModel {
        /**
         * 请求登录
         *
         * @param email
         * @param pwd
         * @return
         */
        Observable<LoginBean> getLoginStatus(String imageToken,String email, String pwd, String code);

        Observable<HttpTokenBean> sendCode(String cellphone);

        Observable<LoginValidBean> getValid(String email);

        Observable<HttpTokenBean> postValid(String geetest_challenge,String geetest_seccode,String geetest_validate,int status,String uid);

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

        void showValid(LoginValidBean.DataBean result) throws JSONException;

        void showVerification(String message) throws JSONException;
        void showSecondaryVerification(String token);
        /**
         * 显示dialog
         */
        void show();
    }
}
