package com.mvc.cryptovault_android.fragment;

import com.blankj.utilcode.util.SPUtils;
import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.base.BaseMVPFragment;
import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.contract.MineContract;
import com.mvc.cryptovault_android.presenter.MinePresenter;

public class MineFragment extends BaseMVPFragment<MineContract.MinePresenter> implements MineContract.IMineView {

    @Override
    protected void initData() {
        super.initData();
        getUserInfo();
    }

    private void getUserInfo() {
        String token = SPUtils.getInstance().getString("token");
        mPresenter.getUserInfo(token);
    }

    @Override
    protected void initView() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_mine;
    }

    @Override
    public BasePresenter initPresenter() {
        return MinePresenter.newIntance();
    }
}
