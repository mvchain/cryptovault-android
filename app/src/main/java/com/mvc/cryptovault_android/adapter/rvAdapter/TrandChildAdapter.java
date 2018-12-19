package com.mvc.cryptovault_android.adapter.rvAdapter;

import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.bean.TrandChildBean;
import com.mvc.cryptovault_android.utils.TextUtils;

import java.util.List;

import static com.mvc.cryptovault_android.common.Constant.SP.DEFAULE_SYMBOL;

public class TrandChildAdapter extends BaseQuickAdapter<TrandChildBean.DataBean, BaseViewHolder> {

    public TrandChildAdapter(int layoutResId, @Nullable List<TrandChildBean.DataBean> data) {
        super(layoutResId, data);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void convert(BaseViewHolder helper, TrandChildBean.DataBean item) {
        ImageView icon = helper.getView(R.id.trand_child_icon);
        TextView increase = helper.getView(R.id.trand_child_increase);

        double incre = item.getIncrease();
        if (incre < 0) {
            increase.setBackground(mContext.getDrawable(R.drawable.shape_aoi_ching_5dp));
        } else {
            increase.setBackground(mContext.getDrawable(R.drawable.shape_aoi_orangey_5dp));
        }
        if (item.getTransactionStatus() == 0) {
            increase.setBackground(mContext.getDrawable(R.drawable.shape_aoi_gray_5dp));
            increase.setGravity(Gravity.CENTER);
            increase.setText("不可交易");
            increase.setTextColor(mContext.getColor(R.color.trand_gray));
        } else {
            increase.setText(TextUtils.doubleToDouble(incre) + "%");
        }
        helper.setText(R.id.trand_child_ratio, TextUtils.doubleToFour(item.getRatio()) + " " + item.getPair().substring(0, item.getPair().indexOf("/")));
        helper.setText(R.id.trand_child_title, item.getTokenName());
        helper.setText(R.id.trand_child_pair, SPUtils.getInstance().getString(DEFAULE_SYMBOL) + TextUtils.rateToPrice(item.getRatio()));
        Glide.with(mContext).load(item.getTokenImage()).into(icon);
        helper.addOnClickListener(R.id.trand_layout);
    }
}
