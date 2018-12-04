package com.mvc.cryptovault_android.activity;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.KeyboardUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.adapter.rvAdapter.IncreaseAdapter;
import com.mvc.cryptovault_android.base.BaseMVPActivity;
import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.bean.IncreaseBean;
import com.mvc.cryptovault_android.contract.IncreaseContract;
import com.mvc.cryptovault_android.listener.EditTextChange;
import com.mvc.cryptovault_android.presenter.IncreasePresenter;
import com.mvc.cryptovault_android.view.RuleRecyclerLines;

import java.util.ArrayList;
import java.util.List;

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
    private ArrayList<IncreaseBean> mBean;
    private ArrayList<IncreaseBean> mSearch;
    private IncreaseAdapter allIncreaseAdapter;
    private IncreaseAdapter searchIncreaseAdapter;

    @Override
    protected void initMVPData() {
        allIncreaseAdapter = new IncreaseAdapter(R.layout.item_increase_rv, mBean);
        searchIncreaseAdapter = new IncreaseAdapter(R.layout.item_increase_rv, mSearch);
        mRvIncrease.addItemDecoration(new RuleRecyclerLines(this, RuleRecyclerLines.HORIZONTAL_LIST, 1));
        mRvIncrease.setAdapter(allIncreaseAdapter);
        mSerachRvIncrease.addItemDecoration(new RuleRecyclerLines(this, RuleRecyclerLines.HORIZONTAL_LIST, 1));
        mSerachRvIncrease.setAdapter(searchIncreaseAdapter);
        mEditIncrease.addTextChangedListener(new EditTextChange() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchTv = s.toString();
                if (searchTv.equals("")) {
                    mSerachNullIncrease.setVisibility(View.GONE);
                    mSerachRvIncrease.setVisibility(View.GONE);
                    mRvIncrease.setVisibility(View.VISIBLE);
                } else {
                    mPresenter.getCurrencySerach(searchTv);
                }
            }
        });
        mPresenter.getCurrencyAll();
    }

    @Override
    protected void initMVPView() {
        mBean = new ArrayList<>();
        mSearch = new ArrayList<>();
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
        mRvIncrease.setLayoutManager(new LinearLayoutManager(this));
        mSerachRvIncrease.setLayoutManager(new LinearLayoutManager(this));
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

    public void startActivity() {

    }

    @Override
    public BasePresenter initPresenter() {
        return IncreasePresenter.newIntance();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.increase_back:
                // TODO 18/12/03
                finish();
                break;
            case R.id.increase_serach:
                // TODO 18/12/03
                isSerach = !isSerach;
                if (isSerach) {
                    mSerachIncrease.setImageDrawable(getDrawable(R.drawable.cancel_icon_black));
                    mEditIncrease.setVisibility(View.VISIBLE);
                    mTitleIncrease.setVisibility(View.GONE);
                    mEditIncrease.setFocusable(true);
                    mEditIncrease.requestFocus();
                    KeyboardUtils.toggleSoftInput();
                } else {
                    mSerachIncrease.setImageDrawable(getDrawable(R.drawable.serch_icon_black));
                    mEditIncrease.setVisibility(View.GONE);
                    mTitleIncrease.setVisibility(View.VISIBLE);
//                    在关闭搜索框的时候搜索内容也关闭掉
                    mSerachNullIncrease.setVisibility(View.GONE);
                    mSerachRvIncrease.setVisibility(View.GONE);
                    mRvIncrease.setVisibility(View.VISIBLE);
                    KeyboardUtils.toggleSoftInput();
                }
                break;
        }
    }

    @Override
    public void showCurrency(List<IncreaseBean> beanList) {
        mBean.clear();
        mBean.addAll(beanList);
        mSerachNullIncrease.setVisibility(View.GONE);
        mSerachRvIncrease.setVisibility(View.GONE);
        mRvIncrease.setVisibility(View.VISIBLE);
        allIncreaseAdapter.notifyDataSetChanged();
    }

    @Override
    public void showSearchCurrency(List<IncreaseBean> beanList) {
        mSearch.clear();
        mSearch.addAll(beanList);
        mSerachNullIncrease.setVisibility(View.GONE);
        mSerachRvIncrease.setVisibility(View.VISIBLE);
        mRvIncrease.setVisibility(View.GONE);
        searchIncreaseAdapter.notifyDataSetChanged();
    }

    @Override
    public void showNull() {
        mSerachNullIncrease.setVisibility(View.VISIBLE);
        mSerachRvIncrease.setVisibility(View.GONE);
        mRvIncrease.setVisibility(View.GONE);
    }
}
