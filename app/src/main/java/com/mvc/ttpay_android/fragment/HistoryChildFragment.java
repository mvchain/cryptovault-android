package com.mvc.ttpay_android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.LogUtils;
import com.mvc.ttpay_android.R;
import com.mvc.ttpay_android.activity.DetailActivity;
import com.mvc.ttpay_android.adapter.rvAdapter.HistoryChildAdapter;
import com.mvc.ttpay_android.base.BaseMVPFragment;
import com.mvc.ttpay_android.base.BasePresenter;
import com.mvc.ttpay_android.bean.HistoryBeanEvent;
import com.mvc.ttpay_android.bean.HistroyBean;
import com.mvc.ttpay_android.contract.IHistroyChildContract;
import com.mvc.ttpay_android.event.HistroyFragmentEvent;
import com.mvc.ttpay_android.presenter.HistroyChildPresenter;
import com.mvc.ttpay_android.view.RuleRecyclerLines;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class HistoryChildFragment extends BaseMVPFragment<IHistroyChildContract.HistroyChildPrecenter> implements IHistroyChildContract.IHistroyChildView {
    private RecyclerView mRvChild;
    private ImageView mDataNull;
    private HistoryChildAdapter historyChildAdapter;
    private List<HistroyBean.DataBean> mHisData;
    private int tokenId;
    private int action;
    private SwipeRefreshLayout mItemSwipeHis;
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
        mHisData = new ArrayList<>();
        mRvChild = rootView.findViewById(R.id.child_rv);
        mDataNull = rootView.findViewById(R.id.data_null);
        mItemSwipeHis = rootView.findViewById(R.id.his_item_swip);
        mRvChild.setLayoutManager(new LinearLayoutManager(activity));
        mRvChild.addItemDecoration(new RuleRecyclerLines(activity, RuleRecyclerLines.HORIZONTAL_LIST, 1));
        historyChildAdapter = new HistoryChildAdapter(R.layout.item_histroy_child_list, mHisData);
        historyChildAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.his_layout:
                    Intent intent = new Intent(activity, DetailActivity.class);
                    intent.putExtra("id", mHisData.get(position).getId());
                    startActivity(intent);
                    break;
            }
        });
        mRvChild.setAdapter(historyChildAdapter);
        mItemSwipeHis.setRefreshing(true);
        mItemSwipeHis.setOnRefreshListener(this::refresh);
        getArgument();
        initRecyclerLoadmore();
    }


    private void initRecyclerLoadmore() {
        mRvChild.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                    if (lastVisibleItemPosition + 1 == historyChildAdapter.getItemCount() && historyChildAdapter.getItemCount() >= 10 && !isRefresh) {
                        mPresenter.getAll(action, mHisData.get(mHisData.size() - 1).getId(), 10, tokenId, 0, 1);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

            }
        });
    }

    private void getArgument() {
        Bundle arguments = getArguments();
        tokenId = arguments.getInt("tokenId");
        action = arguments.getInt("action", 0);
        LogUtils.e("HistoryChildFragment", "action:" + action);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_child_list_rv;
    }

    @Override
    public void startActivity() {

    }

    @Override
    protected void initData() {
        super.initData();
        isRefresh = true;
        mPresenter.getAll(action, 0, 10, tokenId, 0, 0);
    }

    @Override
    public BasePresenter initPresenter() {
        return HistroyChildPresenter.newIntance();
    }

    @Override
    public void showSuccess(List<HistroyBean.DataBean> msgs) {
        if (isRefresh) {
            mHisData.clear();
            isRefresh = false;
        }
        mHisData.addAll(msgs);
        mDataNull.setVisibility(View.GONE);
        mRvChild.setVisibility(View.VISIBLE);
        mItemSwipeHis.post(() -> mItemSwipeHis.setRefreshing(false));
        historyChildAdapter.notifyDataSetChanged();
    }

    @Override
    public void showNull() {
        isRefresh = false;
        mItemSwipeHis.post(() -> mItemSwipeHis.setRefreshing(false));
        if (mHisData.size() > 0 && isRefresh) {
//            Toast.makeText(context, "没有新的交易信息", Toast.LENGTH_SHORT).show();
        } else if (mHisData.size() > 0 && !isRefresh) {
//            Toast.makeText(context, "没有更多交易信息", Toast.LENGTH_SHORT).show();
        } else {
            mDataNull.setVisibility(View.VISIBLE);
            mRvChild.setVisibility(View.GONE);
        }
    }

    public void refresh() {
        isRefresh = true;
        mPresenter.getAll(action, 0, 10, tokenId, 0, 0);
        EventBus.getDefault().post(new HistoryBeanEvent());
    }

    @Subscribe
    public void eventRefresh(HistroyFragmentEvent event) {
        isRefresh = true;
        mPresenter.getAll(action, 0, 10, tokenId, 0, 0);
    }
}
