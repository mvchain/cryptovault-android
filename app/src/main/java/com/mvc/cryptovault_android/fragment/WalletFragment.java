package com.mvc.cryptovault_android.fragment;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bartoszlipinski.recyclerviewheader2.RecyclerViewHeader;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.activity.HistroyActivity;
import com.mvc.cryptovault_android.activity.IncreaseCurrencyActivity;
import com.mvc.cryptovault_android.activity.MsgActivity;
import com.mvc.cryptovault_android.adapter.rvAdapter.WalletAssetsAdapter;
import com.mvc.cryptovault_android.base.BaseMVPFragment;
import com.mvc.cryptovault_android.base.BasePresenter;
import com.mvc.cryptovault_android.base.ExchangeRateBean;
import com.mvc.cryptovault_android.bean.AllAssetBean;
import com.mvc.cryptovault_android.bean.AssetListBean;
import com.mvc.cryptovault_android.bean.CurrencyBean;
import com.mvc.cryptovault_android.bean.RateDefalutBean;
import com.mvc.cryptovault_android.contract.WallteContract;
import com.mvc.cryptovault_android.listener.IPopViewListener;
import com.mvc.cryptovault_android.presenter.WalletPresenter;
import com.mvc.cryptovault_android.utils.DataTempCacheMap;
import com.mvc.cryptovault_android.utils.FileUtils;
import com.mvc.cryptovault_android.utils.JsonHelper;
import com.mvc.cryptovault_android.utils.TextViewDrawUtils;
import com.mvc.cryptovault_android.view.PopViewHelper;
import com.per.rslibrary.IPermissionRequest;
import com.per.rslibrary.RsPermission;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class WalletFragment extends BaseMVPFragment<WallteContract.WalletPresenter> implements WallteContract.IWallteView, View.OnClickListener, IPermissionRequest, IPopViewListener {
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
    private List<ExchangeRateBean.DataBean> mExchange;
    private SwipeRefreshLayout mSwipAsstes;
    private CurrencyBean cyBean = null;
    private AssetListBean assetBean = null;
    private AllAssetBean allAssetBean = null;
    private PopupWindow mPopView;
    private int position = 0;

    @Override
    protected void initView() {
        mData = new ArrayList<>();
        mExchange = new ArrayList<>();
        mBarStatus = rootView.findViewById(R.id.status_bar);
        mHintAssets = rootView.findViewById(R.id.assets_hint);
        mTitleAssets = rootView.findViewById(R.id.assets_title);
        mAddAssets = rootView.findViewById(R.id.assets_add);
        mAssetsAll = rootView.findViewById(R.id.all_assets);
        mTypeAssets = rootView.findViewById(R.id.assets_type);
        mPriceAssets = rootView.findViewById(R.id.assets_price);
        mRvAssets = rootView.findViewById(R.id.assets_rv);
        mAssetsLayout = rootView.findViewById(R.id.assets_layout);
        mSwipAsstes = rootView.findViewById(R.id.asstes_swip);
        mSwipAsstes.post(() -> mSwipAsstes.setRefreshing(true));
        mSwipAsstes.setOnRefreshListener(this::onRefresh);
        mHintAssets.setOnClickListener(this);
        mAddAssets.setOnClickListener(this);
        mTypeAssets.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_wallet;
    }

    @Override
    public BasePresenter initPresenter() {
        return WalletPresenter.newIntance();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.assets_hint:
                // TODO 18/11/28
                startActivity(new Intent(activity, MsgActivity.class));
                break;
            case R.id.assets_add:
                // TODO 18/11/28
                startActivity(new Intent(activity, IncreaseCurrencyActivity.class));
                break;
            case R.id.assets_type:
                // TODO 18/11/28
                mPopView.showAsDropDown(mTypeAssets, -50, -10, Gravity.CENTER);
                TextViewDrawUtils.setRigthDraw(activity.getDrawable(R.drawable.down_icon), mTypeAssets);
                break;
        }
    }

    @Override
    protected void initData() {
        super.initData();
        mRvAssets.setLayoutManager(new LinearLayoutManager(activity));
        assetsAdapter = new WalletAssetsAdapter(R.layout.item_home_assets_type, mData);
        assetsAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.item_assets_layout:
                    Intent intent = new Intent(activity, HistroyActivity.class);
                    int type = 0;
                    CurrencyBean currencyBean = (CurrencyBean) JsonHelper.stringToJson((String) DataTempCacheMap.get("currency_list").getValue(), CurrencyBean.class);
                    for (int i = 0; i < currencyBean.getData().size(); i++) {
                        if (mData.get(position).getTokenId() == currencyBean.getData().get(i).getTokenId()) {
                            type = currencyBean.getData().get(i).getTokenType();
                            break;
                        }
                    }
                    intent.putExtra("type", type);
                    intent.putExtra("tokenId", mData.get(position).getTokenId());
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
        //检查是否有时间戳，用来限制汇率接口调用次数
        long rate_time = SPUtils.getInstance().getLong("rate_time", -1);
        if (rate_time == -1) {
            SPUtils.getInstance().put("rate_time", System.currentTimeMillis());
            mPresenter.getExchangeRate(token);
        } else if (System.currentTimeMillis() - rate_time >= 60 * 60 * 12 * 1000) {
            SPUtils.getInstance().put("rate_time", System.currentTimeMillis());
            mPresenter.getExchangeRate(token);
        }
        //初始化pop
        initPop();
        RateDefalutBean defalutBean = (RateDefalutBean) JsonHelper.stringToJson(getDefalutRate(), RateDefalutBean.class);
        if (defalutBean != null) {
            mTypeAssets.setText(defalutBean.getRate_name());
        }
    }

    private void initPop() {
        String rate_default = SPUtils.getInstance().getString("rate_default");
        ArrayList<String> content = new ArrayList<>();
        if (rate_default != null && !rate_default.equals("")) {
            ExchangeRateBean rateBean = (ExchangeRateBean) JsonHelper.stringToJson(rate_default, ExchangeRateBean.class);
            for (ExchangeRateBean.DataBean dataBean : rateBean.getData()) {
                content.add(dataBean.getName());
                mExchange.add(dataBean);
            }
            if (getDefalutRate().equals("")) {
                JSONObject object = new JSONObject();
                try {
                    object.put("rate_name", rateBean.getData().get(0).getName());
                    object.put("rate_value", rateBean.getData().get(0).getValue());
                    SPUtils.getInstance().put("rate_mod", object.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            mPopView = PopViewHelper.getInstance().setiPopViewListener(this).create(activity, R.layout.layout_rate_pop, content);
        }
    }

    @Override
    public void refreshAssetList(AssetListBean asset) {
        mData.addAll(asset.getData());
        assetBean = asset;
        mSwipAsstes.post(() -> mSwipAsstes.setRefreshing(false));
        DataTempCacheMap.put("asset_list", JsonHelper.jsonToString(asset));
        assetsAdapter.notifyDataSetChanged();
    }

    /**
     * 刷新总资产余额
     *
     * @param allAssetBean
     */
    @Override
    public void refreshAllAsset(AllAssetBean allAssetBean) {
        DecimalFormat format = new DecimalFormat("#.##");
        mPriceAssets.setText(format.format(allAssetBean.getData()));
        this.allAssetBean = allAssetBean;
    }

    /**
     * 保存资产列表
     *
     * @param currencyBean
     */
    @Override
    public void savaLocalCurrency(CurrencyBean currencyBean) {
        cyBean = currencyBean;
        DataTempCacheMap.put("currency_list", JsonHelper.jsonToString(currencyBean));
        RsPermission.getInstance().setRequestCode(200).setiPermissionRequest(this).requestPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    /**
     * 保存汇率
     *
     * @param currencyBean
     */
    @Override
    public void savaExchangeRate(ExchangeRateBean currencyBean) {
        DataTempCacheMap.put("rate_list", JsonHelper.jsonToString(currencyBean));
        SPUtils.getInstance().put("rate_default", JsonHelper.jsonToString(currencyBean));
        initPop();
    }

    /**
     * 服务器出错/网络异常 都会走这里，此时的数据为本地缓存
     */
    @Override
    public void serverError() {
        String currency_list = FileUtils.getFileToString("currency_list");
        String asset_list = FileUtils.getFileToString("asset_list");
        String allAsset = FileUtils.getFileToString("allAssetBean");
        if (!currency_list.equals("")) {
            CurrencyBean currencyBean = (CurrencyBean) JsonHelper.stringToJson(currency_list,CurrencyBean.class);
            /**
             * save data to DataTempCacheMap
             */
            for (CurrencyBean.DataBean dataBean : currencyBean.getData()) {
                DataTempCacheMap.put(String.valueOf(dataBean.getTokenId()), dataBean.getTokenImage());
            }
            DataTempCacheMap.put("currency_list", currency_list);
        }
        if (!asset_list.equals("")) {
            AssetListBean assetListBean = (AssetListBean) JsonHelper.stringToJson(asset_list,AssetListBean.class);
            mData.clear();
            mData.addAll(assetListBean.getData());
            assetsAdapter.notifyDataSetChanged();
            DataTempCacheMap.put("asset_list", asset_list);
        }
        if (!allAsset.equals("")) {
            AllAssetBean allAssetBean = (AllAssetBean) JsonHelper.stringToJson(allAsset,AllAssetBean.class);
            DecimalFormat format = new DecimalFormat("#.##");
            mPriceAssets.setText(format.format(allAssetBean.getData()));
        }
        mSwipAsstes.setRefreshing(false);
    }

    /**
     * 刷新接口
     */
    public void onRefresh() {
        String token = SPUtils.getInstance().getString("token");
        mData.clear();
        assetsAdapter.notifyDataSetChanged();
        mPresenter.getAllAsset(token);
        mPresenter.getAssetList(token);
        mPresenter.getCurrencyAll(token);
    }

    /**
     * 权限回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        RsPermission.getInstance().onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 跳转到权限设置页面
     */
    @Override
    public void toSetting() {
        RsPermission.getInstance().toSettingPer();
    }

    @Override
    public void cancle(int i) {

    }

    /**
     * 权限成功回调
     *
     * @param i
     */
    @Override
    public void success(int i) {
        switch (i) {
            case 200:
                FileSava();
                break;
        }
    }

    /**
     * 保存json到本地文件/保存一份临时文件
     */
    private void FileSava() {
        //save list currency
        if (cyBean != null) {
            FileUtils.saveJsonFile("currency_list", cyBean);
        }
        // save List of existing assets
        if (assetBean != null) {
            FileUtils.saveJsonFile("asset_list", assetBean);
        }
        if (allAssetBean != null) {
            FileUtils.saveJsonFile("allAssetBean", allAssetBean);
        }

        List<CurrencyBean.DataBean> data = cyBean.getData();
        for (CurrencyBean.DataBean dataBean : data) {
            DataTempCacheMap.put(String.valueOf(dataBean.getTokenId()), dataBean.getTokenImage());
            LogUtils.e("WalletFragment", dataBean.getTokenImage());
        }
    }

    /**
     * 设置汇率的时候会回调该方法
     *
     * @param position
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void toRate(int position) {
        ExchangeRateBean.DataBean dataBean = mExchange.get(position);
        JSONObject object = new JSONObject();
        try {
            object.put("rate_name", dataBean.getName());
            object.put("rate_value", dataBean.getValue());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        SPUtils.getInstance().put("rate_mod", object.toString());
        changeAssets();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void dismiss() {
        TextViewDrawUtils.setRigthDraw(activity.getDrawable(R.drawable.up_icon), mTypeAssets);
    }

    /**
     * 设置汇率之后需要更改金额
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void changeAssets() {
        TextViewDrawUtils.setRigthDraw(activity.getDrawable(R.drawable.up_icon), mTypeAssets);
        RateDefalutBean defalutBean = (RateDefalutBean) JsonHelper.stringToJson(getDefalutRate(), RateDefalutBean.class);
        mTypeAssets.setText(defalutBean.getRate_name());
//        float price;
//        LogUtils.e("WalletFragment", defalutBean.getRate_name());
//        if (defalutBean.getRate_name().equals("CNY")) {
//            price = Float.parseFloat(mPriceAssets.getText().toString()) * defalutBean.getRate_value();
//        } else {
//            price = Float.parseFloat(mPriceAssets.getText().toString()) / defalutBean.getRate_value();
//        }
//        mPriceAssets.setText(new DecimalFormat("#.##").format(price));
////        update assets
//        assetsAdapter.notifyDataSetChanged();

    }
}
