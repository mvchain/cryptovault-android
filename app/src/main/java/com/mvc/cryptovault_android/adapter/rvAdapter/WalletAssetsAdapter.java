package com.mvc.cryptovault_android.adapter.rvAdapter;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.bean.AssetListBean;
import com.mvc.cryptovault_android.utils.DataTempCacheMap;

import java.text.DecimalFormat;
import java.util.List;

public class WalletAssetsAdapter extends BaseQuickAdapter<AssetListBean.DataBean, BaseViewHolder> {
    //    btc home_icon_eos home_icon_etc home_icon_vrt home_icon_xrp
    public WalletAssetsAdapter(int layoutResId, @Nullable List<AssetListBean.DataBean> data) {
        super(layoutResId, data);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void convert(BaseViewHolder helper, AssetListBean.DataBean item) {
        DecimalFormat moneyFormat = new DecimalFormat("#.##");
        DecimalFormat actualFormat = new DecimalFormat("#.####");
        String tokenName = item.getTokenName();
        TextView type = helper.getView(R.id.item_assets_type);
        TextView actual = helper.getView(R.id.item_assets_actual);
        ImageView icon = helper.getView(R.id.item_assets_icon);
        TextView money = helper.getView(R.id.item_assets_money);
        helper.addOnClickListener(R.id.item_assets_layout); //add onclick to the layout to jump startActivity
        type.setText(item.getTokenName());
        money.setText("￥" + moneyFormat.format(item.getRatio()));
        actual.setText(actualFormat.format(item.getValue()) + " " + tokenName);
        String value = (String) DataTempCacheMap.getPreciseQuery(String.valueOf(item.getTokenId())).getValue();
        LogUtils.e(TAG,"img==============:"+ value);
        Glide.with(mContext).load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542378065226&di=513e584258d0bed94aea1be2b0fd5f11&imgtype=0&").into(icon);
    }
}
