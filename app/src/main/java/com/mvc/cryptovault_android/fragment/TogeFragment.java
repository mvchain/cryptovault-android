package com.mvc.cryptovault_android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.activity.TogeHistroyActivity;
import com.mvc.cryptovault_android.adapter.TogePagerAdapter;
import com.mvc.cryptovault_android.base.BaseFragment;
import com.mvc.cryptovault_android.utils.TabLayoutUtils;

import java.util.ArrayList;

public class TogeFragment extends BaseFragment implements View.OnClickListener {
    private TextView mTitleToge;
    private ImageView mHistroyToge;
    private TabLayout mTableToge;
    private ViewPager mVpToge;
    private ArrayList<Fragment> mFragment;
    private TogePagerAdapter togePagerAdapter;

    @Override
    protected void initData() {
        TogeChildFragment receiceFragment = new TogeChildFragment();
        Bundle receiceBundle = new Bundle();
        receiceBundle.putInt("projectType", 1);
        receiceFragment.setArguments(receiceBundle);
        mFragment.add(receiceFragment);
        TogeChildFragment soonFragment = new TogeChildFragment();
        Bundle soonBundle = new Bundle();
        soonBundle.putInt("projectType", 0);
        soonFragment.setArguments(soonBundle);
        mFragment.add(soonFragment);
        TogeChildFragment overFragment = new TogeChildFragment();
        Bundle overBundle = new Bundle();
        overBundle.putInt("projectType", 2);
        overFragment.setArguments(overBundle);
        mFragment.add(overFragment);
        togePagerAdapter = new TogePagerAdapter(getChildFragmentManager(), mFragment);
        mVpToge.setAdapter(togePagerAdapter);
        mTableToge.setupWithViewPager(mVpToge);
        TabLayoutUtils.setIndicator(mTableToge, 40, 40);
    }

    @Override
    protected void initView() {
        mFragment = new ArrayList<>();
        mTitleToge = rootView.findViewById(R.id.toge_title);
        mHistroyToge = rootView.findViewById(R.id.toge_histroy);
        mHistroyToge.setOnClickListener(this);
        mTableToge = rootView.findViewById(R.id.toge_table);
        mVpToge = rootView.findViewById(R.id.toge_vp);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_together;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toge_histroy:
                // TODO 18/11/29
                Intent intent = new Intent(activity, TogeHistroyActivity.class);
                startActivity(intent);
                break;
        }
    }

}
