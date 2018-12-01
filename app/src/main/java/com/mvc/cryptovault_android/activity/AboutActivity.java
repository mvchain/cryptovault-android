package com.mvc.cryptovault_android.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.base.BaseActivity;

public class AboutActivity extends BaseActivity implements View.OnClickListener {

    private View mBarStatus;
    private ImageView mIconAbout;
    private TextView mVersionAbout;
    private TextView mSingoutAbout;
    private ImageView mBackAbout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    protected void initData() {
        ImmersionBar.with(this).statusBarView(R.id.status_bar).statusBarDarkFont(true).init();
    }

    protected void initView() {

        mBarStatus = findViewById(R.id.status_bar);
        mIconAbout = findViewById(R.id.about_icon);
        mVersionAbout = findViewById(R.id.about_version);
        mSingoutAbout = findViewById(R.id.about_singout);
        mSingoutAbout.setOnClickListener(this);
        mBackAbout = findViewById(R.id.about_back);
        mBackAbout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.about_back:
                // TODO 18/11/30
                finish();
                break;
            case R.id.about_singout:
                // TODO 18/11/30
                SPUtils.getInstance().remove("refreshToken");
                SPUtils.getInstance().remove("token");
                startTaskActivity(this);
                break;
        }
    }
}
