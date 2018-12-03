package com.mvc.cryptovault_android.activity;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.base.BaseMVPActivity;
import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.contract.IncreaseContract;

public class IncreaseCurrencyActivity extends BaseMVPActivity<IncreaseContract.IncreasePresenter> implements IncreaseContract.IIncreaseView, View.OnClickListener {
    private View mBarStatus;
    private ImageView mBackIncrease;
    private TextView mTitleIncrease;
    private EditText mEditIncrease;
    private ImageView mSerachIncrease;
    private RecyclerView mRvIncrease;
    private RecyclerView mSerachRvIncrease;
    private TextView mSerachNullIncrease;

    @Override
    protected void initMVPData() {

    }

    @Override
    protected void initMVPView() {
        mBarStatus = findViewById(R.id.status_bar);
        mBackIncrease = findViewById(R.id.increase_back);
        mBackIncrease.setOnClickListener(this);
        mTitleIncrease = findViewById(R.id.increase_title);
        mEditIncrease = findViewById(R.id.increase_edit);
        mSerachIncrease = findViewById(R.id.increase_serach);
        mSerachIncrease.setOnClickListener(this);
        mRvIncrease = findViewById(R.id.increase_rv);
        mSerachRvIncrease = findViewById(R.id.increase_serach_rv);
        mSerachNullIncrease = findViewById(R.id.increase_serach_null);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_increase;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
    }

    @Override
    public void showCurrency() {

    }

    @Override
    public void startActivity() {

    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.increase_back:
                // TODO 18/12/03
                break;
            case R.id.increase_serach:
                // TODO 18/12/03
                break;
            default:
                break;
        }
    }
}
