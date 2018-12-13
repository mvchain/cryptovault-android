package com.mvc.cryptovault_android.activity;

import android.os.Handler;

import com.blankj.utilcode.util.SPUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.mvc.cryptovault_android.MainActivity;
import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.base.BaseActivity;

import static com.mvc.cryptovault_android.common.Constant.SP.REFRESH_TOKEN;
import static com.mvc.cryptovault_android.common.Constant.SP.TOKEN;


public class StartActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_start;
    }

    @Override
    protected void initData() {
        String refreshToken = SPUtils.getInstance().getString(REFRESH_TOKEN);
        String token = SPUtils.getInstance().getString(TOKEN);
        new Handler().postDelayed(() -> {
            if (!refreshToken.equals("") && !token.equals("")) {
                startActivity(MainActivity.class);
                finish();
            } else {
                startTaskActivity(StartActivity.this);
            }
        }, 300);
    }

    @Override
    protected void initView() {
        ImmersionBar.with(this).titleBar(R.id.status_bar).statusBarDarkFont(true).init();
    }
}
