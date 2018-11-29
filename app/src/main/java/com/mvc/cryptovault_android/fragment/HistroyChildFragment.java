package com.mvc.cryptovault_android.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.adapter.rvAdapter.HistroyChildAdapter;
import com.mvc.cryptovault_android.base.BaseMVPFragment;
import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.contract.HistroyChildContract;
import com.mvc.cryptovault_android.presenter.HistroyChildPresenter;

import java.util.ArrayList;
import java.util.List;

public class HistroyChildFragment extends BaseMVPFragment<HistroyChildContract.HistroyChildPrecenter> implements HistroyChildContract.IHistroyChildView {
    private RecyclerView mRvChild;
    private HistroyChildAdapter histroyChildAdapter;
    private List<String> mStrings;

    @Override
    protected void initView() {
        mStrings = new ArrayList<>();
        mRvChild = rootView.findViewById(R.id.child_rv);
        mRvChild.setLayoutManager(new LinearLayoutManager(activity));
        histroyChildAdapter = new HistroyChildAdapter(R.layout.item_histroy_child_list, mStrings);
        mRvChild.setAdapter(histroyChildAdapter);
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
        mPresenter.getMsg();
    }

    @Override
    public BasePresenter initPresenter() {
        return HistroyChildPresenter.newIntance();
    }

    @Override
    public void showSuccess(List<String> msgs) {
        mStrings.addAll(msgs);
        histroyChildAdapter.notifyDataSetChanged();
    }
}
