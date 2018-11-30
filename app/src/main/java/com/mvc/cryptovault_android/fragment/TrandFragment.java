package com.mvc.cryptovault_android.fragment;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;

import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.activity.HistroyActivity;
import com.mvc.cryptovault_android.adapter.TrandPagerAdapter;
import com.mvc.cryptovault_android.base.BaseFragment;
import com.mvc.cryptovault_android.view.NoScrollViewPager;

import java.util.ArrayList;

public class TrandFragment extends BaseFragment implements View.OnClickListener {
    private TabLayout mTableTrand;
    private ImageView mHistroyTrand;
    private NoScrollViewPager mVpTrand;
    private ArrayList<Fragment> mFragments;
    private TrandPagerAdapter trandPagerAdapter;

    @Override
    protected void initData() {
        TrandChildFragment vrtFragment = new TrandChildFragment();
        mFragments.add(vrtFragment);
        TrandChildFragment banFragment = new TrandChildFragment();
        mFragments.add(banFragment);
        trandPagerAdapter = new TrandPagerAdapter(getChildFragmentManager(), mFragments);
        mVpTrand.setAdapter(trandPagerAdapter);
        mTableTrand.setupWithViewPager(mVpTrand);
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
                Intent intent = new Intent(activity,HistroyActivity.class);
                startActivity(intent);
                break;
        }
    }

}