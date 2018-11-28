package com.mvc.cryptovault_android;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioGroup;

import com.mvc.cryptovault_android.adapter.HomePagerAdapter;
import com.mvc.cryptovault_android.base.BaseMVPActivity;
import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.fragment.WalletFragment;
import com.mvc.cryptovault_android.view.CenterButton;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends BaseMVPActivity implements View.OnClickListener {
    private boolean isBack = false;
    private Timer timer = new Timer();
    private View mBarStatus;
    private ViewPager mMainVpHome;
    private CenterButton mWalletHome;
    private CenterButton mTrandHome;
    private CenterButton mTogetherHome;
    private CenterButton mMainHome;
    private RadioGroup mButtonGroupHome;
    private ArrayList<Fragment> mFragment;
    private HomePagerAdapter pagerAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        WalletFragment walletFragment = new WalletFragment();
        mFragment.add(walletFragment);
        pagerAdapter = new HomePagerAdapter(getSupportFragmentManager(),mFragment);
        mMainVpHome.setAdapter(pagerAdapter);
    }

    @Override
    protected void initView() {
        mBarStatus = findViewById(R.id.status_bar);
        mMainVpHome = findViewById(R.id.home_main_vp);
        mWalletHome = findViewById(R.id.home_wallet);
        mTrandHome = findViewById(R.id.home_trand);
        mTogetherHome = findViewById(R.id.home_together);
        mMainHome = findViewById(R.id.home_main);
        mButtonGroupHome = findViewById(R.id.home_button_group);
        mWalletHome.setOnClickListener(this);
        mTrandHome.setOnClickListener(this);
        mTogetherHome.setOnClickListener(this);
        mMainHome.setOnClickListener(this);
        mFragment = new ArrayList<>();
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_wallet:
                // TODO 18/11/28
                break;
            case R.id.home_trand:
                // TODO 18/11/28
                break;
            case R.id.home_together:
                // TODO 18/11/28
                break;
            case R.id.home_main:
                // TODO 18/11/28
                break;
        }
    }
}
