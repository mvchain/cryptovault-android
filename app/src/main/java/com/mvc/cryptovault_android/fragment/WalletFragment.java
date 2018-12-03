package com.mvc.cryptovault_android.fragment;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bartoszlipinski.recyclerviewheader2.RecyclerViewHeader;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.activity.HistroyActivity;
import com.mvc.cryptovault_android.activity.MsgActivity;
import com.mvc.cryptovault_android.adapter.rvAdapter.WalletAssetsAdapter;
import com.mvc.cryptovault_android.base.BaseMVPFragment;
import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.bean.AllAssetBean;
import com.mvc.cryptovault_android.bean.AssetListBean;
import com.mvc.cryptovault_android.bean.CurrencyBean;
import com.mvc.cryptovault_android.contract.WallteContract;
import com.mvc.cryptovault_android.presenter.WalletPresenter;
import com.mvc.cryptovault_android.utils.DataTempCacheMap;
import com.mvc.cryptovault_android.utils.FileUtils;
import com.per.rslibrary.IPermissionRequest;
import com.per.rslibrary.RsPermission;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class WalletFragment extends BaseMVPFragment<WallteContract.WalletPresenter> implements WallteContract.IWallteView, View.OnClickListener, IPermissionRequest {
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
    private List<CurrencyBean.DataBean> cyBean;

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
        cyBean = new ArrayList<>();
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
        assetsAdapter.setOnItemClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.item_assets_layout:
                    Intent intent = new Intent(activity, HistroyActivity.class);
                    startActivity(intent);
                    break;
            }
        });
        mAssetsLayout.attachTo(mRvAssets);
        mRvAssets.setAdapter(assetsAdapter);
        String token = SPUtils.getInstance().getString("token");
        mPresenter.getAllAsset(token);
        mPresenter.getAssetList(token);
        mPresenter.getCurrencyAll(token);
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

    @Override
    public void savaLocalCurrency(CurrencyBean currencyBean) {
        cyBean.addAll(currencyBean.getData());
        RsPermission.getInstance().setRequestCode(200).setiPermissionRequest(this).requestPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    public void onRefresh() {
        String token = SPUtils.getInstance().getString("token");
        mData.clear();
        assetsAdapter.notifyDataSetChanged();
        mPresenter.getAllAsset(token);
        mPresenter.getAssetList(token);
        mPresenter.getCurrencyAll(token);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        RsPermission.getInstance().onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void toSetting() {

    }

    @Override
    public void cancle(int i) {

    }

    @Override
    public void success(int i) {
        switch (i) {
            case 200:
                FileSava();
                break;
        }
    }

    private void FileSava() {
        for (CurrencyBean.DataBean dataBean : cyBean) {
            DataTempCacheMap.put(dataBean.getTokenCnName(), dataBean);
            DataTempCacheMap.put(dataBean.getTokenEnName(), dataBean);
            DataTempCacheMap.put(dataBean.getTokenName(), dataBean);
            //cache img
            DataTempCacheMap.put(String.valueOf(dataBean.getTokenId()), dataBean.getTokenImage());
            FileUtils.saveFile("currency_list", dataBean);
            String currency_list = FileUtils.getFileToString("currency_list");
            LogUtils.e("WalletFragment", "=================>:"+currency_list);
        }
    }
}
