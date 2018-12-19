package com.mvc.cryptovault_android.presenter;


import android.annotation.SuppressLint;

import com.blankj.utilcode.util.LogUtils;
import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.contract.LoginContract;
import com.mvc.cryptovault_android.model.LoginModel;

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
            mIView.showLoginStauts(false, "手机号不可为空");
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
                    } else {
                        mIView.showLoginStauts(false,loginBean.getMessage());
                    }
                }, throwable -> {
                    mIView.showLoginStauts(false, throwable.getMessage());
                    LogUtils.e("LoginPresenter", throwable.getMessage());
                }));
    }

    @Override
    public void sendCode(String cellphone) {
        if (cellphone == null || cellphone.equals("")) {
            mIView.showSendCode(false, "手机号不可为空");
            return;
        }
        rxUtils.register(mIModel.sendCode(cellphone)
                .subscribe(bean -> {
                    if (bean.getCode() == 200 && bean.isData()) {
                        mIView.showSendCode(true, "验证码发送成功");
                    } else {
                        mIView.showSendCode(false, bean.getMessage());
                    }
                }, throwable -> {
                    mIView.showSendCode(false, throwable.getMessage());
                    LogUtils.e("LoginPresenter", throwable.getMessage());
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
