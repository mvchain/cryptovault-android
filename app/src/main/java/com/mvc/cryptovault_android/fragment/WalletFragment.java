package com.mvc.cryptovault_android.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
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
import com.mvc.cryptovault_android.bean.ExchangeRateBean;
import com.mvc.cryptovault_android.bean.AllAssetBean;
import com.mvc.cryptovault_android.bean.AssetListBean;
import com.mvc.cryptovault_android.bean.CurrencyBean;
import com.mvc.cryptovault_android.bean.MsgBean;
import com.mvc.cryptovault_android.contract.WallteContract;
import com.mvc.cryptovault_android.event.TrandFragmentEvent;
import com.mvc.cryptovault_android.event.WalletAssetsListEvent;
import com.mvc.cryptovault_android.event.WalletFragmentEvent;
import com.mvc.cryptovault_android.event.WalletMsgEvent;
import com.mvc.cryptovault_android.listener.IPopViewListener;
import com.mvc.cryptovault_android.presenter.WalletPresenter;
import com.mvc.cryptovault_android.utils.JsonHelper;
import com.mvc.cryptovault_android.utils.TextUtils;
import com.mvc.cryptovault_android.utils.ViewDrawUtils;
import com.mvc.cryptovault_android.view.PopViewHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

import static com.mvc.cryptovault_android.common.Constant.SP.ALLASSETS;
import static com.mvc.cryptovault_android.common.Constant.SP.ASSETS_LIST;
import static com.mvc.cryptovault_android.common.Constant.SP.CURRENCY_LIST;
import static com.mvc.cryptovault_android.common.Constant.SP.DEFAULE_SYMBOL;
import static com.mvc.cryptovault_android.common.Constant.SP.MSG_TIME;
import static com.mvc.cryptovault_android.common.Constant.SP.RATE_LIST;
import static com.mvc.cryptovault_android.common.Constant.SP.READ_MSG;
import static com.mvc.cryptovault_android.common.Constant.SP.SET_RATE;

public class WalletFragment extends BaseMVPFragment<WallteContract.WalletPresenter> implements WallteContract.IWallteView, View.OnClickListener, IPopViewListener {
    private ImageView mHintAssets;
    private ImageView mNullAssets;
    private ImageView mAddAssets;
    private TextView mTypeAssets;
    private TextView mPriceAssets;
    private RecyclerViewHeader mAssetsLayout;
    private RecyclerView mRvAssets;
    private WalletAssetsAdapter assetsAdapter;
    private List<AssetListBean.DataBean> mData;
    private List<ExchangeRateBean.DataBean> mExchange;
    private List<MsgBean.DataBean> mMsgBean;
    private SwipeRefreshLayout mSwipAsstes;
    private PopupWindow mPopView;
    private boolean createCarryOut;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initView() {
        mData = new ArrayList<>();
        mExchange = new ArrayList<>();
        mMsgBean = new ArrayList<>();
        mHintAssets = rootView.findViewById(R.id.assets_hint);
        mNullAssets = rootView.findViewById(R.id.assets_null);
        mAddAssets = rootView.findViewById(R.id.assets_add);
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
        mPriceAssets.setOnClickListener(this);
        createCarryOut = true;
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
                SPUtils.getInstance().put(READ_MSG, true);
                startActivityForResult(new Intent(activity, MsgActivity.class), 200);
                break;
            case R.id.assets_add:
                // TODO 18/11/28
                startActivity(new Intent(activity, IncreaseCurrencyActivity.class));
                break;
            case R.id.assets_price:
            case R.id.assets_type:
                // TODO 18/11/28
                if (mPopView != null) {
                    if (mPopView.isShowing()) {
                        mPopView.dismiss();
                    } else {
                        mPopView.showAsDropDown(mTypeAssets, -mTypeAssets.getWidth()+(mTypeAssets.getWidth()/3), 0, Gravity.CENTER);
                        ViewDrawUtils.setRigthDraw(ContextCompat.getDrawable(activity,R.drawable.down_icon), mTypeAssets);
                    }
                }
                break;
        }
    }

    @Subscribe
    public void updateAssets(WalletAssetsListEvent listEvent) {
        mPresenter.getAssetList(getToken());
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && createCarryOut) {
            onRefresh();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Subscribe
    public void updateRate(WalletFragmentEvent fragmentEvent) {
        int position = fragmentEvent.getPosition();
        changeRate(position);
    }

    @Override
    protected void initData() {
        super.initData();
        if (SPUtils.getInstance().getLong(MSG_TIME) == -1) {
            SPUtils.getInstance().put(MSG_TIME, System.currentTimeMillis());
        }
        JPushInterface.requestPermission(activity);
        mRvAssets.setLayoutManager(new LinearLayoutManager(activity));
//        mRvAssets.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return mAssetsLayout.onTouchEvent(event);
//            }
//        });
        assetsAdapter = new WalletAssetsAdapter(R.layout.item_home_assets_type, mData);
        assetsAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.item_assets_layout:
                    Intent intent = new Intent(activity, HistroyActivity.class);
                    int type = 0;
                    CurrencyBean currencyBean = (CurrencyBean) JsonHelper.stringToJson(SPUtils.getInstance().getString(CURRENCY_LIST), CurrencyBean.class);
                    for (int i = 0; i < currencyBean.getData().size(); i++) {
                        if (mData.get(position).getTokenId() == currencyBean.getData().get(i).getTokenId()) {
                            type = currencyBean.getData().get(i).getTokenType();
                            break;
                        }
                    }
                    intent.putExtra("type", type);
                    intent.putExtra("tokenId", mData.get(position).getTokenId());
                    intent.putExtra("tokenName", mData.get(position).getTokenName());
                    intent.putExtra("data", mData.get(position));
                    intent.putExtra("rate_type", mTypeAssets.getText().toString());
                    startActivity(intent);
                    break;
            }
        });
        mAssetsLayout.attachTo(mRvAssets);
        mRvAssets.setAdapter(assetsAdapter);
        mPresenter.getAssetList(getToken());
        long msg_time = SPUtils.getInstance().getLong(MSG_TIME);
        mPresenter.getMsg(getToken(), msg_time == -1 ? System.currentTimeMillis() : msg_time, 0, 1);
        ExchangeRateBean.DataBean defalutBean = (ExchangeRateBean.DataBean) JsonHelper.stringToJson(getDefalutRate(), ExchangeRateBean.DataBean.class);
        if (defalutBean != null) {
            String default_type = defalutBean.getName();
            mTypeAssets.setText(default_type.substring(1, default_type.length()));
        }
    }

    private void initPop() {
        ArrayList<String> content = new ArrayList<>();
        String rate_list = SPUtils.getInstance().getString(RATE_LIST);
        LogUtils.e("WalletFragment", rate_list);
        if (!rate_list.equals("")) {
            ExchangeRateBean rateBean = (ExchangeRateBean) JsonHelper.stringToJson(rate_list, ExchangeRateBean.class);
            for (ExchangeRateBean.DataBean dataBean : rateBean.getData()) {
                content.add(dataBean.getName());
                mExchange.add(dataBean);
            }
            mPopView = PopViewHelper.getInstance().create(activity, R.layout.layout_rate_pop, content, this);
        }
    }

    @Override
    public void refreshAssetList(AssetListBean asset) {
        SPUtils.getInstance().put(ASSETS_LIST, JsonHelper.jsonToString(asset));
        mPresenter.getAllAsset(getToken());
        mNullAssets.setVisibility(View.GONE);
        mRvAssets.setVisibility(View.VISIBLE);
        mData.clear();
        mData.addAll(asset.getData());
        mSwipAsstes.post(() -> mSwipAsstes.setRefreshing(false));
        assetsAdapter.notifyDataSetChanged();
        initPop();
    }

    /**
     * 刷新总资产余额
     *
     * @param allAssetBean
     */
    @Override
    public void refreshAllAsset(AllAssetBean allAssetBean) {
        SPUtils.getInstance().put(ALLASSETS, JsonHelper.jsonToString(allAssetBean));
        mPriceAssets.setText(TextUtils.rateToPrice(allAssetBean.getData()));
    }

    @Override
    public void refreshMsg(MsgBean msgBean) {
        if (msgBean.getData().size() > 0) {
            mMsgBean.clear();
            mMsgBean.addAll(msgBean.getData());
            mHintAssets.setImageResource(R.drawable.home_newnote_icon);
        } else {
            if (SPUtils.getInstance().getBoolean(READ_MSG, false)) {
                mHintAssets.setImageResource(R.drawable.home_note_icon);
            }
        }
    }


    @Override
    public void showNullAsset() {
        mNullAssets.setVisibility(View.VISIBLE);
        mRvAssets.setVisibility(View.GONE);
    }

    /**
     * 服务器出错/网络异常 都会走这里，此时的数据为本地缓存
     */
    @Override
    public void serverError() {
        String asset_list = SPUtils.getInstance().getString(ASSETS_LIST);
        String allAsset = SPUtils.getInstance().getString(ALLASSETS);
        initPop();
        if (!asset_list.equals("")) {
            AssetListBean assetListBean = (AssetListBean) JsonHelper.stringToJson(asset_list, AssetListBean.class);
            mData.clear();
            mData.addAll(assetListBean.getData());
            assetsAdapter.notifyDataSetChanged();
            mNullAssets.setVisibility(View.GONE);
            mRvAssets.setVisibility(View.VISIBLE);
        } else {
            mNullAssets.setVisibility(View.VISIBLE);
            mRvAssets.setVisibility(View.GONE);
        }
        if (!allAsset.equals("")) {
            AllAssetBean allAssetBean = (AllAssetBean) JsonHelper.stringToJson(allAsset, AllAssetBean.class);
            mPriceAssets.setText(TextUtils.rateToPrice(allAssetBean.getData()));
        }
        mSwipAsstes.post(() -> mSwipAsstes.setRefreshing(false));
    }

    /**
     * 刷新接口
     */
    public void onRefresh() {
//        mData.clear();
        mPresenter.getAllAsset(getToken());
        mPresenter.getAssetList(getToken());
        long msg_time = SPUtils.getInstance().getLong(MSG_TIME);
        mPresenter.getMsg(getToken(), msg_time == -1 ? System.currentTimeMillis() : msg_time, 0, 1);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSwipAsstes.post(() -> mSwipAsstes.setRefreshing(false));
    }

    /**
     * 异步刷新接口
     */
    @Subscribe
    public void msgRefresh(WalletMsgEvent msgEvent) {
//        mData.clear();
        mPresenter.getAllAsset(getToken());
        mPresenter.getAssetList(getToken());
        long msg_time = SPUtils.getInstance().getLong(MSG_TIME);
        mPresenter.getMsg(getToken(), msg_time == -1 ? System.currentTimeMillis() : msg_time, 0, 1);
    }

    /**
     * 设置汇率的时候会回调该方法
     *
     * @param position
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void changeRate(int position) {
        ExchangeRateBean.DataBean dataBean = mExchange.get(position);
        SPUtils.getInstance().put(SET_RATE, JsonHelper.jsonToString(dataBean));
        String symbol = dataBean.getName();
        String newSymbol = symbol.substring(0, 1);
        SPUtils.getInstance().put(DEFAULE_SYMBOL, newSymbol + " ");
        String default_type = dataBean.getName();
        mTypeAssets.setText(default_type.substring(1, default_type.length()));
        changeAssets();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void dismiss() {
        ViewDrawUtils.setRigthDraw(ContextCompat.getDrawable(activity,R.drawable.up_icon), mTypeAssets);
    }

    /**
     * 设置汇率之后需要更改金额
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void changeAssets() {
        ViewDrawUtils.setRigthDraw(ContextCompat.getDrawable(activity,R.drawable.up_icon), mTypeAssets);
        AllAssetBean assetBean = (AllAssetBean) JsonHelper.stringToJson(SPUtils.getInstance().getString(ALLASSETS), AllAssetBean.class);
        mPriceAssets.setText(TextUtils.rateToPrice(assetBean.getData()));
        assetsAdapter.notifyDataSetChanged();
        EventBus.getDefault().post(new TrandFragmentEvent());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200) {
            if(mMsgBean!=null && mMsgBean.size()>0){
                SPUtils.getInstance().put(MSG_TIME, mMsgBean.get(0).getCreatedAt());
            }
            SPUtils.getInstance().put(READ_MSG, false);
            mHintAssets.setImageResource(R.drawable.home_note_icon);
        }
    }
}
