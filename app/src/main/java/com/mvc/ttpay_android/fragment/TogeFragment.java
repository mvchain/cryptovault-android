package com.mvc.ttpay_android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.mvc.ttpay_android.R;
import com.mvc.ttpay_android.activity.PublishListActivity;
import com.mvc.ttpay_android.activity.TogeHistroyActivity;
import com.mvc.ttpay_android.adapter.TogePagerAdapter;
import com.mvc.ttpay_android.base.BaseFragment;

import java.util.ArrayList;

public class TogeFragment extends BaseFragment implements View.OnClickListener {
    private TextView mHistroyToge;
    private TextView mPublishToge;
    private TabLayout mTableToge;
    private ViewPager mVpToge;
    private ArrayList<Fragment> mFragment;
    private TogePagerAdapter togePagerAdapter;

    @Override
    protected void initData() {
        TogeChildFragment soonFragment = new TogeChildFragment();
        Bundle soonBundle = new Bundle();
        soonBundle.putInt("projectType", 0);
        soonFragment.setArguments(soonBundle);
        mFragment.add(soonFragment);
        TogeChildFragment receiceFragment = new TogeChildFragment();
        Bundle receiceBundle = new Bundle();
        receiceBundle.putInt("projectType", 1);
        receiceFragment.setArguments(receiceBundle);
        mFragment.add(receiceFragment);
        TogeChildFragment overFragment = new TogeChildFragment();
        Bundle overBundle = new Bundle();
        overBundle.putInt("projectType", 2);
        overFragment.setArguments(overBundle);
        mFragment.add(overFragment);
        TogeChildFragment meFragment = new TogeChildFragment();
        Bundle meBundle = new Bundle();
        meBundle.putInt("projectType", 3);
        meFragment.setArguments(meBundle);
        mFragment.add(meFragment);
        togePagerAdapter = new TogePagerAdapter(getChildFragmentManager(), mFragment, activity);
        mVpToge.setAdapter(togePagerAdapter);
        mTableToge.setupWithViewPager(mVpToge);
        mVpToge.setCurrentItem(1);
//        TabLayoutUtils.setIndicator(mTableToge, 30, 30);
    }

    @Override
    protected void initView() {
        mFragment = new ArrayList<>();
        mHistroyToge = rootView.findViewById(R.id.toge_histroy);
        mPublishToge = rootView.findViewById(R.id.toge_publish);
        mHistroyToge.setOnClickListener(this);
        mPublishToge.setOnClickListener(this);
        mTableToge = rootView.findViewById(R.id.toge_table);
        mVpToge = rootView.findViewById(R.id.toge_vp);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_together;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.toge_histroy:
                // TODO 18/11/29
                intent.setClass(activity, TogeHistroyActivity.class);
                startActivity(intent);
                break;

            case R.id.toge_publish:
                // TODO 18/11/29
                intent.setClass(activity, PublishListActivity.class);
                startActivity(intent);
                break;
        }
    }

}
