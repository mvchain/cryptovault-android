package com.mvc.cryptovault_android.adapter.rvAdapter;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.bean.AssetListBean;
import com.mvc.cryptovault_android.utils.TextUtils;

import java.util.List;

import static com.mvc.cryptovault_android.common.Constant.SP.DEFAULE_SYMBOL;

public class WalletAssetsAdapter extends BaseQuickAdapter<AssetListBean.DataBean, BaseViewHolder> {
    //    btc home_icon_eos home_icon_etc home_icon_vrt home_icon_xrp
    public WalletAssetsAdapter(int layoutResId, @Nullable List<AssetListBean.DataBean> data) {
        super(layoutResId, data);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void convert(BaseViewHolder helper, AssetListBean.DataBean item) {
        String tokenName = item.getTokenName();
        TextView type = helper.getView(R.id.item_assets_type);
        TextView actual = helper.getView(R.id.item_assets_actual);
        ImageView icon = helper.getView(R.id.item_assets_icon);
        TextView money = helper.getView(R.id.item_assets_money);
        helper.addOnClickListener(R.id.item_assets_layout); //add onclick to the layout to jump startActivity
        type.setText(item.getTokenName());
        money.setText(SPUtils.getInstance().getString(DEFAULE_SYMBOL) + TextUtils.rateToPrice(item.getRatio() * item.getValue()));
        actual.setText(TextUtils.doubleToFour(item.getValue()) + " " + tokenName);
        RequestOptions options = new RequestOptions().fallback(R.drawable.default_project).placeholder(R.drawable.loading_img).error(R.drawable.default_project);
        Glide.with(mContext).load(item.getTokenImage()).apply(options).into(icon);
    }
}
