package com.mvc.cryptovault_android.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

public abstract class BaseActivity<P extends BasePresenter> extends RxAppCompatActivity implements IBaseView{
    protected P persenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutID());
        persenter = (P) initPersenter();
        if(persenter != null){
            persenter.attchMVP(this);
        }
        initView();
        initData();
    }


    protected abstract void initData();

    protected abstract void initView();

    protected abstract int getLayoutID();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(persenter != null){
            persenter.detachMVP();
        }
    }
}
