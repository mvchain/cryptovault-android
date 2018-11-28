package com.mvc.cryptovault_android.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.gyf.barlibrary.ImmersionBar;
import com.mvc.cryptovault_android.R;

public abstract class BaseMVPActivity<P extends BasePresenter> extends BaseActivity implements IBaseView{
    protected P mPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = (P) initPresenter();
        if(mPresenter != null){
            mPresenter.attchMVP(this);
        }
        ImmersionBar.with(this).titleBar(R.id.status_bar).statusBarDarkFont(true).init();
        initView();
        initData();
    }


    protected abstract void initData();

    protected abstract void initView();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mPresenter != null){
            mPresenter.detachMVP();
        }
    }
    protected void startActvity(Class clazz){
        startActivity(clazz);
    }
    protected void setStatusRect(){

    }
}
