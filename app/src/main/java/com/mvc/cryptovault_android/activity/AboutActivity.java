package com.mvc.cryptovault_android.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.mvc.cryptovault_android.MainActivity;
import com.mvc.cryptovault_android.MyApplication;
import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.api.ApiStore;
import com.mvc.cryptovault_android.base.BaseActivity;
import com.mvc.cryptovault_android.bean.InstallApkBean;
import com.mvc.cryptovault_android.utils.AppInnerDownLoder;
import com.mvc.cryptovault_android.utils.RetrofitUtils;
import com.mvc.cryptovault_android.utils.RxHelper;
import com.mvc.cryptovault_android.view.DialogHelper;
import com.per.rslibrary.IPermissionRequest;
import com.per.rslibrary.RsPermission;

import io.reactivex.functions.Consumer;


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
        dialogHelper = DialogHelper.getInstance();
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
        RetrofitUtils.client(ApiStore.class).updateApk(MyApplication.getTOKEN(), "apk")
                .compose(RxHelper.rxSchedulerHelper())
                .subscribe(installApkBean -> {
                    if (installApkBean.getCode() == 200) {
                        PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                        int versionCode = packageInfo.versionCode;
                        if (installApkBean.getData().getAppVersionCode() > versionCode) {
                            dialogHelper.create(AboutActivity.this, "检查到新版本，是否更新？", viewId -> {
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
                                                AppInnerDownLoder.downLoadApk(AboutActivity.this, installApkBean.getData().getHttpUrl(), "BZT");
                                            }
                                        }).requestPermission(AboutActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                                        break;
                                    case R.id.hint_cancle:
                                        dialogHelper.dismiss();
                                        break;
                                }
                            }).show();
                        } else {
                            ToastUtils.showShort("当前版本已是最新");
                        }
                    } else {
                        LogUtils.e(installApkBean.getMessage());
                    }
                }, throwable -> LogUtils.e(throwable.getMessage()));
    }
}
