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


public class AboutActivity extends BaseActivity implements View.OnClickListener {

    private TextView mVersionAbout;
    private TextView mSingoutAbout;
    private ImageView mBackAbout;
    private DialogHelper dialogHelper;

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
        mSingoutAbout = findViewById(R.id.about_singout);
        mSingoutAbout.setOnClickListener(this);
        mBackAbout = findViewById(R.id.about_back);
        mBackAbout.setOnClickListener(this);
        dialogHelper = DialogHelper.getInstance();
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
                dialogHelper.create(this, "确定要登出VPay?", viewId -> {
                    switch (viewId) {
                        case R.id.hint_enter:
                            dialogHelper.dismiss();
                            startTaskActivity(AboutActivity.this);
                            break;
                        case R.id.hint_cancle:
                            dialogHelper.dismiss();
                            break;
                    }
                }).show();
                break;
        }
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
