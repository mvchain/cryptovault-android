package com.mvc.cryptovault_android.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mvc.cryptovault_android.MainActivity;
import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.base.BaseMVPActivity;
import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.contract.LoginContract;


public class LoginActivity extends BaseMVPActivity<LoginContract.LoginPresenter> implements View.OnClickListener,LoginContract.ILoginView {

    private EditText mLoginPhone;
    private EditText mLoginPwd;
    private TextView mLoginForgetPwd;
    private Button mLoginSubmit;

    @Override
    protected int getLayoutId() {
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
                String phone = mLoginPhone.getText().toString().trim();
                String pwd = mLoginPwd.getText().toString().trim();
                mPresenter.login(phone,pwd);
                break;
            case R.id.login_forget_pwd:

                break;
        }
    }

    @Override
    public BasePresenter initPresenter() {
        return com.mvc.cryptovault_android.presenter.LoginPresenter.newIntance();
    }

    @Override
    public void showLoginStauts(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startActivity() {
        startActvity(MainActivity.class);
        finish();
    }
}
