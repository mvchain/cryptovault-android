package com.mvc.cryptovault_android;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.blankj.utilcode.util.LogUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.mvc.cryptovault_android.adapter.HomePagerAdapter;
import com.mvc.cryptovault_android.base.BaseMVPActivity;
import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.fragment.MineFragment;
import com.mvc.cryptovault_android.fragment.TogeFragment;
import com.mvc.cryptovault_android.fragment.TrandFragment;
import com.mvc.cryptovault_android.fragment.WalletFragment;
import com.mvc.cryptovault_android.view.NoScrollViewPager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
    private int[] colors = {R.color.status_blue,R.color.white,R.color.white,R.color.status_gray};
    private ImmersionBar with;

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

    @Override
    protected void initMVPData() {
        WalletFragment walletFragment = new WalletFragment();
        mFragment.add(walletFragment);
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
            mButtonGroupHome.getChildAt(i).setOnClickListener(v -> mMainVpHome.setCurrentItem(finalI));
        }
        mMainVpHome.addOnPageChangeListener(this);
        with = ImmersionBar.with(this);
        with.statusBarDarkFont(true).statusBarColor(colors[0]).fitsSystemWindows(true).init();
    }

    @Override
    protected void initMVPView() {
        mMainVpHome = findViewById(R.id.home_main_vp);
        mButtonGroupHome = findViewById(R.id.home_button_group);
        mFragment = new ArrayList<>();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMainVpHome.removeOnPageChangeListener(this);
        ImmersionBar.with(this).destroy();
    }
}
