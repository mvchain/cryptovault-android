package com.mvc.cryptovault_android.adapter.rvAdapter;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.bean.TogeBean;

import java.util.Date;
import java.util.List;

public class TogeChildAdapter extends BaseQuickAdapter<TogeBean.DataBean, BaseViewHolder> {

    public TogeChildAdapter(int layoutResId, @Nullable List<TogeBean.DataBean> data) {
        super(layoutResId, data);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void convert(BaseViewHolder helper, TogeBean.DataBean item) {
        ImageView togeIcon = helper.getView(R.id.toge_child_icon);
        TextView submit = helper.getView(R.id.toge_child_submit);
        helper.setText(R.id.toge_child_title, item.getProjectName());
        long remainingTime = item.getStartedAt() - item.getStopAt();
        if (item.getStatus() == 1) {
            submit.setText(R.string.toge_com);
            submit.setVisibility(View.VISIBLE);
            submit.setBackground(mContext.getDrawable(R.drawable.bg_toge_child_item_tv_blue));
            submit.setTextColor(mContext.getColor(R.color.white));
            submit.setEnabled(true);
            helper.setText(R.id.toge_child_sj, TimeUtils.getFitTimeSpan(new Date(item.getStopAt()), new Date(item.getStartedAt()), 2));
        } else if (item.getStatus() == 0) {
            submit.setVisibility(View.GONE);
            helper.setText(R.id.toge_child_sj, TimeUtils.getFitTimeSpan(new Date(item.getStopAt()), new Date(item.getStartedAt()), 2));
        } else {
            submit.setText(R.string.toge_end);
            submit.setVisibility(View.VISIBLE);
            submit.setBackground(mContext.getDrawable(R.drawable.bg_toge_child_item_tv_gray));
            submit.setTextColor(mContext.getColor(R.color.gray_white));
            submit.setEnabled(false);
            helper.setText(R.id.toge_child_sj, R.string.toge_end);
        }
        helper.setText(R.id.toge_child_recevie_type, "接收币种：" + item.getBaseTokenName());
        helper.setText(R.id.toge_child_recevie_bespoke, "预约时间：" + TimeUtils.millis2String(item.getStartedAt()));
        helper.setText(R.id.toge_child_gm, item.getTotal() + item.getTokenName());
        helper.setText(R.id.toge_child_xg, item.getProjectLimit() + item.getTokenName());
        helper.setText(R.id.toge_child_jg, "1" + item.getTokenName() + " = " + item.getRatio() + item.getBaseTokenName());
        helper.setText(R.id.toge_child_bl, item.getReleaseValue() + "%");
        Glide.with(mContext).load(item.getProjectImage()).into(togeIcon);
        helper.addOnClickListener(R.id.toge_child_submit);
    }
}
