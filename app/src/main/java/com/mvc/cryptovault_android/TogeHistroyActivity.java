package com.mvc.cryptovault_android;

import com.gyf.barlibrary.ImmersionBar;
import com.mvc.cryptovault_android.base.BaseMVPActivity;
import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.contract.TogeHistroyContract;

public class TogeHistroyActivity extends BaseMVPActivity<TogeHistroyContract.TogeHistroyPresenter> implements TogeHistroyContract.ITogeHisView {
    @Override
    protected void initMVPData() {

    }

    @Override
    protected void initMVPView() {
        ImmersionBar.with(this).statusBarView(R.id.status_bar).statusBarDarkFont(true).init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_togehis;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    public void startActivity() {

    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }
}
