package com.mvc.ttpay_android;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.blankj.utilcode.util.LogUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.mvc.ttpay_android.adapter.HomePagerAdapter;
import com.mvc.ttpay_android.api.ApiStore;
import com.mvc.ttpay_android.base.BaseMVPActivity;
import com.mvc.ttpay_android.base.BasePresenter;
import com.mvc.ttpay_android.bean.LanguageEvent;
import com.mvc.ttpay_android.fragment.*;
import com.mvc.ttpay_android.utils.AppInnerDownLoder;
import com.mvc.ttpay_android.utils.RetrofitUtils;
import com.mvc.ttpay_android.utils.RxHelper;
import com.mvc.ttpay_android.view.DialogHelper;
import com.per.rslibrary.IPermissionRequest;
import com.per.rslibrary.RsPermission;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends BaseMVPActivity {
    private boolean isBack = false;
    private Timer timer = new Timer();
    private RadioGroup mButtonGroupHome;
    private ArrayList<Fragment> mFragment;
    private HomePagerAdapter pagerAdapter;
    private View statusBar;
    private Fragment currentFragment;
    private int[] colors = {R.drawable.home_top_toolbar, R.drawable.home_top_toolbar, R.drawable.imm_white, R.drawable.home_top_toolbar};
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

    @SuppressLint("CheckResult")
    @Override
    protected void initMVPData() {
        WalletFragment walletFragment = new WalletFragment();
        mFragment.add(walletFragment);
        FinancialManagementFragment financial = new FinancialManagementFragment();
        mFragment.add(financial);
        TradingAreaFragment tradingFragment = new TradingAreaFragment();
        mFragment.add(tradingFragment);
        MineFragment mineFragment = new MineFragment();
        mFragment.add(mineFragment);
        int childCount = mButtonGroupHome.getChildCount();
        for (int i = 0; i < childCount; i++) {
            int finalI = i;
            mButtonGroupHome.getChildAt(i).setOnClickListener(v -> {
                switch (finalI) {
                    case 0:
                        switchFragment(walletFragment).commit();
                        break;
                    case 1:
                        switchFragment(financial).commit();
                        break;
                    case 2:
                        switchFragment(tradingFragment).commit();
                        break;
                    case 3:
                        switchFragment(mineFragment).commit();
                        break;
                }
                statusBar.setBackgroundResource(colors[finalI]);
                ImmersionBar.with(this).titleBar(statusBar).statusBarDarkFont(true).init();
            });
        }
        switchFragment(walletFragment).commit();
        ((RadioButton) mButtonGroupHome.getChildAt(0)).setChecked(true);
        ImmersionBar.with(this).titleBar(statusBar).statusBarDarkFont(true).init();
        RetrofitUtils.client(MyApplication.getBaseUrl(), ApiStore.class).updateApk(MyApplication.getTOKEN(), "apk")
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
                                                AppInnerDownLoder.downLoadApk(MainActivity.this
                                                        , installApkBean.getData().getHttpUrl()
                                                        , "TTPay"
                                                        , "版本升级"
                                                        , "正在下载安装包，请稍后");
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
        mButtonGroupHome = findViewById(R.id.home_button_group);
        statusBar = findViewById(R.id.status_bar);
        mFragment = new ArrayList<>();
        dialogHelper = DialogHelper.Companion.getInstance();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mMainVpHome.removeOnPageChangeListener(this);
        ImmersionBar.with(this).destroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (mFragment != null && ((RadioButton) mButtonGroupHome.getChildAt(0)).isChecked() && mPresenter != null && mFragment.get(0) instanceof WalletFragment) {
            ((WalletFragment) mFragment.get(0)).onRefresh();
        }
    }

    @Subscribe
    public void changeLanguage(LanguageEvent languageEvent) {
        recreate();
    }

    private FragmentTransaction switchFragment(Fragment targetFragment){
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        if (!targetFragment.isAdded()) {
            //第一次使用switchFragment()时currentFragment为null，所以要判断一下
            if (currentFragment != null) {
                transaction.hide(currentFragment);
            }
            transaction.add(R.id.home_main_fragment, targetFragment,targetFragment.getClass().getName());
        } else {
            transaction
                    .hide(currentFragment)
                    .show(targetFragment);
        }
        currentFragment = targetFragment;
        return  transaction;
    }

}
