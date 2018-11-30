package com.mvc.cryptovault_android.fragment;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.activity.HistroyActivity;
import com.mvc.cryptovault_android.adapter.TogePagerAdapter;
import com.mvc.cryptovault_android.base.BaseFragment;
import com.mvc.cryptovault_android.view.NoScrollViewPager;

import java.util.ArrayList;

public class TogeFragment extends BaseFragment implements View.OnClickListener {
    private TextView mTitleToge;
    private ImageView mHistroyToge;
    private TabLayout mTableToge;
    private NoScrollViewPager mVpToge;
    private ArrayList<Fragment> fragments;
    private TogePagerAdapter togePagerAdapter;

    @Override
    protected void initData() {
        TogeChildFragment receiceFragment = new TogeChildFragment();
        fragments.add(receiceFragment);
        TogeChildFragment soonFragment = new TogeChildFragment();
        fragments.add(soonFragment);
        TogeChildFragment overFragment = new TogeChildFragment();
        fragments.add(overFragment);
        togePagerAdapter = new TogePagerAdapter(getChildFragmentManager(), fragments);
        mVpToge.setAdapter(togePagerAdapter);
        mTableToge.setupWithViewPager(mVpToge);
    }

    @Override
    protected void initView() {
        fragments = new ArrayList<>();
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
                Intent intent = new Intent(activity,HistroyActivity.class);
                startActivity(intent);
                break;
        }
    }

}
