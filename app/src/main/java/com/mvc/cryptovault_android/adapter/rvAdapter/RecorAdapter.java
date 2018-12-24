package com.mvc.cryptovault_android.adapter.rvAdapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.bean.RecorBean;
import com.mvc.cryptovault_android.common.Constant;
import com.mvc.cryptovault_android.utils.TextUtils;

import java.util.List;

import static com.mvc.cryptovault_android.common.Constant.SP.RECORDING_TYPE;
import static com.mvc.cryptovault_android.common.Constant.SP.RECORDING_UNIT;

public class RecorAdapter extends BaseQuickAdapter<RecorBean.DataBean, BaseViewHolder> {
    public RecorAdapter(int layoutResId, @Nullable List<RecorBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecorBean.DataBean item) {
        ImageView recIcon = helper.getView(R.id.recor_icon);
        helper.setText(R.id.recor_nickname, item.getNickname());
        helper.setText(R.id.recor_max, (item.getTransactionType() == 1 ? "剩余购买量：" : "剩余出售量：") + TextUtils.doubleToFour(item.getLimitValue()));
        helper.setText(R.id.recor_price, TextUtils.doubleToFour(item.getPrice()) + " " + SPUtils.getInstance().getString(RECORDING_TYPE))
        ;
        Glide.with(mContext).load(item.getHeadImage()).into(recIcon);
        helper.addOnClickListener(R.id.recording_layout);
    }
}
