package com.mvc.cryptovault_android.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.blankj.utilcode.util.KeyboardUtils;


public abstract class BaseMVPActivity<P extends BasePresenter> extends BaseActivity implements IBaseView {
    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = (P) initPresenter();
        if (mPresenter != null) {
            mPresenter.attchMVP(this);
        }
        initMVPView();
        initMVPData();
    }

    protected abstract void initMVPData();

    protected abstract void initMVPView();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachMVP();
        }
    }

    protected void startActvity(Class clazz) {
        startActivity(clazz);
    }
}
