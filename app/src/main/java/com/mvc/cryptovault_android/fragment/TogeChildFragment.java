package com.mvc.cryptovault_android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.activity.CrowdfundingAppointmentActivity;
import com.mvc.cryptovault_android.adapter.rvAdapter.TogeChildAdapter;
import com.mvc.cryptovault_android.base.BaseMVPFragment;
import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.bean.TogeBean;
import com.mvc.cryptovault_android.contract.TogeChildContract;
import com.mvc.cryptovault_android.event.TogeFragmentEvent;
import com.mvc.cryptovault_android.presenter.TogeChildPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class TogeChildFragment extends BaseMVPFragment<TogeChildContract.TogeChildPresenter> implements TogeChildContract.ITogeView {
    private RecyclerView mChildRvToge;
    private ArrayList<TogeBean.DataBean> mData;
    private TogeChildAdapter togeChildAdapter;
    private Bundle arguments;
    private int projectType;
    private SwipeRefreshLayout mItemSwipHis;
    private ImageView mNullData;

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        mData = new ArrayList<>();
        mItemSwipHis = rootView.findViewById(R.id.his_item_swip);
        mNullData = rootView.findViewById(R.id.data_null);
        mChildRvToge = rootView.findViewById(R.id.child_rv);
        mChildRvToge.setLayoutManager(new LinearLayoutManager(activity));
        togeChildAdapter = new TogeChildAdapter(R.layout.item_together_child_list, mData);
        mChildRvToge.setAdapter(togeChildAdapter);
        mItemSwipHis.setOnRefreshListener(this::refresh);
        mItemSwipHis.post(() -> mItemSwipHis.setRefreshing(true));
        mItemSwipHis.setRefreshing(true);
        arguments = getArguments();
        projectType = arguments.getInt("projectType");
        togeChildAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.toge_child_submit:
                    TogeBean.DataBean dataBean = mData.get(position);
                    Intent intent = new Intent(activity, CrowdfundingAppointmentActivity.class);
                    intent.putExtra("databean", dataBean);
                    startActivity(intent);
                    break;
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        if (projectType == 0) {
            mPresenter.getComingSoon(getToken(), 1, 0, projectType, 0);
        } else if (projectType == 1) {
            mPresenter.getProcess(getToken(), 1, 0, projectType, 0);
        } else if (projectType == 2) {
            mPresenter.getToEnd(getToken(), 1, 0, projectType, 0);
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventRefresh(TogeFragmentEvent fragmentEvent){
        if (projectType == 0) {
            mPresenter.getComingSoon(getToken(), 1, 0, projectType, 0);
        } else if (projectType == 1) {
            mPresenter.getProcess(getToken(), 1, 0, projectType, 0);
        } else if (projectType == 2) {
            mPresenter.getToEnd(getToken(), 1, 0, projectType, 0);
        }
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_child_list_rv;
    }

    @Override
    public BasePresenter initPresenter() {
        return TogeChildPresenter.newIntance();
    }

    public void refresh() {
        mData.clear();
        mItemSwipHis.post(() -> mItemSwipHis.setRefreshing(true));
        if (projectType == 0) {
            mPresenter.getComingSoon(getToken(), 1, 0, projectType, 0);
        } else if (projectType == 1) {
            mPresenter.getProcess(getToken(), 1, 0, projectType, 0);
        } else if (projectType == 2) {
            mPresenter.getToEnd(getToken(), 1, 0, projectType, 0);
        }
    }

    @Override
    public void showSuccess(List<TogeBean.DataBean> msgs) {
        mItemSwipHis.post(() -> mItemSwipHis.setRefreshing(false));
        mChildRvToge.setVisibility(View.VISIBLE);
        mNullData.setVisibility(View.GONE);
        mData.addAll(msgs);
        togeChildAdapter.notifyDataSetChanged();
    }

    @Override
    public void showNull() {
        mItemSwipHis.post(() -> mItemSwipHis.setRefreshing(false));
        mChildRvToge.setVisibility(View.GONE);
        mNullData.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
