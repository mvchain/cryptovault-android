package com.mvc.cryptovault_android.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.adapter.rvAdapter.TogeChildAdapter;
import com.mvc.cryptovault_android.base.BaseMVPFragment;
import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.contract.TogeChildContract;
import com.mvc.cryptovault_android.presenter.TogeChildPresenter;

import java.util.ArrayList;
import java.util.List;

public class TogeChildFragment extends BaseMVPFragment<TogeChildContract.TogeChildPresenter> implements TogeChildContract.ITogeView {
    private RecyclerView mChildRvToge;
    private ArrayList<String> strings;
    private TogeChildAdapter togeChildAdapter;

    @Override
    protected void initView() {
        strings = new ArrayList<>();
        mChildRvToge = rootView.findViewById(R.id.child_rv);
        mChildRvToge.setLayoutManager(new LinearLayoutManager(activity));
        togeChildAdapter = new TogeChildAdapter(R.layout.item_together_child_list, strings);
        mChildRvToge.setAdapter(togeChildAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getMsg();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_child_list_rv;
    }

    @Override
    public BasePresenter initPresenter() {
        return TogeChildPresenter.newIntance();
    }

    @Override
    public void showSuccess(List<String> msgs) {
        strings.addAll(msgs);
        togeChildAdapter.notifyDataSetChanged();
    }
}
