package com.mvc.cryptovault_android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.activity.TrandRecordingActivity;
import com.mvc.cryptovault_android.adapter.rvAdapter.TrandChildAdapter;
import com.mvc.cryptovault_android.base.BaseMVPFragment;
import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.bean.TrandChildBean;
import com.mvc.cryptovault_android.contract.TrandChildContract;
import com.mvc.cryptovault_android.event.TrandFragmentEvent;
import com.mvc.cryptovault_android.presenter.TrandChildPresenter;
import com.mvc.cryptovault_android.view.RuleRecyclerLines;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import static com.mvc.cryptovault_android.common.Constant.SP.RECORDING_TYPE;

public class TrandChildFragment extends BaseMVPFragment<TrandChildContract.TrandChildPresenter> implements TrandChildContract.ITrandChildView {
    private TextView mCurrencyTc;
    private TextView mPriceTc;
    private TextView mAmountOfIncreaseTc;
    private RecyclerView mRvTc;
    private SwipeRefreshLayout mSwipeTc;
    private Bundle arguments;
    private List<TrandChildBean.DataBean> data;
    private TrandChildAdapter childAdapter;
    private int pairType;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSwipeTc.post(() -> mSwipeTc.setRefreshing(false));
    }

    @Override
    protected void initView() {
        mCurrencyTc = rootView.findViewById(R.id.tc_currency);
        mPriceTc = rootView.findViewById(R.id.tc_price);
        mAmountOfIncreaseTc = rootView.findViewById(R.id.tc_amount_of_increase);
        mRvTc = rootView.findViewById(R.id.tc_rv);
        mSwipeTc = rootView.findViewById(R.id.tc_swipe);
        initArgument();
        data = new ArrayList<>();
        mRvTc.setLayoutManager(new LinearLayoutManager(activity));
        childAdapter = new TrandChildAdapter(R.layout.item_trand_child_list, data);
        childAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.trand_layout:
                    Intent intent = new Intent(activity, TrandRecordingActivity.class);
                    TrandChildBean.DataBean dataBean = data.get(position);
                    intent.putExtra("data", dataBean);
                    SPUtils.getInstance().put(RECORDING_TYPE, dataBean.getPair().substring(dataBean.getPair().indexOf("/") + 1, dataBean.getPair().length()));
                    startActivity(intent);
                    break;
            }
        });
        mRvTc.addItemDecoration(new RuleRecyclerLines(activity, RuleRecyclerLines.HORIZONTAL_LIST, 1));
        mRvTc.setAdapter(childAdapter);
    }

    private void initArgument() {
        arguments = getArguments();
        pairType = arguments.getInt("pairType");
        mSwipeTc.setOnRefreshListener(this::refresh);
        mSwipeTc.post(() -> mSwipeTc.setRefreshing(true));
    }

    @Subscribe()
    public void refreshRate(TrandFragmentEvent fragmentEvent) {
        if (childAdapter != null) {
            childAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void initData() {
        super.initData();
        if (pairType == 1) {
            mPresenter.getVrt(getToken(), pairType);
        } else {
            mPresenter.getBalanceTransactions(getToken(), pairType);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_child_trand;
    }

    @Override
    public BasePresenter initPresenter() {
        return TrandChildPresenter.newIntance();
    }

    @Override
    public void showSuccess(List<TrandChildBean.DataBean> msgs) {
        mSwipeTc.post(() -> mSwipeTc.setRefreshing(false));
        data.addAll(msgs);
        childAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError() {
        mSwipeTc.post(() -> mSwipeTc.setRefreshing(false));
    }

    @Override
    public void showNull() {
        mSwipeTc.post(() -> mSwipeTc.setRefreshing(false));
    }

    public void refresh() {
        data.clear();
        if (pairType == 1) {
            mPresenter.getVrt(getToken(), pairType);
        } else {
            mPresenter.getBalanceTransactions(getToken(), pairType);
        }
    }
}
