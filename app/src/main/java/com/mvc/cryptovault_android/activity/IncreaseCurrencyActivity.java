package com.mvc.cryptovault_android.activity;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
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
    private boolean isSerach = false;

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
        ImmersionBar.with(this).statusBarView(mBarStatus).statusBarDarkFont(true).init();
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.increase_back:
                // TODO 18/12/03
                break;
            case R.id.increase_serach:
                // TODO 18/12/03
                isSerach = !isSerach;
                if(isSerach){
                    mSerachIncrease.setImageDrawable(getDrawable(R.drawable.cancel_icon_black));
                    mEditIncrease.setVisibility(View.VISIBLE);
                    mTitleIncrease.setVisibility(View.GONE);
                }else{
                    mSerachIncrease.setImageDrawable(getDrawable(R.drawable.serch_icon_black));
                    mEditIncrease.setVisibility(View.GONE);
                    mTitleIncrease.setVisibility(View.VISIBLE);
                }
                break;
            default:
                break;
        }
    }
}
