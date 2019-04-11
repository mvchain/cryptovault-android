package com.mvc.cryptovault_android.adapter.rvAdapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.bean.TogeHisBean;
import com.mvc.cryptovault_android.utils.TextUtils;

import java.util.List;

public class TogeHisAdapter extends BaseQuickAdapter<TogeHisBean.DataBean, BaseViewHolder> {
    public TogeHisAdapter(int layoutResId, @Nullable List<TogeHisBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TogeHisBean.DataBean item) {
        helper.addOnClickListener(R.id.his_layout);
        ImageView hisIcon = helper.getView(R.id.toge_histroy_icon);
        TextView hisType = helper.getView(R.id.toge_histroy_type);
        switch (item.getReservationType()) {
            case 0:
                hisType.setText("等待项目预约结算");
                Glide.with(mContext).load(R.drawable.pending_icon_1).into(hisIcon);
                break;
            case 1:
                hisType.setText("已获得众筹名额");
                Glide.with(mContext).load(R.drawable.success_icon_1).into(hisIcon);
                break;
            case 9:
                hisType.setText("未获得众筹名额");
                Glide.with(mContext).load(R.drawable.miss_icon_1).into(hisIcon);
                break;
        }
//        Glide.with(mContext).load(item.)

        helper.setText(R.id.toge_histroy_title, item.getProjectName());
        helper.setText(R.id.toge_histroy_time, TimeUtils.millis2String(item.getCreatedAt()));
        helper.setText(R.id.toge_histroy_order_num, item.getProjectOrderId());
        helper.setText(R.id.toge_histroy_bl, 1 + item.getTokenName() + " = " + item.getRatio() + item.getBaseTokenName());
        helper.setText(R.id.toge_histroy_day_bl, item.getReleaseValue() + "%");
        helper.setText(R.id.toge_histroy_pay_num, TextUtils.INSTANCE.doubleToEight(item.getSuccessPayed()) + "/" + TextUtils.INSTANCE.doubleToEight(item.getPrice()) + " "+item.getBaseTokenName());
        helper.setText(R.id.toge_histroy_success_num, TextUtils.INSTANCE.doubleToEight(item.getSuccessValue()) + "/" + item.getValue() + " " + item.getTokenName());
        helper.setText(R.id.toge_histroy_over_time, TimeUtils.millis2String(item.getStopAt()));
    }
}
