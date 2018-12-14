package com.mvc.cryptovault_android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.adapter.TrandRecorAdapter;
import com.mvc.cryptovault_android.base.BaseActivity;
import com.mvc.cryptovault_android.bean.TrandChildBean;
import com.mvc.cryptovault_android.fragment.RecordingFragment;
import com.mvc.cryptovault_android.view.NoScrollViewPager;

import java.util.ArrayList;

import static com.mvc.cryptovault_android.common.Constant.SP.RECORDING_UNIT;

public class TrandRecordingActivity extends BaseActivity implements View.OnClickListener {
    private ArrayList<Fragment> mFragment;
    private TrandChildBean.DataBean data;
    private TextView mTitleTrand;
    private ImageView mBackTrand;
    private ImageView mHistroyTrand;
    private RelativeLayout mToolbarAbout;
    private TextView mCurrentTvRecording;
    private TextView mDayMaxTvRecording;
    private TextView mDayMinTvRecording;
    private RadioButton mInRadioRecording;
    private RadioButton mOutRadioRecording;
    private NoScrollViewPager mVpRecording;
    private TextView mPurhSubmitRecording;
    private TextView mSellSubmitRecording;
    private TrandRecorAdapter recorAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_trand_recording;
    }

    @Override
    protected void initData() {
        ImmersionBar.with(this).statusBarView(R.id.status_bar).statusBarDarkFont(true).init();
        RecordingFragment purhFragment = new RecordingFragment();
        Bundle purhBundle = new Bundle();
        purhBundle.putInt("transType", 1);
        purhBundle.putInt("pairId", data.getPairId());
        purhFragment.setArguments(purhBundle);
        mFragment.add(purhFragment);
        RecordingFragment sellFragment = new RecordingFragment();
        Bundle sellBundle = new Bundle();
        sellBundle.putInt("transType", 2);
        sellBundle.putInt("pairId", data.getPairId());
        sellFragment.setArguments(sellBundle);
        mFragment.add(sellFragment);
        recorAdapter = new TrandRecorAdapter(getSupportFragmentManager(), mFragment);
        mVpRecording.setAdapter(recorAdapter);
        mTitleTrand.setText(data.getPair());
        mInRadioRecording.setText("购买" + data.getTokenName());
        mOutRadioRecording.setText("出售" + data.getTokenName());
        String subTitle = data.getPair().substring(0, data.getPair().indexOf("/"));
        SPUtils.getInstance().put(RECORDING_UNIT, subTitle);
    }

    @Override
    protected void initView() {
        mFragment = new ArrayList<>();
        data = getIntent().getParcelableExtra("data");
        mTitleTrand = findViewById(R.id.trand_title);
        mBackTrand = findViewById(R.id.trand_back);
        mBackTrand.setOnClickListener(this);
        mHistroyTrand = findViewById(R.id.trand_histroy);
        mHistroyTrand.setOnClickListener(this);
        mToolbarAbout = findViewById(R.id.about_toolbar);
        mCurrentTvRecording = findViewById(R.id.recording_current_tv);
        mDayMaxTvRecording = findViewById(R.id.recording_day_max_tv);
        mDayMinTvRecording = findViewById(R.id.recording_day_min_tv);
        mInRadioRecording = findViewById(R.id.recording_in_radio);
        mInRadioRecording.setOnClickListener(this);
        mOutRadioRecording = findViewById(R.id.recording_out_radio);
        mOutRadioRecording.setOnClickListener(this);
        mVpRecording = findViewById(R.id.recording_vp);
        mPurhSubmitRecording = findViewById(R.id.recording_purh_submit);
        mPurhSubmitRecording.setOnClickListener(this);
        mSellSubmitRecording = findViewById(R.id.recording_sell_submit);
        mSellSubmitRecording.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.trand_back:
                // TODO 18/12/13
                finish();
                break;
            case R.id.trand_histroy:
                // TODO 18/12/13
                break;
            case R.id.recording_in_radio:
                // TODO 18/12/13
                mVpRecording.setCurrentItem(0);
                break;
            case R.id.recording_out_radio:
                // TODO 18/12/13
                mVpRecording.setCurrentItem(1);
                break;
            case R.id.recording_purh_submit:
                // TODO 18/12/13
                intent.setClass(this, TrandPurhAndSellActivity.class);
                intent.putExtra("title", "出售" + data.getTokenName());
                intent.putExtra("data", data);
                intent.putExtra("type", 2);
                startActivity(intent);
                break;
            case R.id.recording_sell_submit:
                // TODO 18/12/13
                intent.setClass(this, TrandPurhAndSellActivity.class);
                intent.putExtra("title", "购买" + data.getTokenName());
                intent.putExtra("data", data);
                intent.putExtra("type", 1);
                startActivity(intent);
                break;
        }
    }
}
