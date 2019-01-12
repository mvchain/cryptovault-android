package com.mvc.cryptovault_android.activity;

import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.mvc.cryptovault_android.MainActivity;
import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.base.BaseActivity;

import static com.mvc.cryptovault_android.common.Constant.LANGUAGE.ACCEPT_CHINESE;
import static com.mvc.cryptovault_android.common.Constant.LANGUAGE.CHINESE;
import static com.mvc.cryptovault_android.common.Constant.LANGUAGE.DEFAULT_ACCEPT_LANGUAGE;
import static com.mvc.cryptovault_android.common.Constant.LANGUAGE.DEFAULT_LANGUAGE;
import static com.mvc.cryptovault_android.common.Constant.SP.REFRESH_TOKEN;
import static com.mvc.cryptovault_android.common.Constant.SP.TOKEN;


public class StartActivity extends BaseActivity {
    private TextView mLogin;
    private TextView mRegister;
    private AlphaAnimation alphaAnimation;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_start;
    }

    @Override
    protected void initData() {
        String default_language = SPUtils.getInstance().getString(DEFAULT_LANGUAGE);
        String default_accept_language = SPUtils.getInstance().getString(DEFAULT_ACCEPT_LANGUAGE);
        //如果没有默认语言  就设置为中文
        if (default_language.equals("")) {
            SPUtils.getInstance().put(DEFAULT_LANGUAGE, CHINESE);
        }
        //如果没有默认国际化语言  就设置为中文
        if (default_accept_language.equals("")) {
            SPUtils.getInstance().put(DEFAULT_ACCEPT_LANGUAGE, ACCEPT_CHINESE);
        }
        String refreshToken = SPUtils.getInstance().getString(REFRESH_TOKEN);
        String token = SPUtils.getInstance().getString(TOKEN);
        new Handler().postDelayed(() -> {
            if (!refreshToken.equals("") && !token.equals("")) {
//                SPUtils.getInstance().put(TOKEN, "eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6IjEzMDc1ODAxOTMzIiwidXNlcklkIjoxMCwic2VydmljZSI6ImFwcCIsInR5cGUiOiJ0b2tlbiIsImV4cCI6MTU0NjkzNDQ0OX0.Lo3ZKEMlm43d21a1KnZh74W6ryLbCgst6N93Y4o6Aho");
                startActivity(MainActivity.class);
                finish();
            } else {
                startTaskActivity(this);
            }
        }, 300);
    }

    @Override
    protected void initView() {
        ImmersionBar.with(this).titleBar(R.id.status_bar).statusBarDarkFont(true).init();
    }
}
