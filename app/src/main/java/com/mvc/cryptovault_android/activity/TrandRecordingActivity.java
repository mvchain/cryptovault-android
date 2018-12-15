package com.mvc.cryptovault_android.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.gyf.barlibrary.ImmersionBar;
import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.adapter.TrandRecorAdapter;
import com.mvc.cryptovault_android.api.ApiStore;
import com.mvc.cryptovault_android.base.BaseActivity;
import com.mvc.cryptovault_android.bean.TrandChildBean;
import com.mvc.cryptovault_android.bean.UpdateBean;
import com.mvc.cryptovault_android.fragment.RecordingFragment;
import com.mvc.cryptovault_android.utils.RetrofitUtils;
import com.mvc.cryptovault_android.utils.RxHelper;
import com.mvc.cryptovault_android.view.NoScrollViewPager;

import java.util.ArrayList;

import io.reactivex.functions.Consumer;

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
    private LineChart mChartRecording;
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
        mChartRecording.setDragEnabled(false);
        mChartRecording.setScaleEnabled(false);
        mChartRecording.setDoubleTapToZoomEnabled(false);
        initLineChartData();
    }

    @SuppressLint("CheckResult")
    private void initLineChartData() {
        RetrofitUtils.client(ApiStore.class).getKLine(getToken(), data.getPairId())
                .compose(RxHelper.rxSchedulerHelper())
                .subscribe(new Consumer<UpdateBean>() {
                    @Override
                    public void accept(UpdateBean updateBean) throws Exception {
//                        initLineChart(updateBean);
                    }
                }, throwable -> {
                    LogUtils.e("TrandRecordingActivity", throwable.getMessage());
                });
        LineDataSet dataSetByIndex = (LineDataSet) mChartRecording.getData().getDataSetByIndex(0);
        ArrayList<Entry> values = new ArrayList<>();

        LineData data = new LineData();

    }

    @Override
    protected void initView() {
        mFragment = new ArrayList<>();
        data = getIntent().getParcelableExtra("data");
        mTitleTrand = findViewById(R.id.trand_title);
        mBackTrand = findViewById(R.id.trand_back);
        mHistroyTrand = findViewById(R.id.trand_histroy);
        mToolbarAbout = findViewById(R.id.about_toolbar);
        mCurrentTvRecording = findViewById(R.id.recording_current_tv);
        mDayMaxTvRecording = findViewById(R.id.recording_day_max_tv);
        mDayMinTvRecording = findViewById(R.id.recording_day_min_tv);
        mInRadioRecording = findViewById(R.id.recording_in_radio);
        mOutRadioRecording = findViewById(R.id.recording_out_radio);
        mVpRecording = findViewById(R.id.recording_vp);
        mPurhSubmitRecording = findViewById(R.id.recording_purh_submit);
        mSellSubmitRecording = findViewById(R.id.recording_sell_submit);
        mChartRecording = findViewById(R.id.recording_chart);
        mBackTrand.setOnClickListener(this);
        mHistroyTrand.setOnClickListener(this);
        mInRadioRecording.setOnClickListener(this);
        mOutRadioRecording.setOnClickListener(this);
        mPurhSubmitRecording.setOnClickListener(this);
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
                intent.setClass(this, TrandOrderActivity.class);
                intent.putExtra("pairId", data.getPairId() + "");
                startActivity(intent);
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

    public void startPurhActivity(int transType) {
        Intent intent = new Intent(this, TrandPurhAndSellItemActivity.class);
        intent.putExtra("title", "购买" + data.getTokenName());
        intent.putExtra("data", data);
        intent.putExtra("type", transType);
        startActivity(intent);
    }
}
