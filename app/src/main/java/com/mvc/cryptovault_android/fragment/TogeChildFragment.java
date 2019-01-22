package com.mvc.cryptovault_android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
    private boolean isRefresh = false;
    private boolean createCarryOut;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initView() {
        createCarryOut = true;
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
        initRecyclerLoadmore();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mItemSwipHis.post(() -> mItemSwipHis.setRefreshing(false));
    }

    @Override
    protected void initData() {
        super.initData();
        isRefresh = true;
        mPresenter.getComingList(10, 0, projectType, 0);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventRefresh(TogeFragmentEvent fragmentEvent) {
        mData.clear();
        isRefresh = true;
        mPresenter.getComingList(10, 0, projectType, 0);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_toge_list_rv;
    }

    @Override
    public BasePresenter initPresenter() {
        return TogeChildPresenter.newIntance();
    }

    public void refresh() {
        mItemSwipHis.post(() -> mItemSwipHis.setRefreshing(true));
        isRefresh = true;
        mPresenter.getComingList(10, 0, projectType, 0);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && createCarryOut) {
            refresh();
        }
    }

    @Override
    public void showSuccess(List<TogeBean.DataBean> msgs) {
        isRefresh = false;
        mData.clear();
        mItemSwipHis.post(() -> mItemSwipHis.setRefreshing(false));
        mChildRvToge.setVisibility(View.VISIBLE);
        mNullData.setVisibility(View.GONE);
        mData.addAll(msgs);
        togeChildAdapter.notifyDataSetChanged();
    }

    @Override
    public void showNull() {
        isRefresh = false;
        mItemSwipHis.post(() -> mItemSwipHis.setRefreshing(false));
        mChildRvToge.setVisibility(View.GONE);
        mNullData.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void initRecyclerLoadmore() {
        mChildRvToge.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                    if (lastVisibleItemPosition + 1 == togeChildAdapter.getItemCount() && togeChildAdapter.getItemCount() >= 10 && !isRefresh) {
                        int projectId = mData.get(mData.size() - 1).getProjectId();
                        mPresenter.getComingList(10, projectId, projectType, 1);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

            }
        });
    }
}
