package com.mvc.ttpay_android.base;

public abstract class BaseMVPFragment<P extends BasePresenter> extends BaseFragment implements IBaseFragment {
    protected P mPresenter;

    @Override
    protected void initData() {
        mPresenter = (P) initPresenter();
        if (mPresenter != null) {
            mPresenter.attachMVP(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachMVP();
        }
    }

}
