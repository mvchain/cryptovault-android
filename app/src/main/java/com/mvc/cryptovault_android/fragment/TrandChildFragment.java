package com.mvc.cryptovault_android.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.base.BaseMVPFragment;
import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.contract.VRTContract;

public class TrandChildVRTFragment extends BaseMVPFragment<VRTContract.VRTPresenter> implements VRTContract.IVRTView {
    private TextView mCurrencyTc;
    private TextView mPriceTc;
    private TextView mAmountOfIncreaseTc;
    private RecyclerView mRvTc;
    private SwipeRefreshLayout mSwipeTc;

    @Override
    protected void initView() {
        mCurrencyTc = rootView.findViewById(R.id.tc_currency);
        mPriceTc = rootView.findViewById(R.id.tc_price);
        mAmountOfIncreaseTc = rootView.findViewById(R.id.tc_amount_of_increase);
        mRvTc = rootView.findViewById(R.id.tc_rv);
        mSwipeTc = rootView.findViewById(R.id.tc_swipe);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_trand_child;
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }
}
