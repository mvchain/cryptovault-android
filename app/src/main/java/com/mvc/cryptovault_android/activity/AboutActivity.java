package com.mvc.cryptovault_android.activity;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.base.BaseActivity;
import com.mvc.cryptovault_android.view.DialogHelper;


public class AboutActivity extends BaseActivity{
    private TextView mVersionAbout;
    private ImageView mBackAbout;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    protected void initData() {
        mVersionAbout.setText(getVersionName(this));
    }

    protected void initView() {
        ImmersionBar.with(this).statusBarView(R.id.status_bar).statusBarDarkFont(true).init();
        mVersionAbout = findViewById(R.id.about_version);
        mBackAbout = findViewById(R.id.about_back);
        mBackAbout.setOnClickListener(v -> finish());
    }

    /**
     * get App versionName
     *
     * @param context
     * @return
     */
    public String getVersionName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo;
        String versionName = "";
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "Version: " + versionName;
    }
}
