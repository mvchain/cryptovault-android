package com.mvc.cryptovault_android.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bartoszlipinski.recyclerviewheader2.RecyclerViewHeader;
import com.blankj.utilcode.util.SPUtils;
import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.activity.MsgActivity;
import com.mvc.cryptovault_android.adapter.rvAdapter.WalletAssetsAdapter;
import com.mvc.cryptovault_android.base.BaseMVPFragment;
import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.bean.AllAssetBean;
import com.mvc.cryptovault_android.bean.AssetListBean;
import com.mvc.cryptovault_android.contract.WallteContract;
import com.mvc.cryptovault_android.presenter.WalletPresenter;

import java.text.DecimalFormat;
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
    private RecyclerViewHeader mAssetsLayout;
    private RecyclerView mRvAssets;
    private WalletAssetsAdapter assetsAdapter;
    private List<AssetListBean.DataBean> mData;
    private SwipeRefreshLayout mSwipAsstes;

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
        mSwipAsstes = rootView.findViewById(R.id.asstes_swip);
        mSwipAsstes.setOnRefreshListener(this::onRefresh);
        mSwipAsstes.post(() -> mSwipAsstes.setRefreshing(true));
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
        mRvAssets.setLayoutManager(new LinearLayoutManager(activity));
        assetsAdapter = new WalletAssetsAdapter(R.layout.item_home_assets_type, mData);
        mAssetsLayout.attachTo(mRvAssets);
        mRvAssets.setAdapter(assetsAdapter);
        String token = SPUtils.getInstance().getString("token");
        mPresenter.getAllAsset(token);
        mPresenter.getAssetList(token);
    }

    @Override
    public void refreshAssetList(AssetListBean asset) {
        mData.addAll(asset.getData());
        mSwipAsstes.post(() -> mSwipAsstes.setRefreshing(false));
        assetsAdapter.notifyDataSetChanged();
    }

    @Override
    public void refreshAllAssrt(AllAssetBean allAssetBean) {
        DecimalFormat format = new DecimalFormat("#.##");
        mPriceAssets.setText(format.format(allAssetBean.getData()));
    }

    public void onRefresh() {
        String token = SPUtils.getInstance().getString("token");
        mData.clear();
        assetsAdapter.notifyDataSetChanged();
        mPresenter.getAllAsset(token);
        mPresenter.getAssetList(token);
    }
}
