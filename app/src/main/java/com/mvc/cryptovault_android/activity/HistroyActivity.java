package com.mvc.cryptovault_android.activity;

import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.base.BaseMVPActivity;
import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.contract.HistroyContract;
import com.mvc.cryptovault_android.view.NoScrollViewPager;

import java.util.List;

public class HistroyActivity extends BaseMVPActivity<HistroyContract.HistroyPrecenter> implements HistroyContract.IHistroyView, View.OnClickListener {
    private View mBarStatus;
    private ImageView mBackHis;
    private ImageView mQcodeHis;
    private TextView mPriceHis;
    private TextView mTypeHis;
    private TextView mActualHis;
    private TabLayout mTabHis;
    private NoScrollViewPager mVpHis;
    private TextView mSubHis;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mBarStatus = findViewById(R.id.status_bar);
        mBackHis = findViewById(R.id.his_back);
        mQcodeHis = findViewById(R.id.his_qcode);
        mPriceHis = findViewById(R.id.his_price);
        mTypeHis = findViewById(R.id.his_type);
        mActualHis = findViewById(R.id.his_actual);
        mTabHis = findViewById(R.id.his_tab);
        mVpHis = findViewById(R.id.his_vp);
        mSubHis = findViewById(R.id.his_sub);
        mQcodeHis.setOnClickListener(this);
        mBackHis.setOnClickListener(this);
        mTypeHis.setOnClickListener(this);
        mSubHis.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_histroy;
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void startActivity() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.his_back:
                // TODO 18/11/29
                break;
            case R.id.his_qcode:
                // TODO 18/11/29
                break;
            case R.id.his_type:
                // TODO 18/11/29
                break;
            case R.id.his_sub:
                // TODO 18/11/29
                break;
        }
    }

    @Override
    public void showSuccess(List<String> msgs) {

    }
}
