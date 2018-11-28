package com.mvc.cryptovault_android;

import com.mvc.cryptovault_android.base.BaseMVPActivity;
import com.mvc.cryptovault_android.base.BasePresenter;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends BaseMVPActivity {
    private boolean isBack = false;
    private Timer timer = new Timer();
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
        if(isBack) {
            super.onBackPressed();
        }else{
            isBack = true;
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    isBack = false;
                }
            },1000);
            showToast(R.string.app_exit);
        }
    }
}
