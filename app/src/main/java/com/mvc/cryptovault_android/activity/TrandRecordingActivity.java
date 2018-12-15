package com.mvc.cryptovault_android.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
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
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.gyf.barlibrary.ImmersionBar;
import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.adapter.TrandRecorAdapter;
import com.mvc.cryptovault_android.api.ApiStore;
import com.mvc.cryptovault_android.base.BaseActivity;
import com.mvc.cryptovault_android.bean.KLineBean;
import com.mvc.cryptovault_android.bean.TrandChildBean;
import com.mvc.cryptovault_android.fragment.RecordingFragment;
import com.mvc.cryptovault_android.utils.RetrofitUtils;
import com.mvc.cryptovault_android.utils.RxHelper;
import com.mvc.cryptovault_android.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

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
        mChartRecording.setViewPortOffsets(0, 0, 0, 0);
        mChartRecording.setBackgroundColor(Color.rgb(104, 241, 175));

        // no description text
        mChartRecording.getDescription().setEnabled(false);

        // enable touch gestures
        mChartRecording.setTouchEnabled(true);

        // enable scaling and dragging
        mChartRecording.setDragEnabled(true);
        mChartRecording.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChartRecording.setPinchZoom(false);

        mChartRecording.setDrawGridBackground(false);
        mChartRecording.setMaxHighlightDistance(300);
        XAxis x = mChartRecording.getXAxis();
        x.setEnabled(false);

        YAxis y = mChartRecording.getAxisLeft();
        y.setLabelCount(6, false);
        y.setTextColor(Color.WHITE);
        y.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        y.setDrawGridLines(false);
        y.setAxisLineColor(Color.WHITE);
        mChartRecording.getAxisRight().setEnabled(false);

        mChartRecording.getLegend().setEnabled(false);

        mChartRecording.animateXY(2000, 2000);

        // don't forget to refresh the drawing
        mChartRecording.invalidate();
        initLineChartData();
    }

    @SuppressLint("CheckResult")
    private void initLineChartData() {
        RetrofitUtils.client(ApiStore.class).getKLine(getToken(), data.getPairId())
                .compose(RxHelper.rxSchedulerHelper())
                .subscribe(updateBean -> initLineChart(updateBean), throwable -> {
                    LogUtils.e("TrandRecordingActivity", throwable.getMessage());
                });

    }

    private void initLineChart(KLineBean updateBean) {
        LineDataSet dataSetByIndex;
        ArrayList<Entry> values = new ArrayList<>();
        List<Long> timeX = updateBean.getData().getTimeX();
        List<Float> valueY = updateBean.getData().getValueY();
//        values.add(new Entry(0, 3));
//        for (int i = timeX.size() - 200; i < timeX.size(); i++) {
//            values.add(new Entry(timeX.get(i), valueY.get(i)));
//        }
//        values.add(new Entry(timeX.get(timeX.size() - 1), 8));
        for (int i = 0; i < 200; i++) {
            float val = (float) (Math.random() * 1) + 19;
            values.add(new Entry(i, val));
        }
        dataSetByIndex = new LineDataSet(values, "kline");
        dataSetByIndex.setValues(values);
        dataSetByIndex.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        dataSetByIndex.setCubicIntensity(0.2f);
        dataSetByIndex.setDrawFilled(true);
        dataSetByIndex.setDrawCircles(false);
        dataSetByIndex.setLineWidth(0.5f);
        dataSetByIndex.setCircleRadius(0.8f);
        dataSetByIndex.setCircleColor(Color.BLUE);
        dataSetByIndex.setHighLightColor(Color.RED);
        dataSetByIndex.setColor(Color.YELLOW);
        dataSetByIndex.setFillColor(Color.GREEN);
        dataSetByIndex.setFillAlpha(100);
        dataSetByIndex.setDrawHorizontalHighlightIndicator(false);
        dataSetByIndex.setFillFormatter(new IFillFormatter() {
            @Override
            public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                return mChartRecording.getAxisLeft().getAxisMinimum();
            }
        });
        LineData data = new LineData(dataSetByIndex);
        mChartRecording.setData(data);
        mChartRecording.invalidate();
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
