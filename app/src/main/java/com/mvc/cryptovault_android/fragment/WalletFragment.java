package com.mvc.cryptovault_android.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dasu.recyclerlibrary.ui.ScrollWrapRecycler;
import com.gyf.barlibrary.ImmersionBar;
import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.activity.MsgActivity;
import com.mvc.cryptovault_android.adapter.rvAdapter.WalletAssetsAdapter;
import com.mvc.cryptovault_android.base.BaseMVPFragment;
import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.contract.WallteContract;
import com.mvc.cryptovault_android.presenter.WalletPresenter;

import java.util.ArrayList;
import java.util.List;

public class WalletFragment extends BaseMVPFragment<WallteContract.WalletPresenter> implements WallteContract.IWallteView, View.OnClickListener {
    private View mBarStatus;
    private ImageView mHintAssets;
    private TextView mTitleAssets;
    private ImageView mAddAssets;
    private TextView mAssetsAll;
    private TextView mTypeAssets;
    private TextView mPriceAssets;
    private RelativeLayout mAssetsLayout;
    private ScrollWrapRecycler mRvAssets;
    private WalletAssetsAdapter assetsAdapter;
    private List<String> mData;

    @Override
    protected void initView() {
        mBarStatus = rootView.findViewById(R.id.status_bar);
        mHintAssets = rootView.findViewById(R.id.assets_hint);
        mHintAssets.setOnClickListener(this);
        mTitleAssets = rootView.findViewById(R.id.assets_title);
        mAddAssets = rootView.findViewById(R.id.assets_add);
        mAddAssets.setOnClickListener(this);
        mAssetsAll = rootView.findViewById(R.id.all_assets);
        mTypeAssets = rootView.findViewById(R.id.assets_type);
        mTypeAssets.setOnClickListener(this);
        mPriceAssets = rootView.findViewById(R.id.assets_price);
        mRvAssets = rootView.findViewById(R.id.assets_rv);
        mAssetsLayout = rootView.findViewById(R.id.assets_layout);
        mData = new ArrayList<>();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_wallet;
    }

    @Override
    public BasePresenter initPresenter() {
        return WalletPresenter.newIntance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.assets_hint:
                // TODO 18/11/28
                startActivity(new Intent(activity, MsgActivity.class));
                break;
            case R.id.assets_add:
                // TODO 18/11/28
                break;
            case R.id.assets_type:
                // TODO 18/11/28
                break;
        }
    }

    @Override
    protected void initData() {
        super.initData();
        ImmersionBar.with(activity).titleBar(R.id.status_bar).statusBarDarkFont(true).init();
        mRvAssets.setLayoutManager(new LinearLayoutManager(activity));
        assetsAdapter = new WalletAssetsAdapter(R.layout.item_home_assets_type, mData);
        mRvAssets.addHeadView(mAssetsLayout);
        mRvAssets.setRefresh(false);
        mRvAssets.setLoadMore(false);
        mRvAssets.setAdapter(assetsAdapter);
        mPresenter.refreshData();
    }

    @Override
    public void refresh(List<String> string) {
        mData.addAll(string);
        assetsAdapter.notifyDataSetChanged();
    }
}
