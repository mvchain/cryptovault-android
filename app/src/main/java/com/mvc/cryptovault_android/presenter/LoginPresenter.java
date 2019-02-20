package com.mvc.cryptovault_android.presenter;


import android.annotation.SuppressLint;

import com.blankj.utilcode.util.LogUtils;
import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.activity.TrandPurhAndSellActivity;
import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.bean.LoginValidBean;
import com.mvc.cryptovault_android.bean.UpdateBean;
import com.mvc.cryptovault_android.contract.LoginContract;
import com.mvc.cryptovault_android.model.LoginModel;

import org.json.JSONObject;

import java.net.SocketTimeoutException;

import io.reactivex.functions.Consumer;

public class LoginPresenter extends LoginContract.LoginPresenter {

    public static BasePresenter newIntance() {
        return new LoginPresenter();
    }

    @SuppressLint("CheckResult")
    @Override
    public void login(String phone, String pwd, String code) {
        mIView.show();
        if (mIModel == null || mIView == null) {
            return;
        }
        if (phone == null || phone.equals("")) {
            mIView.showLoginStauts(false, "邮箱不可为空");
            return;
        }
        if (pwd == null || pwd.equals("")) {
            mIView.showLoginStauts(false, "密码不可为空");
            return;
        }
        if (code == null || code.equals("")) {
            mIView.showLoginStauts(false, "验证码不可为空");
            return;
        }
        rxUtils.register(mIModel.getLoginStatus(phone, pwd, code)
                .subscribe(loginBean -> {
                    if (loginBean.getCode() == 200) {
                        mIView.showLoginStauts(true, "登录成功");
                        mIView.saveUserInfo(loginBean);
                    } else if (loginBean.getCode() == 406) {
                        mIView.userNotRegister(loginBean.getMessage());
                    } else if (loginBean.getCode() == 402) {
                        mIView.showVerfication();
                    } else {
                        mIView.showLoginStauts(false, loginBean.getMessage());
                    }
                }, throwable -> {
                    if (throwable instanceof SocketTimeoutException) {
                        mIView.showLoginStauts(false, "连接超时");
                    } else {
                        mIView.showLoginStauts(false, "连接超时");
                    }
                    LogUtils.e("LoginPresenter", throwable.getMessage());
                }));
    }

    @Override
    public void sendCode(String cellphone) {
        if (cellphone == null || cellphone.equals("")) {
            mIView.showSendCode(false, "邮箱不可为空");
            return;
        }
        rxUtils.register(mIModel.sendCode(cellphone)
                .subscribe(bean -> {
                    if (bean.getCode() == 200) {
                        mIView.showSendCode(true, "验证码发送成功");
                    } else {
                        mIView.showSendCode(false, bean.getMessage());
                    }
                }, throwable -> {
                    if (throwable instanceof SocketTimeoutException) {
                        mIView.showSendCode(false, "连接超时");
                    } else {
                        mIView.showSendCode(false, throwable.getMessage());
                    }
                    LogUtils.e("LoginPresenter", throwable.getMessage());
                }));
    }

    @Override
    public void getValid() {
        rxUtils.register(mIModel.getValid()
                .subscribe(loginValidBean -> {
                    if (loginValidBean.getCode() == 200) {
                        mIView.showValid(loginValidBean.getData());
                    } else {
                        mIView.showValid(null);
                    }
                }, throwable -> mIView.showValid(null)));
    }

    @Override
    public void postValid(String geetest_challenge, String geetest_seccode, String geetest_validate, int status, String uid) {
        rxUtils.register(mIModel.postValid(geetest_challenge, geetest_seccode, geetest_validate, status, uid)
                .subscribe(updateBean -> {
                    if (updateBean.getCode() == 200) {
                        mIView.showSecondaryVerification(true);
                    } else {
                        mIView.showSecondaryVerification(false);
                    }
                }, throwable -> {
                    mIView.showSecondaryVerification(false);
                    LogUtils.e(throwable.getMessage());
                }));
    }

    @Override
    protected LoginModel getModel() {
        return LoginModel.getInstance();
    }

    @Override
    public void onStart() {

    }
}
