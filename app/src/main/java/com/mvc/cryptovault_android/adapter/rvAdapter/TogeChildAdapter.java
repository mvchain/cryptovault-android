package com.mvc.cryptovault_android.adapter.rvAdapter;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
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
        if (item.getStatus() == 1) {
            submit.setText(R.string.toge_com);
            submit.setVisibility(View.VISIBLE);
            submit.setBackground(ContextCompat.getDrawable(mContext,R.drawable.bg_toge_child_item_tv_blue));
            submit.setTextColor(ContextCompat.getColor(mContext,R.color.white));
            submit.setEnabled(true);
            helper.setText(R.id.toge_child_sj, TimeUtils.getFitTimeSpan(new Date(item.getStopAt()),new Date(System.currentTimeMillis()), 4));
        } else if (item.getStatus() == 0) {
            submit.setVisibility(View.GONE);
            helper.setText(R.id.toge_child_sj, "即将开始");
        } else {
            submit.setText(R.string.toge_end);
            submit.setVisibility(View.VISIBLE);
            submit.setBackground(ContextCompat.getDrawable(mContext,R.drawable.bg_toge_child_item_tv_gray));
            submit.setTextColor(ContextCompat.getColor(mContext,R.color.gray_white));
            submit.setEnabled(false);
            helper.setText(R.id.toge_child_sj, R.string.toge_end);
        }
        helper.setText(R.id.toge_child_recevie_type, "接收币种：" + item.getBaseTokenName());
        helper.setText(R.id.toge_child_recevie_bespoke, "预约时间：" + TimeUtils.millis2String(item.getStartedAt()));
        helper.setText(R.id.toge_child_gm, item.getTotal() + " " + item.getTokenName());
        helper.setText(R.id.toge_child_xg, item.getProjectLimit() + " " + item.getTokenName());
        helper.setText(R.id.toge_child_jg, "1 " + item.getTokenName() + " = " + item.getRatio() + " " + item.getBaseTokenName());
        helper.setText(R.id.toge_child_bl, item.getReleaseValue() + "%");
        RequestOptions options = new RequestOptions().fallback(R.drawable.default_project).placeholder(R.drawable.loading_img).error(R.drawable.default_project);
        Glide.with(mContext).load(item.getProjectImage()).apply(options).into(togeIcon);
        helper.getView(R.id.toge_child_submit).setOnClickListener(null);
        helper.addOnClickListener(R.id.toge_child_submit);
    }
}
