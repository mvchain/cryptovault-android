package com.mvc.cryptovault_android.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.base.BaseActivity;

public class AboutActivity extends BaseActivity {

    private View mBarStatus;
    private ImageView mIconAbout;
    private TextView mVersionAbout;
    private TextView mSingoutAbout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mBarStatus = findViewById(R.id.status_bar);
        mIconAbout = findViewById(R.id.about_icon);
        mVersionAbout = findViewById(R.id.about_version);
        mSingoutAbout = findViewById(R.id.about_singout);
        mSingoutAbout.setOnClickListener(v->{
            SPUtils.getInstance().remove("refreshToken");
            SPUtils.getInstance().remove("token");
            Intent intent = new Intent(AboutActivity.this,LoginActivity.class);
            startActivity(intent);
        });
    }
}
