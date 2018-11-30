package com.mvc.cryptovault_android.adapter.rvAdapter;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.bean.AssetListBean;

import java.text.DecimalFormat;
import java.util.List;

public class WalletAssetsAdapter extends BaseQuickAdapter<AssetListBean.DataBean, BaseViewHolder> {
    //    btc EOS ETC VRT XRP
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
        if (tokenName.equals("btc")) {
            Glide.with(mContext).load(R.drawable.home_icon_btc).into(icon);
        } else if (tokenName.equals("EOS")) {
            Glide.with(mContext).load(R.drawable.home_icon_eos).into(icon);
        } else if (tokenName.equals("ETC")) {
            Glide.with(mContext).load(R.drawable.home_icon_etc).into(icon);
        } else if (tokenName.equals("VRT")) {
            Glide.with(mContext).load(R.drawable.home_icon_vrt).into(icon);
        } else if (tokenName.equals("XRP")) {
            Glide.with(mContext).load(R.drawable.home_icon_xrp).into(icon);
        }
    }
}
