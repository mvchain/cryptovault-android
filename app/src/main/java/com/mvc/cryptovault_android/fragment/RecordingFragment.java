package com.mvc.cryptovault_android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.activity.TrandPurhAndSellItemActivity;
import com.mvc.cryptovault_android.adapter.rvAdapter.RecorAdapter;
import com.mvc.cryptovault_android.base.BaseMVPFragment;
import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.bean.RecorBean;
import com.mvc.cryptovault_android.contract.RecordingContract;
import com.mvc.cryptovault_android.presenter.RecordingPresenter;

import java.util.ArrayList;
import java.util.List;

public class RecordingFragment extends BaseMVPFragment<RecordingContract.RecordingPresenter> implements RecordingContract.IRecordingView {
    private RecyclerView mRvChild;
    private ImageView mNullData;
    private SwipeRefreshLayout mItemSwipHis;
    private List<RecorBean.DataBean> bean;
    private RecorAdapter mRecorAdapter;
    private int transType;
    private int pairId;

    @Override
    protected void initView() {
        bean = new ArrayList<>();
        mRvChild = rootView.findViewById(R.id.child_rv);
        mNullData = rootView.findViewById(R.id.data_null);
        mItemSwipHis = rootView.findViewById(R.id.his_item_swip);
        mItemSwipHis.post(() -> mItemSwipHis.setRefreshing(true));
        mItemSwipHis.setOnRefreshListener(this::refresh);
        mRvChild.setLayoutManager(new LinearLayoutManager(activity));
        mRecorAdapter = new RecorAdapter(R.layout.item_recording_rv, bean);
        mRvChild.setAdapter(mRecorAdapter);
        mRecorAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.recording_layout:
                        // TODO 18/12/13
                        Intent intent = new Intent(activity,TrandPurhAndSellItemActivity.class);
//                        intent.putExtra("title", "购买" + data.getTokenName());
//                        intent.putExtra("data", data);
                        intent.putExtra("type", 1);
                        startActivity(intent);
                        break;
                }
            }
        });
        initArgument();
    }

    private void initArgument() {
        Bundle arguments = getArguments();
        transType = arguments.getInt("transType");
        pairId = arguments.getInt("pairId");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_child_list_rv;
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getRecorList(getToken(),0,10,pairId,transType,0);
    }

    @Override
    public BasePresenter initPresenter() {
        return RecordingPresenter.newIntance();
    }

    @Override
    public void showSuccess(List<RecorBean.DataBean> bean) {
        this.bean.addAll(bean);
        mItemSwipHis.post(() -> mItemSwipHis.setRefreshing(false));
        mRvChild.setVisibility(View.VISIBLE);
        mNullData.setVisibility(View.GONE);
        mRecorAdapter.notifyDataSetChanged();
    }

    @Override
    public void showNull() {
        mItemSwipHis.post(() -> mItemSwipHis.setRefreshing(false));
        if(bean.size()>0){
            mRvChild.setVisibility(View.VISIBLE);
            mNullData.setVisibility(View.GONE);
        }else{
            mRvChild.setVisibility(View.GONE);
            mNullData.setVisibility(View.VISIBLE);
        }
    }
    private void refresh(){
        bean.clear();
        mPresenter.getRecorList(getToken(),0,10,pairId,transType,0);
    }
}
