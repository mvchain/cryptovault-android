package com.mvc.cryptovault_android.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.base.BaseMVPActivity;
import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.bean.DetailBean;
import com.mvc.cryptovault_android.contract.DetailContract;
import com.mvc.cryptovault_android.presenter.DetailPresenter;

public class DetailActivity extends BaseMVPActivity<DetailContract.DetailPresenter> implements DetailContract.IDetailView, View.OnClickListener {
    private View mBarStatus;
    private ImageView mBackDetail;
    private ImageView mShareDetail;
    private ImageView mIconDetail;
    private TextView mTitleDetail;
    private TextView mTimeDetail;
    private TextView mPriceTitleDetail;
    private TextView mPriceContentDetail;
    private LinearLayout mPriceLayoutDetail;
    private TextView mFeesTitleDetail;
    private TextView mFeesContentDetail;
    private LinearLayout mFeesLayoutDetail;
    private TextView mColladdTitleDetail;
    private TextView mColladdContentDetail;
    private LinearLayout mCollLayoutDetail;
    private TextView mPayTitleDetail;
    private TextView mPayContentDetail;
    private LinearLayout mPayLayoutDetail;
    private TextView mHashTitleDetail;
    private TextView mHashContentDetail;
    private LinearLayout mHashLayoutDetail;
    private int id;

    @Override
    protected void initMVPData() {

    }

    @Override
    protected void initMVPView() {
        ImmersionBar.with(this).statusBarView(R.id.status_bar).statusBarDarkFont(true).init();
        mBarStatus = findViewById(R.id.status_bar);
        mBackDetail = findViewById(R.id.detail_back);
        mBackDetail.setOnClickListener(this);
        mShareDetail = findViewById(R.id.detail_share);
        mShareDetail.setOnClickListener(this);
        mIconDetail = findViewById(R.id.detail_icon);
        mTitleDetail = findViewById(R.id.detail_title);
        mTimeDetail = findViewById(R.id.detail_time);
        mPriceTitleDetail = findViewById(R.id.detail_price_title);
        mPriceContentDetail = findViewById(R.id.detail_price_content);
        mPriceLayoutDetail = findViewById(R.id.detail_price_layout);
        mFeesTitleDetail = findViewById(R.id.detail_fees_title);
        mFeesContentDetail = findViewById(R.id.detail_fees_content);
        mFeesLayoutDetail = findViewById(R.id.detail_fees_layout);
        mColladdTitleDetail = findViewById(R.id.detail_colladd_title);
        mColladdContentDetail = findViewById(R.id.detail_colladd_content);
        mCollLayoutDetail = findViewById(R.id.detail_coll_layout);
        mPayTitleDetail = findViewById(R.id.detail_pay_title);
        mPayContentDetail = findViewById(R.id.detail_pay_content);
        mPayLayoutDetail = findViewById(R.id.detail_pay_layout);
        mHashTitleDetail = findViewById(R.id.detail_hash_title);
        mHashContentDetail = findViewById(R.id.detail_hash_content);
        mHashLayoutDetail = findViewById(R.id.detail_hash_layout);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail;
    }

    @Override
    protected void initData() {
        id = getIntent().getIntExtra("id", -1);
        if (id != -1) {
            mPresenter.getDetailOnID(getToken(), id);
        }
    }

    @Override
    protected void initView() {

    }

    @Override
    public void startActivity() {

    }

    @Override
    public BasePresenter initPresenter() {
        return DetailPresenter.newIntance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.detail_back:
                // TODO 18/12/01
                finish();
                break;
            case R.id.detail_share:
                // TODO 18/12/01
                break;
        }
    }

    @Override
    public void showDetail(DetailBean bean) {
        DetailBean.DataBean data = bean.getData();
        int classify = data.getClassify();
        //区块链和转账
        if (classify == 0 || classify == 3) {
            
        } else if (classify == 1) { //订单交易

        } else if (classify == 2) { //众筹交易

        }

    }
}
