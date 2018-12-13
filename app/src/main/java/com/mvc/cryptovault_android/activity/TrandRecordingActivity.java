package com.mvc.cryptovault_android.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.adapter.TrandRecorAdapter;
import com.mvc.cryptovault_android.base.BaseActivity;
import com.mvc.cryptovault_android.fragment.RecordingFragment;
import com.mvc.cryptovault_android.view.NoScrollViewPager;

import java.util.ArrayList;

public class TrandRecordingActivity extends BaseActivity implements View.OnClickListener {
    private ArrayList<Fragment> mFragment;
    private int pairId;
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
        purhBundle.putInt("pairId", pairId);
        purhFragment.setArguments(purhBundle);
        mFragment.add(purhFragment);
        RecordingFragment sellFragment = new RecordingFragment();
        Bundle sellBundle = new Bundle();
        sellBundle.putInt("transType", 2);
        sellBundle.putInt("pairId", pairId);
        sellFragment.setArguments(sellBundle);
        mFragment.add(sellFragment);
        recorAdapter = new TrandRecorAdapter(getSupportFragmentManager(),mFragment);
        mVpRecording.setAdapter(recorAdapter);
    }

    @Override
    protected void initView() {
        mFragment = new ArrayList<>();
        pairId = getIntent().getIntExtra("pairId", 0);
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
                break;
            case R.id.recording_sell_submit:
                // TODO 18/12/13
                break;
        }
    }
}
