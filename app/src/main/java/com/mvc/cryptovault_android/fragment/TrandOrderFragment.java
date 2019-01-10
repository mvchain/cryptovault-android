package com.mvc.cryptovault_android.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.adapter.rvAdapter.TrandOrderAdapter;
import com.mvc.cryptovault_android.base.BaseMVPFragment;
import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.bean.TrandOrderBean;
import com.mvc.cryptovault_android.contract.ITrandOrderContract;
import com.mvc.cryptovault_android.event.TrandOrderEvent;
import com.mvc.cryptovault_android.presenter.OrderPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class TrandOrderFragment extends BaseMVPFragment<ITrandOrderContract.TrandOrderPresenter> implements ITrandOrderContract.ITrandOrderView {
    private List<TrandOrderBean.DataBean> dataBeans;
    private int status;
    private TrandOrderAdapter orderAdapter;
    private RecyclerView mRvOrder;
    private ImageView mNullOrder;
    private SwipeRefreshLayout mSwipOrder;
    private Dialog mHintDialog;
    private String pairId;
    private String transactionType;
    private boolean isRefresh = false;

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
    protected void initView() {
        dataBeans = new ArrayList<>();
        status = getArguments().getInt("status");
        pairId = getArguments().getString("pairId");
        transactionType = getArguments().getString("transactionType");
        mRvOrder = rootView.findViewById(R.id.order_rv);
        mNullOrder = rootView.findViewById(R.id.order_null);
        mSwipOrder = rootView.findViewById(R.id.order_swip);
        orderAdapter = new TrandOrderAdapter(R.layout.item_trand_order, dataBeans);
        mRvOrder.setAdapter(orderAdapter);
        mSwipOrder.setOnRefreshListener(() -> {
            isRefresh = true;
            mPresenter.getTrandOrder( 0, 10, pairId, status, transactionType, 0);
        });
        mSwipOrder.post(() -> mSwipOrder.setRefreshing(true));
        initRecyclerLoadmore();
    }

    private void initRecyclerLoadmore() {
        mRvOrder.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                    if (lastVisibleItemPosition + 1 == orderAdapter.getItemCount() && orderAdapter.getItemCount() >= 10 && !isRefresh) {
                        mPresenter.getTrandOrder( dataBeans.get(dataBeans.size() - 1).getId(), 10, pairId, status, transactionType, 1);
                    }
                }
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_trand_order;
    }

    @Override
    public void showSuccess(List<TrandOrderBean.DataBean> dataBeans) {
        if (isRefresh) {
            isRefresh = false;
            this.dataBeans.clear();
        }
        this.dataBeans.addAll(dataBeans);
        mSwipOrder.post(() -> mSwipOrder.setRefreshing(false));
        mNullOrder.setVisibility(View.INVISIBLE);
        mRvOrder.setVisibility(View.VISIBLE);
        orderAdapter.notifyDataSetChanged();
    }

    @Subscribe
    public void refresh(TrandOrderEvent orderEvent) {
        isRefresh = true;
        dataBeans.clear();
        orderAdapter.notifyDataSetChanged();
        if (orderEvent.getPariId() != null && orderEvent.getTransactionType() != null) {
            mPresenter.getTrandOrder( 0, 10, orderEvent.getPariId(), status, orderEvent.getTransactionType(), 0);
            pairId = orderEvent.getPariId();
            transactionType = orderEvent.getTransactionType();
        } else if (orderEvent.getPariId() != null) {
            mPresenter.getTrandOrder( 0, 10, orderEvent.getPariId(), status, transactionType, 0);
            pairId = orderEvent.getPariId();
        } else if (orderEvent.getTransactionType() != null) {
            mPresenter.getTrandOrder( 0, 10, pairId, status, orderEvent.getTransactionType(), 0);
            transactionType = orderEvent.getTransactionType();
        } else {
            mPresenter.getTrandOrder( 0, 10, pairId, status, transactionType, 0);
        }
    }

    @Override
    protected void initData() {
        super.initData();
        isRefresh = true;
        mPresenter.getTrandOrder( 0, 10, pairId, status, transactionType, 0);
    }

    @Override
    public void showNull() {
        isRefresh = false;
        mSwipOrder.post(() -> mSwipOrder.setRefreshing(false));
        if (dataBeans.size() > 0) {

        } else {
            mNullOrder.setVisibility(View.VISIBLE);
            mRvOrder.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public BasePresenter initPresenter() {
        return OrderPresenter.newIntance();
    }

}
