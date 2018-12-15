package com.mvc.cryptovault_android.fragment;

import android.app.Dialog;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.mvc.cryptovault_android.view.DialogHelper;

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

    @Override
    protected void initView() {
        dataBeans = new ArrayList<>();
        status = getArguments().getInt("status");
        mRvOrder = rootView.findViewById(R.id.order_rv);
        mNullOrder = rootView.findViewById(R.id.order_null);
        mSwipOrder = rootView.findViewById(R.id.order_swip);
        orderAdapter = new TrandOrderAdapter(R.layout.item_trand_order, dataBeans);
        mRvOrder.setAdapter(orderAdapter);
        orderAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()){
                case R.id.order_item_submit:
                    mHintDialog = DialogHelper.getInstance().create(activity, "确认撤除 USDT/VRT的挂单?", viewId -> {
                        switch (viewId) {
                            case R.id.hint_cancle:
                                mHintDialog.dismiss();
                                break;
                            case R.id.hint_enter:
                                mHintDialog.dismiss();

                                break;
                        }
                    });
                    mHintDialog.show();
                    break;
            }
        });
        mSwipOrder.setOnRefreshListener(() -> {
            dataBeans.clear();
            mPresenter.getTrandOrder(getToken(), 0, 10, "", status, "", 0);
        });
        mSwipOrder.post(() -> mSwipOrder.setRefreshing(true));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_trand_order;
    }

    @Override
    public void showSuccess(List<TrandOrderBean.DataBean> dataBeans) {
        mSwipOrder.post(() -> mSwipOrder.setRefreshing(false));
        this.dataBeans.addAll(dataBeans);
        mNullOrder.setVisibility(View.INVISIBLE);
        mRvOrder.setVisibility(View.VISIBLE);
        orderAdapter.notifyDataSetChanged();
    }
    @Subscribe
    public void refresh(TrandOrderEvent orderEvent) {
        dataBeans.clear();
        mPresenter.getTrandOrder(getToken(), 0, 10, "", status, "", 0);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getTrandOrder(getToken(), 0, 10, "", status, "", 0);
    }

    @Override
    public void showNull() {
        mSwipOrder.post(() -> mSwipOrder.setRefreshing(false));
        mNullOrder.setVisibility(View.VISIBLE);
        mRvOrder.setVisibility(View.INVISIBLE);
    }

    @Override
    public BasePresenter initPresenter() {
        return OrderPresenter.newIntance();
    }
}
