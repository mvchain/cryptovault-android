package com.mvc.cryptovault_android.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mvc.cvaji.R;
import com.example.mvc.cvaji.base.BaseActivity;
import com.example.mvc.cvaji.base.BasePresenter;
import com.example.mvc.cvaji.persenter.LoginPersenter;

public class LoginActivity extends BaseActivity<LoginPersenter> implements View.OnClickListener {

    private EditText mLoginPhone;
    private EditText mLoginPwd;
    private TextView mLoginForgetPwd;
    private Button mLoginSubmit;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mLoginPhone = findViewById(R.id.login_phone);
        mLoginPwd = findViewById(R.id.login_pwd);
        mLoginForgetPwd = findViewById(R.id.login_forget_pwd);
        mLoginSubmit = findViewById(R.id.login_submit);
        mLoginSubmit.setOnClickListener(this);
        mLoginForgetPwd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_submit:
                break;
            case R.id.login_forget_pwd:

                break;
        }
    }

    @Override
    public BasePresenter initPersenter() {
        return LoginPersenter.newIntance();
    }
}
