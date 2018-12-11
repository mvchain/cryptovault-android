package com.mvc.cryptovault_android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.activity.DetailActivity;
import com.mvc.cryptovault_android.adapter.rvAdapter.HistroyChildAdapter;
import com.mvc.cryptovault_android.base.BaseMVPFragment;
import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.bean.HistroyBean;
import com.mvc.cryptovault_android.contract.HistroyChildContract;
import com.mvc.cryptovault_android.presenter.HistroyChildPresenter;

import java.util.ArrayList;
import java.util.List;

public class HistroyChildFragment extends BaseMVPFragment<HistroyChildContract.HistroyChildPrecenter> implements HistroyChildContract.IHistroyChildView {
    private RecyclerView mRvChild;
    private ImageView mDataNull;
    private HistroyChildAdapter histroyChildAdapter;
    private List<HistroyBean.DataBean> mHisData;
    private int tokenId;
    private int action;
    private SwipeRefreshLayout mItemSwipHis;

    @Override
    protected void initView() {
        mHisData = new ArrayList<>();
        mRvChild = rootView.findViewById(R.id.child_rv);
        mDataNull = rootView.findViewById(R.id.data_null);
        mItemSwipHis = rootView.findViewById(R.id.his_item_swip);
        mRvChild.setLayoutManager(new LinearLayoutManager(activity));
        histroyChildAdapter = new HistroyChildAdapter(R.layout.item_histroy_child_list, mHisData);
        histroyChildAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.his_layout:
                    Intent intent = new Intent(activity, DetailActivity.class);
                    intent.putExtra("id",mHisData.get(position).getId());
                    startActivity(intent);
                    break;
            }
        });
        mRvChild.setAdapter(histroyChildAdapter);
        mItemSwipHis.setRefreshing(true);
        mItemSwipHis.setOnRefreshListener(this::refresh);
        getArgument();
        initRecyclerLoadmore();
    }


    private void initRecyclerLoadmore() {
        mRvChild.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager.getItemCount() > 10 && layoutManager.findLastVisibleItemPosition() >= layoutManager.getItemCount() * 0.7) {
                    switch (action) {
                        case 0:
                            mPresenter.getAll(getToken(), mHisData.get(mHisData.size() - 1).getId(), 10, tokenId, action, 1);
                            break;
                        case 1:
                            mPresenter.getOut(getToken(), mHisData.get(mHisData.size() - 1).getId(), 10, tokenId, action, 1);
                            break;
                        case 2:
                            mPresenter.getIn(getToken(), mHisData.get(mHisData.size() - 1).getId(), 10, tokenId, action, 1);
                            break;
                    }
                }
            }
        });
    }

    private void getArgument() {
        Bundle arguments = getArguments();
        tokenId = arguments.getInt("tokenId");
        action = arguments.getInt("action", 0);
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
        LogUtils.e("HistroyChildFragment", "action:" + action);
        switch (action) {
            case 0:
                mPresenter.getAll(getToken(), 0, 10, tokenId, action, 0);
                break;
            case 1:
                mPresenter.getOut(getToken(), 0, 10, tokenId, action, 0);
                break;
            case 2:
                mPresenter.getIn(getToken(), 0, 10, tokenId, action, 0);
                break;
        }
    }

    @Override
    public BasePresenter initPresenter() {
        return HistroyChildPresenter.newIntance();
    }

    @Override
    public void showSuccess(List<HistroyBean.DataBean> msgs) {
        mHisData.addAll(msgs);
        mDataNull.setVisibility(View.GONE);
        mRvChild.setVisibility(View.VISIBLE);
        mItemSwipHis.post(() -> mItemSwipHis.setRefreshing(false));
        histroyChildAdapter.notifyDataSetChanged();
    }

    @Override
    public void showNull() {
        mItemSwipHis.post(() -> mItemSwipHis.setRefreshing(false));
        if (mHisData.size() > 0) {
            Toast.makeText(context, "没有新的交易信息", Toast.LENGTH_SHORT).show();
        } else {
            mDataNull.setVisibility(View.VISIBLE);
            mRvChild.setVisibility(View.GONE);
        }
    }

    public void refresh() {
        mHisData.clear();
        switch (action) {
            case 0:
                mPresenter.getAll(getToken(), 0, 10, tokenId, action, 0);
                break;
            case 1:
                mPresenter.getOut(getToken(), 0, 10, tokenId, action, 0);
                break;
            case 2:
                mPresenter.getIn(getToken(), 0, 10, tokenId, action, 0);
                break;
        }

    }
}
