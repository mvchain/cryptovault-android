package com.mvc.cryptovault_android.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.base.BaseActivity;

public class CrowdfundingAppointmentActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mBackM;
    private TextView mTitleM;
    private LinearLayout mTitleLayoutM;
    private ImageView mInfoIconM;
    private TextView mInfoTitleM;
    private TextView mInfoMaxM;
    private TextView mInfoMinM;
    private TextView mInfoBlM;
    private RelativeLayout mInfoLayoutM;
    private EditText mBwPriceM;
    private TextView mPriceM;
    private TextView mAvailableM;
    private TextView mSubmitM;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_crewfunding;
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();

    }

    @Override
    protected void initView() {
        mBackM = findViewById(R.id.m_back);
        mTitleM = findViewById(R.id.m_title);
        mTitleLayoutM = findViewById(R.id.m_title_layout);
        mInfoIconM = findViewById(R.id.m_info_icon);
        mInfoTitleM = findViewById(R.id.m_info_title);
        mInfoMaxM = findViewById(R.id.m_info_max);
        mInfoMinM = findViewById(R.id.m_info_min);
        mInfoBlM = findViewById(R.id.m_info_bl);
        mInfoLayoutM = findViewById(R.id.m_info_layout);
        mBwPriceM = findViewById(R.id.m_bw_price);
        mPriceM = findViewById(R.id.m_price);
        mAvailableM = findViewById(R.id.m_available);
        mSubmitM = findViewById(R.id.m_submit);
        mBackM.setOnClickListener(this);
        mSubmitM.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.m_back:
                // TODO 18/12/12
                break;
            case R.id.m_submit:
                // TODO 18/12/12
                break;
        }
    }
}
