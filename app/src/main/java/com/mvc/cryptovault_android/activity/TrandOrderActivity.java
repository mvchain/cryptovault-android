package com.mvc.cryptovault_android.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.adapter.TrandOrderViewPagerAdapter;
import com.mvc.cryptovault_android.base.BaseActivity;
import com.mvc.cryptovault_android.fragment.TrandOrderFragment;

import java.util.ArrayList;

public class TrandOrderActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBackM;
    private TextView mTitleM;
    private ImageView mFilterM;
    private TabLayout mTablayoutM;
    private ViewPager mViewpagerM;
    private ArrayList<Fragment> mFragment;
    private TrandOrderViewPagerAdapter orderAdapter;
    private String pairId = "";
    private String transactionType = "";


    @Override
    protected int getLayoutId() {
        return R.layout.activity_trand_order;
    }

    @Override
    protected void initData() {
        pairId = getIntent().getStringExtra("pairId");
        transactionType = getIntent().getStringExtra("transactionType");
        ImmersionBar.with(this).statusBarView(R.id.status_bar).statusBarDarkFont(true).init();
        TrandOrderFragment processFragment = new TrandOrderFragment();
        Bundle processBundle = new Bundle();
        processFragment.setArguments(processBundle);
        processBundle.putInt("status", 0);
        processBundle.putString("pairId", pairId);
        processBundle.putString("transactionType", transactionType);
        mFragment.add(processFragment);
        TrandOrderFragment overFragment = new TrandOrderFragment();
        Bundle overBundle = new Bundle();
        overBundle.putInt("status", 1);
        overBundle.putString("pairId", pairId);
        overBundle.putString("transactionType", transactionType);
        overFragment.setArguments(overBundle);
        mFragment.add(overFragment);
        orderAdapter = new TrandOrderViewPagerAdapter(getSupportFragmentManager(), mFragment);
        mViewpagerM.setAdapter(orderAdapter);
        mTablayoutM.setupWithViewPager(mViewpagerM);
    }

    @Override
    protected void initView() {
        mFragment = new ArrayList<>();
        mBackM = findViewById(R.id.m_back);
        mTitleM = findViewById(R.id.m_title);
        mFilterM = findViewById(R.id.m_filter);
        mTablayoutM = findViewById(R.id.m_tablayout);
        mViewpagerM = findViewById(R.id.m_viewpager);
        mBackM.setOnClickListener(this);
        mFilterM.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.m_back:
                // TODO 18/12/15
                finish();
                break;
            case R.id.m_filter:
                // TODO 18/12/15
                startActivity(FilterOrderActivity.class);
                break;
        }
    }
}
