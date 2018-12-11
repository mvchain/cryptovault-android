package com.mvc.cryptovault_android.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.SPUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.mvc.cryptovault_android.MainActivity;
import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.base.BaseMVPActivity;
import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.bean.LoginBean;
import com.mvc.cryptovault_android.contract.LoginContract;
import com.mvc.cryptovault_android.presenter.LoginPresenter;
import com.mvc.cryptovault_android.view.DialogHelper;


public class LoginActivity extends BaseMVPActivity<LoginContract.LoginPresenter> implements View.OnClickListener,LoginContract.ILoginView {

    private EditText mLoginPhone;
    private EditText mLoginPwd;
    private TextView mLoginForgetPwd;
    private Button mLoginSubmit;
    private DialogHelper dialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }
    @Override
    protected void initData() {
    }

    @Override
    protected void initView() {
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
        return LoginPresenter.newIntance();
    }

    @Override
    public void showLoginStauts(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void saveUserInfo(LoginBean loginBean) {
        LoginBean.DataBean data = loginBean.getData();
        SPUtils.getInstance().put("refreshToken",data.getRefreshToken());
        SPUtils.getInstance().put("token",data.getToken());
    }

    @Override
    public void show() {
        dialog.create(LoginActivity.this,R.drawable.pending_icon,getResources().getString(R.string.login_load)).show();
    }

    @Override
    public void dismiss() {
        dialog.dismiss();
    }

    @Override
    public void startActivity() {
        startActvity(MainActivity.class);
        finish();
    }

    @Override
    protected void initMVPData() {
        ImmersionBar.with(this).titleBar(R.id.status_bar).statusBarDarkFont(true).init();
    }

    @Override
    protected void initMVPView() {
        dialog = DialogHelper.getInstance();
        mLoginPhone = findViewById(R.id.login_phone);
        mLoginPwd = findViewById(R.id.login_pwd);
        mLoginForgetPwd = findViewById(R.id.login_forget_pwd);
        mLoginSubmit = findViewById(R.id.login_submit);
        mLoginSubmit.setOnClickListener(this);
        mLoginForgetPwd.setOnClickListener(this);
    }
}
