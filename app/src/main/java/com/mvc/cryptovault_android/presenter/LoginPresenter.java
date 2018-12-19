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
    public void login(String phone, String pwd) {
        if (mIModel == null || mIView == null) {
            return;
        }
        if (phone == null || phone.equals("")) {
            mIView.showLoginStauts("手机号不可为空");
            return;
        }
        if (pwd == null || pwd.equals("")) {
            mIView.showLoginStauts("密码不可为空");
            return;
        }
        mIView.show();
        rxUtils.register(mIModel.getLoginStatus(phone, pwd)
                .subscribe(loginBean -> {
                    if (loginBean.getCode() == 200) {
                        mIView.showLoginStauts("登录成功");
                        mIView.startActivity();
                        mIView.saveUserInfo(loginBean);
                    } else {
                        mIView.showLoginStauts(loginBean.getMessage());
                    }
                    mIView.dismiss();
                }, throwable -> {
                    mIView.dismiss();
                    mIView.showLoginStauts("登录失败, 失败原因：" + throwable.getMessage());
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
