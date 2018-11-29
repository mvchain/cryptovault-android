package com.mvc.cryptovault_android.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.adapter.rvAdapter.TrandChildAdapter;
import com.mvc.cryptovault_android.base.BaseMVPFragment;
import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.contract.TrandChildContract;
import com.mvc.cryptovault_android.presenter.TrandChildPresenter;

import java.util.ArrayList;
import java.util.List;

public class TrandChildFragment extends BaseMVPFragment<TrandChildContract.TrandChildPresenter> implements TrandChildContract.ITrandChildView {
    private TextView mCurrencyTc;
    private TextView mPriceTc;
    private TextView mAmountOfIncreaseTc;
    private RecyclerView mRvTc;
    private SwipeRefreshLayout mSwipeTc;
    private Bundle arguments;
    private ArrayList<String> data;
    private TrandChildAdapter childAdapter;

    @Override
    protected void initView() {
        mCurrencyTc = rootView.findViewById(R.id.tc_currency);
        mPriceTc = rootView.findViewById(R.id.tc_price);
        mAmountOfIncreaseTc = rootView.findViewById(R.id.tc_amount_of_increase);
        mRvTc = rootView.findViewById(R.id.tc_rv);
        mSwipeTc = rootView.findViewById(R.id.tc_swipe);
        arguments = getArguments();
        data = new ArrayList<>();
        mRvTc.setLayoutManager(new LinearLayoutManager(activity));
        childAdapter = new TrandChildAdapter(R.layout.item_trand_child_list, data);
        mRvTc.setAdapter(childAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getVrt();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_trand_child;
    }

    @Override
    public BasePresenter initPresenter() {
        return TrandChildPresenter.newIntance();
    }

    @Override
    public void showSuccess(List<String> msgs) {
        data.addAll(msgs);
        childAdapter.notifyDataSetChanged();
    }
}
