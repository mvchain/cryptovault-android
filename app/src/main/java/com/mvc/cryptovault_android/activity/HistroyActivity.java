package com.mvc.cryptovault_android.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.adapter.rvAdapter.HistroyPagerAdapter;
import com.mvc.cryptovault_android.base.BaseMVPActivity;
import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.base.ExchangeRateBean;
import com.mvc.cryptovault_android.contract.HistroyContract;
import com.mvc.cryptovault_android.fragment.HistroyChildFragment;
import com.mvc.cryptovault_android.listener.IPopViewListener;
import com.mvc.cryptovault_android.presenter.HistroyPresenter;
import com.mvc.cryptovault_android.utils.JsonHelper;
import com.mvc.cryptovault_android.utils.TextViewDrawUtils;
import com.mvc.cryptovault_android.view.NoScrollViewPager;
import com.mvc.cryptovault_android.view.PopViewHelper;

import java.util.ArrayList;
import java.util.List;

public class HistroyActivity extends BaseMVPActivity<HistroyContract.HistroyPrecenter> implements HistroyContract.IHistroyView, View.OnClickListener, IPopViewListener {
    private ImageView mBackHis;
    private ImageView mQcodeHis;
    private TextView mPriceHis;
    private TextView mTypeHis;
    private TextView mActualHis;
    private TabLayout mTabHis;
    private NoScrollViewPager mVpHis;
    private TextView mSubHis;
    private ArrayList<Fragment> fragments;
    private HistroyPagerAdapter histroyPagerAdapter;
    private TextView mTitleHis;
    private TextView mOutHis;
    private TextView mInHis;
    private LinearLayout mLayoutSub;
    private Intent intent;
    private int type;
    private int tokenId;
    private PopupWindow mPopView;

    @Override
    protected void initMVPData() {
        HistroyChildFragment allFragment = new HistroyChildFragment();
        Bundle allBundle = new Bundle();
        allBundle.putInt("tokenId", tokenId);
        allBundle.putInt("action", 0);
        allFragment.setArguments(allBundle);
        fragments.add(allFragment);
        HistroyChildFragment outFragment = new HistroyChildFragment();
        Bundle outBundle = new Bundle();
        outBundle.putInt("tokenId", tokenId);
        outBundle.putInt("action", 2);
        outFragment.setArguments(outBundle);
        fragments.add(outFragment);
        HistroyChildFragment inFragment = new HistroyChildFragment();
        Bundle inBundle = new Bundle();
        inBundle.putInt("tokenId", tokenId);
        inBundle.putInt("action", 1);
        inFragment.setArguments(inBundle);
        fragments.add(inFragment);
        mVpHis.setAdapter(histroyPagerAdapter);
        mTabHis.setupWithViewPager(mVpHis);
    }

    @Override
    protected void initMVPView() {
        fragments = new ArrayList<>();
        ImmersionBar.with(this).statusBarView(R.id.status_bar).statusBarDarkFont(true).init();
        histroyPagerAdapter = new HistroyPagerAdapter(getSupportFragmentManager(), fragments);
        mBackHis = findViewById(R.id.his_back);
        mTitleHis = findViewById(R.id.his_title);
        mQcodeHis = findViewById(R.id.his_qcode);
        mPriceHis = findViewById(R.id.his_price);
        mTypeHis = findViewById(R.id.his_type);
        mActualHis = findViewById(R.id.his_actual);
        mTabHis = findViewById(R.id.his_tab);
        mVpHis = findViewById(R.id.his_vp);
        mSubHis = findViewById(R.id.his_sub);
        mOutHis = findViewById(R.id.his_out);
        mInHis = findViewById(R.id.his_in);
        mLayoutSub = findViewById(R.id.sub_layout);
        mBackHis.setOnClickListener(this);
        mQcodeHis.setOnClickListener(this);
        mTypeHis.setOnClickListener(this);
        mSubHis.setOnClickListener(this);
        mOutHis.setOnClickListener(this);
        mInHis.setOnClickListener(this);
        initIntent();
        initPop();
    }

    private void initPop() {
        String rate_default = SPUtils.getInstance().getString("rate_default");
        ArrayList<String> content = new ArrayList<>();
        if (rate_default != null && !rate_default.equals("")) {
            ExchangeRateBean rateBean = (ExchangeRateBean) JsonHelper.stringToJson(rate_default, ExchangeRateBean.class);
            for (ExchangeRateBean.DataBean dataBean : rateBean.getData()) {
                content.add(dataBean.getName());
            }
            mPopView = PopViewHelper.getInstance().setiPopViewListener(this).create(this, R.layout.layout_rate_pop, content);
        }
    }

    private void initIntent() {
        intent = getIntent();
        type = intent.getIntExtra("type", 0);
        tokenId = intent.getIntExtra("tokenId", 0);
        switch (type) {
            case 0:
                mTitleHis.setText(R.string.his_title_vp);
                mLayoutSub.setVisibility(View.VISIBLE);
                mOutHis.setVisibility(View.GONE);
                mInHis.setVisibility(View.GONE);
                mSubHis.setVisibility(View.VISIBLE);
                break;
            case 1:
                mTitleHis.setText(R.string.his_title_bth);
                mLayoutSub.setVisibility(View.GONE);
                break;
            case 2:
                mTitleHis.setText(R.string.his_title_znb);
                mLayoutSub.setVisibility(View.VISIBLE);
                mOutHis.setVisibility(View.VISIBLE);
                mInHis.setVisibility(View.VISIBLE);
                mSubHis.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_histroy;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {


    }

    @Override
    public BasePresenter initPresenter() {
        return HistroyPresenter.newIntance();
    }

    @Override
    public void startActivity() {

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.his_back:
                // TODO 18/11/29
                finish();
                break;
            case R.id.his_qcode:
                // TODO 18/11/29
                break;
            case R.id.his_type:
                // TODO 18/11/29
                mPopView.showAsDropDown(mTypeHis, -50, -10, Gravity.CENTER);
                TextViewDrawUtils.setRigthDraw(getDrawable(R.drawable.down_icon), mTypeHis);
                break;
            case R.id.his_sub:
                // TODO 18/11/29
                break;
            case R.id.his_out:// TODO 18/12/05
                break;
            case R.id.his_in:// TODO 18/12/05
                break;
        }
    }

    @Override
    public void showSuccess(List<String> msgs) {

    }

    @Override
    public void toRate(int position) {

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void dismiss() {
        TextViewDrawUtils.setRigthDraw(getDrawable(R.drawable.up_icon), mTypeHis);
    }
}
