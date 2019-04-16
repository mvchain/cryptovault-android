package com.mvc.ttpay_android.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.mvc.ttpay_android.MyApplication;
import com.mvc.ttpay_android.R;
import com.mvc.ttpay_android.api.ApiStore;
import com.mvc.ttpay_android.base.BaseActivity;
import com.mvc.ttpay_android.utils.AppInnerDownLoder;
import com.mvc.ttpay_android.utils.RetrofitUtils;
import com.mvc.ttpay_android.utils.RxHelper;
import com.mvc.ttpay_android.view.DialogHelper;
import com.per.rslibrary.IPermissionRequest;
import com.per.rslibrary.RsPermission;


public class AboutActivity extends BaseActivity {
    private TextView mVersionAbout;
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
        mBackAbout = findViewById(R.id.about_back);
        mBackAbout.setOnClickListener(v -> finish());
        dialogHelper = DialogHelper.Companion.getInstance();
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

    @SuppressLint("CheckResult")
    public void onClick(View view) {
        RetrofitUtils.client(MyApplication.getBaseUrl(), ApiStore.class).updateApk(MyApplication.getTOKEN(), "apk")
                .compose(RxHelper.rxSchedulerHelper())
                .subscribe(installApkBean -> {
                    if (installApkBean.getCode() == 200) {
                        PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                        int versionCode = packageInfo.versionCode;
                        if (installApkBean.getData().getAppVersionCode() > versionCode) {
                            dialogHelper.create(AboutActivity.this, getString(R.string.check_to_new_version), viewId -> {
                                switch (viewId) {
                                    case R.id.hint_enter:
                                        dialogHelper.dismiss();
                                        RsPermission.getInstance().setiPermissionRequest(new IPermissionRequest() {
                                            @Override
                                            public void toSetting() {

                                            }

                                            @Override
                                            public void cancle(int i) {

                                            }

                                            @Override
                                            public void success(int i) {
                                                AppInnerDownLoder.downLoadApk(AboutActivity.this
                                                        , installApkBean.getData().getHttpUrl()
                                                        , getString(R.string.app_name)
                                                        , getString(R.string.version_upgrade)
                                                        , getString(R.string.downloading_package));
                                            }
                                        }).requestPermission(AboutActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                                        break;
                                    case R.id.hint_cancle:
                                        dialogHelper.dismiss();
                                        break;
                                }
                            }).show();
                        } else {
                            ToastUtils.showShort(getString(R.string.the_current_version));
                        }
                    } else {
                        LogUtils.e(installApkBean.getMessage());
                    }
                }, throwable -> LogUtils.e(throwable.getMessage()));
    }
}
