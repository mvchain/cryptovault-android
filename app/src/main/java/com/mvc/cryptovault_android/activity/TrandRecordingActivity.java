package com.mvc.cryptovault_android.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.highlight.ChartHighlighter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.gyf.barlibrary.ImmersionBar;
import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.adapter.TrandRecorAdapter;
import com.mvc.cryptovault_android.api.ApiStore;
import com.mvc.cryptovault_android.base.BaseActivity;
import com.mvc.cryptovault_android.bean.KLineBean;
import com.mvc.cryptovault_android.bean.RecorBean;
import com.mvc.cryptovault_android.bean.TrandChildBean;
import com.mvc.cryptovault_android.fragment.RecordingFragment;
import com.mvc.cryptovault_android.utils.RetrofitUtils;
import com.mvc.cryptovault_android.utils.RxHelper;
import com.mvc.cryptovault_android.utils.TextUtils;
import com.mvc.cryptovault_android.view.NoScrollViewPager;
import com.mvc.cryptovault_android.view.PopMarkerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.mvc.cryptovault_android.common.Constant.SP.RECORDING_TYPE;
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
    private LinearLayout mTimeGroup;
    private TrandRecorAdapter recorAdapter;
    private String recordingType;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_trand_recording;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initData() {
        ImmersionBar.with(this).statusBarView(R.id.status_bar).statusBarDarkFont(true).init();
        RecordingFragment sellFragment = new RecordingFragment();
        Bundle sellBundle = new Bundle();
        sellBundle.putInt("transType", 2);
        sellBundle.putInt("transionType", 1);
        sellBundle.putInt("pairId", data.getPairId());
        sellFragment.setArguments(sellBundle);
        mFragment.add(sellFragment);
        RecordingFragment purhFragment = new RecordingFragment();
        Bundle purhBundle = new Bundle();
        purhBundle.putInt("transType", 1);
        purhBundle.putInt("transionType", 2); //如果获取的是购买挂单列表，那id就是
        purhBundle.putInt("pairId", data.getPairId());
        purhFragment.setArguments(purhBundle);
        mFragment.add(purhFragment);
        recorAdapter = new TrandRecorAdapter(getSupportFragmentManager(), mFragment);
        mVpRecording.setAdapter(recorAdapter);
        mTitleTrand.setText(data.getPair());
        String subTitle = data.getPair().substring(0, data.getPair().indexOf("/"));
        SPUtils.getInstance().put(RECORDING_UNIT, subTitle);
        mChartRecording.setDragEnabled(false);
        mChartRecording.setScaleEnabled(false);
        mChartRecording.setDoubleTapToZoomEnabled(false);
        mChartRecording.setViewPortOffsets(0, 0, 0, 0);
        mChartRecording.setBackgroundColor(Color.WHITE);
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
        y.setLabelCount(7, false);
        y.setTextColor(Color.BLACK);
        y.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        y.setDrawGridLines(false);
        y.setTextSize(8);
        y.setAxisLineColor(Color.BLACK);
        mChartRecording.getAxisRight().setEnabled(false);
        mChartRecording.getAxisLeft().setEnabled(false);
        mChartRecording.getLegend().setEnabled(false);
        mChartRecording.animateXY(2000, 2000);
        // don't forget to refresh the drawing
        mChartRecording.setScaleYEnabled(false);
        mChartRecording.setScaleXEnabled(false);
        initLineChartData();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("CheckResult")
    private void initLineChartData() {
        RetrofitUtils.client(ApiStore.class).getKLine(getToken(), data.getPairId())
                .compose(RxHelper.rxSchedulerHelper())
                .subscribe(updateBean -> initLineChart(updateBean), throwable -> {
                    LogUtils.e("TrandRecordingActivity", throwable.getMessage());
                });

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initLineChart(KLineBean updateBean) {
//        recording_current_tv
        int currentSize = updateBean.getData().getValueY().size();
        long currentTime = updateBean.getData().getTimeX().get(currentSize - 1);
        long dayTime = currentTime - (24 * 60 * 60 * 1000);
        Float lastPrice = updateBean.getData().getValueY().get(currentSize - 1);
        float max = 0;
        float min = lastPrice;
        for (int i = 0; i < 7; i++) {
            TextView timeView = getTimeView(currentTime);
            currentTime -= (24 * 60 * 60 * 1000);
            mTimeGroup.addView(timeView, 0);
        }
        mCurrentTvRecording.setText(TextUtils.doubleToFour(lastPrice) + " " + recordingType);
        LineDataSet dataSetByIndex;
        ArrayList<Entry> values = new ArrayList<>();
        List<Long> timeX = updateBean.getData().getTimeX();
        List<Float> valueY = updateBean.getData().getValueY();
        for (int i = 0; i < timeX.size(); i++) {
            values.add(new Entry(timeX.get(i), valueY.get(i)));
            if (timeX.get(i) >= dayTime) {
                max = Math.max(max, valueY.get(i));
                min = Math.min(min, valueY.get(i));
            }
        }
        mDayMaxTvRecording.setText(TextUtils.doubleToFour(max) + " " + recordingType);
        mDayMinTvRecording.setText(TextUtils.doubleToFour(min) + " " + recordingType);
        dataSetByIndex = new LineDataSet(values, "kline");
        dataSetByIndex.setValues(values);
        dataSetByIndex.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataSetByIndex.setCubicIntensity(0.2f);
        dataSetByIndex.setDrawFilled(true);
        dataSetByIndex.setFillDrawable(getDrawable(R.drawable.shape_linechart_fill));
        dataSetByIndex.setDrawCircles(false);
        dataSetByIndex.setLineWidth(0.5f);
        dataSetByIndex.setCircleRadius(1f);
        dataSetByIndex.setCircleColor(Color.BLUE);
        dataSetByIndex.setHighLightColor(Color.RED);
        dataSetByIndex.setColor(Color.parseColor("#99007AFF"));
        dataSetByIndex.setFillAlpha(100);
        dataSetByIndex.setDrawHorizontalHighlightIndicator(false);
        dataSetByIndex.setFillFormatter((dataSet, dataProvider) -> mChartRecording.getAxisLeft().getAxisMinimum());
        dataSetByIndex.setValueFormatter((value, entry, dataSetIndex, viewPortHandler) -> "");
        LineData data = new LineData(dataSetByIndex);
        mChartRecording.setData(data);
//        mChartRecording.setVisibleXRangeMaximum(30);
//        mChartRecording.zoom(25, 1f, 0, 0);
        PopMarkerView popMarkerView = new PopMarkerView(this, R.layout.layout_chart_marker);
        popMarkerView.setChartView(mChartRecording);
        mChartRecording.setMarker(popMarkerView);
//        mChartRecording.moveViewToX(timeX.size() - 1);
        mChartRecording.invalidate();
    }

    @Override
    protected void initView() {
        mFragment = new ArrayList<>();
        data = getIntent().getParcelableExtra("data");
        recordingType = SPUtils.getInstance().getString(RECORDING_TYPE);
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
        mTimeGroup = findViewById(R.id.time_group);
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
                intent.putExtra("allprice_unit", data.getPair().substring(data.getPair().indexOf("/") + 1, data.getPair().length()));
                intent.putExtra("unit_price", data.getPair().substring(0, data.getPair().indexOf("/")));
                intent.putExtra("data", data);
                intent.putExtra("type", 2);
                startActivity(intent);
                break;
            case R.id.recording_sell_submit:
                // TODO 18/12/13
                intent.setClass(this, TrandPurhAndSellActivity.class);
                intent.putExtra("title", "购买" + data.getTokenName());
                intent.putExtra("unit_price", data.getPair().substring(data.getPair().indexOf("/") + 1, data.getPair().length()));
                intent.putExtra("allprice_unit", data.getPair().substring(data.getPair().indexOf("/") + 1, data.getPair().length()));
                intent.putExtra("data", data);
                intent.putExtra("type", 1);
                startActivity(intent);
                break;
        }
    }

    public void startPurhActivity(int transType, int id, RecorBean.DataBean recorBean) {
        Intent intent = new Intent(this, TrandPurhAndSellItemActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("data", data);
        intent.putExtra("recorBean", recorBean);
        intent.putExtra("type", transType);
        LogUtils.e("TrandRecordingActivity", "transType:" + transType);
//        unitPrice
        intent.putExtra("title", data.getTokenName());
        if (transType == 1) {
            intent.putExtra("unit_price", data.getPair().substring(data.getPair().indexOf("/") + 1, data.getPair().length()));
        } else {
            intent.putExtra("unit_price", data.getPair().substring(0, data.getPair().indexOf("/")));
        }
        intent.putExtra("allprice_unit", data.getPair().substring(data.getPair().indexOf("/") + 1, data.getPair().length()));
        startActivity(intent);
    }

    private TextView getTimeView(long time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd");
        SimpleDateFormat tes = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        TextView timeView = new TextView(this);
        timeView.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1);
        timeView.setLayoutParams(params);
        timeView.setTextSize(12);
        timeView.setText(dateFormat.format(new Date(time)));
        return timeView;
    }
}
