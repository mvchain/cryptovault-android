package com.mvc.cryptovault_android.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.geetest.sdk.GT3ConfigBean;
import com.geetest.sdk.GT3ErrorBean;
import com.geetest.sdk.GT3GeetestUtils;
import com.geetest.sdk.GT3Listener;
import com.gyf.barlibrary.ImmersionBar;
import com.mvc.cryptovault_android.MainActivity;
import com.mvc.cryptovault_android.MyApplication;
import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.api.ApiStore;
import com.mvc.cryptovault_android.base.BaseMVPActivity;
import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.bean.LoginBean;
import com.mvc.cryptovault_android.bean.LoginValidBean;
import com.mvc.cryptovault_android.bean.ValidResultBean;
import com.mvc.cryptovault_android.common.Constant;
import com.mvc.cryptovault_android.contract.LoginContract;
import com.mvc.cryptovault_android.listener.EditTextChange;
import com.mvc.cryptovault_android.listener.OnTimeEndCallBack;
import com.mvc.cryptovault_android.presenter.LoginPresenter;
import com.mvc.cryptovault_android.utils.JsonHelper;
import com.mvc.cryptovault_android.utils.RetrofitUtils;
import com.mvc.cryptovault_android.utils.RxHelper;
import com.mvc.cryptovault_android.utils.TimeVerification;
import com.mvc.cryptovault_android.view.DialogHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

import static com.mvc.cryptovault_android.common.Constant.SP.REFRESH_TOKEN;
import static com.mvc.cryptovault_android.common.Constant.SP.REG_EMAIL;
import static com.mvc.cryptovault_android.common.Constant.SP.TAG_NAME;
import static com.mvc.cryptovault_android.common.Constant.SP.TOKEN;
import static com.mvc.cryptovault_android.common.Constant.SP.UPDATE_PASSWORD_TYPE;
import static com.mvc.cryptovault_android.common.Constant.SP.USER_EMAIL;
import static com.mvc.cryptovault_android.common.Constant.SP.USER_ID;
import static com.mvc.cryptovault_android.common.Constant.SP.USER_PUBLIC_KEY;


public class LoginActivity extends BaseMVPActivity<LoginContract.LoginPresenter> implements View.OnClickListener, LoginContract.ILoginView {

    private EditText mLoginEmail;
    private EditText mLoginPwd;
    private TextView mLoginForgetPwd;
    private Button mLoginSubmit;
    private DialogHelper dialogHelper;
    private EditText mCodeLogin;
    private TextView mCodeSend;
    private TextInputLayout mPasswordLayout;
    private GT3GeetestUtils gt3GeetestUtils;
    private GT3ConfigBean gt3ConfigBean;
    private int status;
    private String uid;
    private String token = "";
    private boolean isValid = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        // 务必在oncreate方法里处理
        gt3GeetestUtils = new GT3GeetestUtils(this);
        // 配置bean文件，也可在oncreate初始化
        gt3ConfigBean = new GT3ConfigBean();
        // 设置验证模式，1：bind，2：unbind
        gt3ConfigBean.setPattern(1);
        // 设置点击灰色区域是否消失，默认不消息
        gt3ConfigBean.setCanceledOnTouchOutside(false);
        // 设置debug模式，开代理可调试
        gt3ConfigBean.setDebug(false);
        // 设置语言，如果为null则使用系统默认语言
        gt3ConfigBean.setLang(SPUtils.getInstance().getString(Constant.LANGUAGE.DEFAULT_LANGUAGE));
        // 设置加载webview超时时间，单位毫秒，默认10000，仅且webview加载静态文件超时，不包括之前的http请求
        gt3ConfigBean.setTimeout(10000);
        // 设置webview请求超时(用户点选或滑动完成，前端请求后端接口)，单位毫秒，默认10000
        gt3ConfigBean.setWebviewTimeout(10000);
        // 设置回调监听
        gt3ConfigBean.setListener(new GT3Listener() {
            @Override
            public void onDialogResult(String s) {
                ValidResultBean validBean = (ValidResultBean) JsonHelper.stringToJson(s, ValidResultBean.class);
                mPresenter.postValid(validBean.getGeetest_challenge(), validBean.getGeetest_seccode(), validBean.getGeetest_validate(), status, uid);
            }

            @Override
            public void onStatistics(String s) {

            }

            @Override
            public void onClosed(int i) {
                dialogHelper.dismiss();
            }

            @Override
            public void onSuccess(String s) {

            }

            @Override
            public void onFailed(GT3ErrorBean gt3ErrorBean) {

            }

            @SuppressLint("CheckResult")
            @Override
            public void onButtonClick() {
                mPresenter.getValid(mLoginEmail.getText().toString().trim());
            }
        });
        gt3GeetestUtils.init(gt3ConfigBean);
    }


    @Override
    public void onClick(View v) {
        String email = mLoginEmail.getText().toString().trim();
        switch (v.getId()) {
            case R.id.login_submit:
                isValid = true;
                String pwd = mLoginPwd.getText().toString().trim();
                String code = mCodeLogin.getText().toString().trim();
                SPUtils.getInstance().put(REG_EMAIL, email);
                String newsPwd = EncryptUtils.encryptMD5ToString(email + EncryptUtils.encryptMD5ToString(pwd));
                mPresenter.login(token, email, newsPwd, code);
                break;
            case R.id.login_forget_pwd:
                SPUtils.getInstance().put(UPDATE_PASSWORD_TYPE, "1");
                startActivity(ForgetPasswordActivity.class);
                break;
            case R.id.send_code:
                dialogHelper.create(this, R.drawable.pending_icon_1, "发送验证码").show();
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
    protected void onDestroy() {
        super.onDestroy();
        gt3GeetestUtils.destory();
    }

    @Override
    public void showLoginStauts(boolean isSuccess, String msg) {
        if (isSuccess) {
            dialogHelper.resetDialogResource(this, R.drawable.success_icon, msg);
            dialogHelper.dismissDelayed(() -> {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }, 2000);
        } else {
            dialogHelper.resetDialogResource(this, R.drawable.miss_icon, msg);
            dialogHelper.dismissDelayed(null, 2000);
        }
    }

    @SuppressLint("CheckResult")
    @Override
    public void saveUserInfo(LoginBean loginBean) {
        LoginBean.DataBean data = loginBean.getData();
        SPUtils.getInstance().put(REFRESH_TOKEN, data.getRefreshToken());
        SPUtils.getInstance().put(TOKEN, data.getToken());
        SPUtils.getInstance().put(USER_ID, data.getUserId());
        SPUtils.getInstance().put(USER_EMAIL, data.getEmail());
        SPUtils.getInstance().put(USER_PUBLIC_KEY, data.getPublicKey());
        MyApplication.setTOKEN(data.getToken());
        RetrofitUtils.client(MyApplication.getBaseUrl(),ApiStore.class).getPushTag(MyApplication.getTOKEN()).compose(RxHelper.rxSchedulerHelper())
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
                    LogUtils.e("MyJPushMessageReceiver", "注册");
                    JPushInterface.setAlias(getApplicationContext(), loginBean.getData().getUserId(), String.valueOf(loginBean.getData().getUserId()));
                }, throwable -> {
                    LogUtils.e("MyJPushMessageReceiver", throwable.getMessage());
                });
    }

    @Override
    public void userNotRegister(String mnemo) {
        dialogHelper.dismissDelayed(null, 0);
        List<String> mnemonicss = Arrays.asList(mnemo.split(","));
        Intent intent = new Intent(this, VerificationMnemonicActivity.class);
        intent.putStringArrayListExtra("menmonicss", new ArrayList(mnemonicss));
        startActivity(intent);
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
                    mCodeSend.setText("重新发送");
                }
            }).updataTime();
        } else {
            dialogHelper.resetDialogResource(this, R.drawable.miss_icon, msg);
        }
        dialogHelper.dismissDelayed(null, 2000);
    }

    @Override
    public void showValid(LoginValidBean.DataBean result) throws JSONException {
        if (result != null) {
            status = result.getStatus();
            uid = result.getUid();
            gt3ConfigBean.setApi1Json(new JSONObject(result.getResult()));
            gt3GeetestUtils.getGeetest();
        }
    }

    @Override
    public void showVerification(String message) {
        //                 开启验证
        dialogHelper.resetDialogResource(this, R.drawable.miss_icon, message);
        dialogHelper.dismissDelayed(() -> {
            if (isValid) {
                gt3GeetestUtils.startCustomFlow();
                isValid = false;
            }
        }, 2000);

    }

    @Override
    public void showSecondaryVerification(String token) {
        if (!token.equals("")) {
            this.token = token;
            gt3GeetestUtils.showSuccessDialog();
            String email = mLoginEmail.getText().toString().trim();
            String pwd = mLoginPwd.getText().toString().trim();
            String code = mCodeLogin.getText().toString().trim();
            LogUtils.e(pwd);
            SPUtils.getInstance().put(REG_EMAIL, email);
            String newsPwd = EncryptUtils.encryptMD5ToString(email + EncryptUtils.encryptMD5ToString(pwd));
            mPresenter.login(token, email, newsPwd, code);
        } else {
            gt3GeetestUtils.showFailedDialog();
        }
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
        dialogHelper = DialogHelper.Companion.getInstance();
        mLoginEmail = findViewById(R.id.login_phone);
        mLoginPwd = findViewById(R.id.login_pwd);
        mPasswordLayout = findViewById(R.id.password_layout);
        mLoginForgetPwd = findViewById(R.id.login_forget_pwd);
        mLoginSubmit = findViewById(R.id.login_submit);
        mCodeLogin = findViewById(R.id.login_code);
        mCodeSend = findViewById(R.id.send_code);
        mCodeSend.setOnClickListener(this);
        mLoginSubmit.setOnClickListener(this);
        mLoginForgetPwd.setOnClickListener(this);
        mLoginPwd.addTextChangedListener(new EditTextChange() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = s.length();
                if (length > 0) {
                    mPasswordLayout.setPasswordVisibilityToggleEnabled(true);
                } else {
                    mPasswordLayout.setPasswordVisibilityToggleEnabled(false);
                }
            }
        });
    }

    @Override
    public void startActivity() {

    }
}
