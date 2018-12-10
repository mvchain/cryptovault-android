package com.mvc.cryptovault_android.activity;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.base.BaseMVPActivity;
import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.contract.TogeHistroyContract;
import com.mvc.cryptovault_android.presenter.TogeHistroyPresenter;

public class TogeHistroyActivity extends BaseMVPActivity<TogeHistroyContract.TogeHistroyPresenter> implements TogeHistroyContract.ITogeHisView, View.OnClickListener {
    private ImageView mBackTogehis;
    private TextView mTitleTogehis;
    private EditText mEditTogehis;
    private ImageView mSerachTogehis;
    private RecyclerView mRvTogehis;
    private RecyclerView mSerachRvTogehis;
    private TextView mSerachNullTogehis;

    @Override
    protected void initMVPData() {

    }

    @Override
    protected void initMVPView() {
        ImmersionBar.with(this).statusBarView(R.id.status_bar).statusBarDarkFont(true).init();
        mBackTogehis = findViewById(R.id.togehis_back);
        mTitleTogehis = findViewById(R.id.togehis_title);
        mEditTogehis = findViewById(R.id.togehis_edit);
        mSerachTogehis = findViewById(R.id.togehis_serach);
        mRvTogehis = findViewById(R.id.togehis_rv);
        mSerachRvTogehis = findViewById(R.id.togehis_serach_rv);
        mSerachNullTogehis = findViewById(R.id.togehis_serach_null);
        mBackTogehis.setOnClickListener(this);
        mSerachTogehis.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_togehis;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {


    }

    @Override
    public void startActivity() {

    }

    @Override
    public BasePresenter initPresenter() {
        return TogeHistroyPresenter.newIntance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.togehis_back:
                // TODO 18/12/07
                finish();
                break;
            case R.id.togehis_serach:
                // TODO 18/12/07
                break;
        }
    }
}
