package com.mvc.cryptovault_android;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.blankj.utilcode.util.LogUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.mvc.cryptovault_android.adapter.HomePagerAdapter;
import com.mvc.cryptovault_android.api.ApiStore;
import com.mvc.cryptovault_android.base.BaseMVPActivity;
import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.bean.LanguageEvent;
import com.mvc.cryptovault_android.fragment.FinancialManagementFragment;
import com.mvc.cryptovault_android.fragment.MineFragment;
import com.mvc.cryptovault_android.fragment.TogeFragment;
import com.mvc.cryptovault_android.fragment.TrandFragment;
import com.mvc.cryptovault_android.fragment.WalletFragment;
import com.mvc.cryptovault_android.utils.AppInnerDownLoder;
import com.mvc.cryptovault_android.utils.RetrofitUtils;
import com.mvc.cryptovault_android.utils.RxHelper;
import com.mvc.cryptovault_android.view.DialogHelper;
import com.mvc.cryptovault_android.view.NoScrollViewPager;
import com.per.rslibrary.IPermissionRequest;
import com.per.rslibrary.RsPermission;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends BaseMVPActivity implements ViewPager.OnPageChangeListener {
    private boolean isBack = false;
    private Timer timer = new Timer();
    private NoScrollViewPager mMainVpHome;
    private RadioGroup mButtonGroupHome;
    private ArrayList<Fragment> mFragment;
    private HomePagerAdapter pagerAdapter;
    private int[] colors = {R.color.status_blue, R.color.white, R.color.white, R.color.white,/* R.color.status_gray*/R.color.status_blue};
    private ImmersionBar with;
    private DialogHelper dialogHelper;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void onBackPressed() {
        if (isBack) {
            super.onBackPressed();
        } else {
            isBack = true;
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    isBack = false;
                }
            }, 1000);
            showToast(R.string.app_exit);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        ((RadioButton) mButtonGroupHome.getChildAt(position)).setChecked(true);
        with.statusBarDarkFont(true).statusBarColor(colors[position]).fitsSystemWindows(true).init();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @SuppressLint("CheckResult")
    @Override
    protected void initMVPData() {
        WalletFragment walletFragment = new WalletFragment();
        mFragment.add(walletFragment);
        FinancialManagementFragment financial = new FinancialManagementFragment();
        mFragment.add(financial);
        TrandFragment trandFragment = new TrandFragment();
        mFragment.add(trandFragment);
        TogeFragment togeFragment = new TogeFragment();
        mFragment.add(togeFragment);
        MineFragment mineFragment = new MineFragment();
        mFragment.add(mineFragment);
        pagerAdapter = new HomePagerAdapter(getSupportFragmentManager(), mFragment);
        mMainVpHome.setAdapter(pagerAdapter);
        int childCount = mButtonGroupHome.getChildCount();
        for (int i = 0; i < childCount; i++) {
            int finalI = i;
            mButtonGroupHome.getChildAt(i).setOnClickListener(v ->
            {
                mMainVpHome.setCurrentItem(finalI);
            });
        }
        mMainVpHome.addOnPageChangeListener(this);
        with = ImmersionBar.with(this);
        with.statusBarDarkFont(true).statusBarColor(colors[0]).fitsSystemWindows(true).init();
        RetrofitUtils.client(ApiStore.class).updateApk(MyApplication.getTOKEN(), "apk")
                .compose(RxHelper.rxSchedulerHelper())
                .subscribe(installApkBean -> {
                    if (installApkBean.getCode() == 200) {
                        PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                        int versionCode = packageInfo.versionCode;
                        if (installApkBean.getData().getAppVersionCode() > versionCode) {
                            dialogHelper.create(MainActivity.this, "检查到新版本，是否更新？", viewId -> {
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
                                                AppInnerDownLoder.downLoadApk(MainActivity.this, installApkBean.getData().getHttpUrl(), "BZT");
                                            }
                                        }).requestPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                                        break;
                                    case R.id.hint_cancle:
                                        dialogHelper.dismiss();
                                        break;
                                }
                            }).show();
                        }
                    } else {
                        LogUtils.e(installApkBean.getMessage());
                    }
                }, throwable -> LogUtils.e(throwable.getMessage()));
    }

    @Override
    protected void initMVPView() {
        mMainVpHome = findViewById(R.id.home_main_vp);
        mButtonGroupHome = findViewById(R.id.home_button_group);
        mFragment = new ArrayList<>();
        dialogHelper = DialogHelper.getInstance();
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMainVpHome.removeOnPageChangeListener(this);
        ImmersionBar.with(this).destroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (mFragment != null && ((RadioButton)mButtonGroupHome.getChildAt(0)).isChecked() && mFragment.get(0) instanceof WalletFragment) {
            ((WalletFragment) mFragment.get(0)).onRefresh();
        }
    }

    @Subscribe
    public void changeLanguage(LanguageEvent languageEvent) {
        recreate();
    }
}
