package com.mvc.ttpay_android.adapter.rvAdapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mvc.ttpay_android.MyApplication;
import com.mvc.ttpay_android.R;
import com.mvc.ttpay_android.bean.RecorBean;
import com.mvc.ttpay_android.utils.TextUtils;

import java.util.List;

import static com.mvc.ttpay_android.common.Constant.SP.RECORDING_TYPE;

public class RecorAdapter extends BaseQuickAdapter<RecorBean.DataBean, BaseViewHolder> {
    public RecorAdapter(int layoutResId, @Nullable List<RecorBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecorBean.DataBean item) {
        ImageView recIcon = helper.getView(R.id.recor_icon);
        helper.setText(R.id.recor_nickname, item.getNickname());
        helper.setText(R.id.recor_max, (item.getTransactionType() == 2 ? MyApplication.getAppContext().getString(R.string.remaining_available_for_sale) : MyApplication.getAppContext().getString(R.string.remaining_purchase_amount)) + TextUtils.INSTANCE.doubleToEight(item.getLimitValue()));
        helper.setText(R.id.recor_price, TextUtils.INSTANCE.doubleToEight(item.getPrice().doubleValue()) + " " + SPUtils.getInstance().getString(RECORDING_TYPE));
        RequestOptions options = new RequestOptions().fallback(R.drawable.portrait_icon).placeholder(R.drawable.loading_img).error(R.drawable.portrait_icon);
        Glide.with(mContext).load(item.getHeadImage()).apply(options).into(recIcon);
        helper.addOnClickListener(R.id.recording_layout);
    }
}
