package com.mvc.cryptovault_android.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.util.ArraySet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.mvc.cryptovault_android.MainActivity;
import com.mvc.cryptovault_android.MyApplication;
import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.api.ApiStore;
import com.mvc.cryptovault_android.base.BaseMVPActivity;
import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.bean.LoginBean;
import com.mvc.cryptovault_android.bean.UpdateBean;
import com.mvc.cryptovault_android.contract.LoginContract;
import com.mvc.cryptovault_android.listener.EditTextChange;
import com.mvc.cryptovault_android.listener.OnTimeEndCallBack;
import com.mvc.cryptovault_android.presenter.LoginPresenter;
import com.mvc.cryptovault_android.utils.RetrofitUtils;
import com.mvc.cryptovault_android.utils.RxHelper;
import com.mvc.cryptovault_android.utils.TimeVerification;
import com.mvc.cryptovault_android.utils.ViewDrawUtils;
import com.mvc.cryptovault_android.view.ClearEditText;
import com.mvc.cryptovault_android.view.DialogHelper;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.HashSet;

import cn.jpush.android.api.JPushInterface;

import static com.mvc.cryptovault_android.common.Constant.SP.REFRESH_TOKEN;
import static com.mvc.cryptovault_android.common.Constant.SP.TAG_NAME;
import static com.mvc.cryptovault_android.common.Constant.SP.TOKEN;
import static com.mvc.cryptovault_android.common.Constant.SP.USER_ID;


public class LoginActivity extends BaseMVPActivity<LoginContract.LoginPresenter> implements View.OnClickListener, LoginContract.ILoginView {

    private EditText mLoginPhone;
    private EditText mLoginPwd;
    private TextView mLoginForgetPwd;
    private Button mLoginSubmit;
    private DialogHelper dialogHelper;
    private EditText mCodeLogin;
    private TextView mCodeSend;
    private TextInputLayout mPasswordLayout;

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
        String email = mLoginPhone.getText().toString().trim();
        switch (v.getId()) {
            case R.id.login_submit:
                String pwd = mLoginPwd.getText().toString().trim();
                String code = mCodeLogin.getText().toString().trim();
                mPresenter.login(email, pwd, code);
                break;
            case R.id.login_forget_pwd:
                startActivity(ForgetPasswordActivity.class);
                break;
            case R.id.send_code:
                dialogHelper.create(this, R.drawable.pending_icon_1, "发送验证码中").show();
                mPresenter.sendCode(email);
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    @Override
    public BasePresenter initPresenter() {
        return LoginPresenter.newIntance();
    }


    @Override
    public void showLoginStauts(boolean isSuccess, String msg) {
        if (isSuccess) {
            dialogHelper.resetDialogResource(this, R.drawable.success_icon, msg);
            dialogHelper.dismissDelayed(() -> {
                startActvity(MainActivity.class);
                finish();
            }, 1000);
        } else {
            dialogHelper.resetDialogResource(this, R.drawable.miss_icon, msg);
            dialogHelper.dismissDelayed(null, 1500);
        }
    }

    @SuppressLint("CheckResult")
    @Override
    public void saveUserInfo(LoginBean loginBean) {
        LoginBean.DataBean data = loginBean.getData();
        SPUtils.getInstance().put(REFRESH_TOKEN, data.getRefreshToken());
        SPUtils.getInstance().put(TOKEN, data.getToken());
        SPUtils.getInstance().put(USER_ID, data.getUserId());
        MyApplication.setTOKEN(data.getToken());
        RetrofitUtils.client(ApiStore.class).getPushTag(MyApplication.getTOKEN()).compose(RxHelper.rxSchedulerHelper())
                .subscribe(tagBean -> {
                    if (tagBean.getCode() == 200 && tagBean.getData() != null) {
                        SPUtils.getInstance().put(TAG_NAME, tagBean.getData());
                        String[] tags = tagBean.getData().split(",");
                        for (int i = 0; i < tags.length; i++) {
                            if (i == 0) {
                                JPushInterface.setTags(getApplication().getApplicationContext(), Integer.parseInt(tags[i]), new HashSet<>(Arrays.asList(tags[i])));
                            } else {
                                JPushInterface.addTags(getApplication().getApplicationContext(), Integer.parseInt(tags[i]), new HashSet<>(Arrays.asList(tags[i])));
                            }
                        }
                    }
                    JPushInterface.setAlias(getApplicationContext(), loginBean.getData().getUserId(), String.valueOf(loginBean.getData().getUserId()));
                }, throwable -> {
                    LogUtils.e("ParameterInterceptor", throwable.getMessage());
                });
    }

    @Override
    public void userNotRegister(String mnemonicss) {

    }

    @Override
    public void showSendCode(boolean isSuccess, String msg) {
        if (isSuccess) {
            dialogHelper.resetDialogResource(this, R.drawable.success_icon, msg);
            TimeVerification.getInstence().setOnTimeEndCallBack(new OnTimeEndCallBack() {
                @Override
                public void updata(int time) {
                    mCodeSend.setEnabled(false);
                    mCodeSend.setBackgroundResource(R.drawable.shape_load_sendcode_bg);
                    mCodeSend.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.edit_bg));
                    mCodeSend.setText(time + "s");
                }

                @Override
                public void exit() {
                    mCodeSend.setEnabled(true);
                    mCodeSend.setBackgroundResource(R.drawable.shape_sendcode_bg);
                    mCodeSend.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.login_content));
                    mCodeSend.setText("重新获取");
                }
            }).updataTime();
        } else {
            dialogHelper.resetDialogResource(this, R.drawable.miss_icon, msg);
        }
        dialogHelper.dismissDelayed(null, 1500);
    }

    @Override
    public void show() {
        dialogHelper.create(LoginActivity.this, R.drawable.pending_icon_1, getResources().getString(R.string.login_load)).show();
    }

    @Override
    protected void initMVPData() {
        ImmersionBar.with(this).titleBar(R.id.status_bar).statusBarDarkFont(true).init();
    }

    @Override
    protected void initMVPView() {
        dialogHelper = DialogHelper.getInstance();
        mLoginPhone = findViewById(R.id.login_phone);
        mLoginPwd = findViewById(R.id.login_pwd);
        mPasswordLayout = findViewById(R.id.password_layout);
        mLoginForgetPwd = findViewById(R.id.login_forget_pwd);
        mLoginSubmit = findViewById(R.id.login_submit);
        mCodeLogin = findViewById(R.id.login_code);
        mCodeSend = findViewById(R.id.send_code);
        mCodeSend.setOnClickListener(this);
        mLoginSubmit.setOnClickListener(this);
        mLoginForgetPwd.setOnClickListener(this);
        mLoginPwd.addTextChangedListener(new EditTextChange(){
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = s.length();
                if(length>0){
                    mPasswordLayout.setPasswordVisibilityToggleEnabled(true);
                }else{
                    mPasswordLayout.setPasswordVisibilityToggleEnabled(false);
                }
            }
        });
    }

    @Override
    public void startActivity() {

    }
}
