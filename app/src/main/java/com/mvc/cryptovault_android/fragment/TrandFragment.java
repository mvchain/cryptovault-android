package com.mvc.cryptovault_android.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.activity.TrandOrderActivity;
import com.mvc.cryptovault_android.adapter.TrandPagerAdapter;
import com.mvc.cryptovault_android.api.ApiStore;
import com.mvc.cryptovault_android.base.BaseFragment;
import com.mvc.cryptovault_android.bean.TrandChildBean;
import com.mvc.cryptovault_android.utils.JsonHelper;
import com.mvc.cryptovault_android.utils.RetrofitUtils;
import com.mvc.cryptovault_android.utils.RxHelper;
import com.mvc.cryptovault_android.utils.TabLayoutUtils;
import com.mvc.cryptovault_android.view.NoScrollViewPager;

import java.util.ArrayList;

import io.reactivex.functions.Consumer;

import static com.mvc.cryptovault_android.common.Constant.SP.TRAND_LIST;

public class TrandFragment extends BaseFragment implements View.OnClickListener {
    private TabLayout mTableTrand;
    private ImageView mHistroyTrand;
    private NoScrollViewPager mVpTrand;
    private ArrayList<Fragment> mFragments;
    private TrandPagerAdapter trandPagerAdapter;

    @SuppressLint("CheckResult")
    @Override
    protected void initData() {
        TrandChildFragment vrtFragment = new TrandChildFragment();
        Bundle vrtBundle = new Bundle();
        vrtBundle.putInt("pairType", 1);
        vrtFragment.setArguments(vrtBundle);
        mFragments.add(vrtFragment);
        TrandChildFragment banFragment = new TrandChildFragment();
        Bundle banBundle = new Bundle();
        banBundle.putInt("pairType", 2);
        banFragment.setArguments(banBundle);
        mFragments.add(banFragment);
        trandPagerAdapter = new TrandPagerAdapter(getChildFragmentManager(), mFragments);
        mVpTrand.setAdapter(trandPagerAdapter);
        mTableTrand.setupWithViewPager(mVpTrand);
        TabLayoutUtils.setIndicator(mTableTrand, 40, 40);
        //保存所有交易对
        RetrofitUtils.client(ApiStore.class).getAllVrtAndBalance(getToken())
                .compose(RxHelper.rxSchedulerHelper())
                .subscribe(trandChildBean ->
                                SPUtils.getInstance().put(TRAND_LIST, JsonHelper.jsonToString(trandChildBean))
                        , throwable -> LogUtils.e("TrandFragment", throwable.getMessage()));
    }

    @Override
    protected void initView() {
        mTableTrand = rootView.findViewById(R.id.trand_table);
        mHistroyTrand = rootView.findViewById(R.id.trand_histroy);
        mHistroyTrand.setOnClickListener(this);
        mVpTrand = rootView.findViewById(R.id.trand_vp);
        mFragments = new ArrayList<>();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_trand;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.trand_histroy:
                Intent intent = new Intent(activity, TrandOrderActivity.class);
                startActivity(intent);
                break;
        }
    }

}
