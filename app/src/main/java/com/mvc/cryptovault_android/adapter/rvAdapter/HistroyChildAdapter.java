package com.mvc.cryptovault_android.adapter.rvAdapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.bean.HistroyBean;

import java.util.List;

public class HistroyChildAdapter extends BaseQuickAdapter<HistroyBean.DataBean, BaseViewHolder> {
    private String[] status = {"转帐中", "转帐中", "转账成功", "转账失败"};
    private int[] status_color = {R.color.login_edit_bg, R.color.login_edit_bg, R.color.login_edit_bg, R.color.error};
    private int[] status_icon = {R.drawable.sent_icon, R.drawable.receive_icon, R.drawable.zc_icon, R.drawable.jy_icon};

    public HistroyChildAdapter(int layoutResId, @Nullable List<HistroyBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HistroyBean.DataBean item) {
        int iconType = 0;
        int transactionType = item.getTransactionType();
        ImageView icon = helper.getView(R.id.his_child_icon);
        helper.setText(R.id.his_child_title, item.getTokenName());
        helper.setText(R.id.his_child_time, TimeUtils.millis2String(item.getCreatedAt()));
        helper.setText(R.id.his_child_price, item.getTransactionType() == 1 ? "+" + (item.getRatio() * item.getValue()) : "-" + (item.getRatio() * item.getValue()));
        TextView mStatusTv = helper.getView(R.id.his_child_status);
        mStatusTv.setText(status[item.getStatus()]);
        if (item.getClassify() == 0) {
            mStatusTv.setVisibility(View.VISIBLE);
        } else {
            mStatusTv.setVisibility(View.GONE);
        }
        if ((item.getClassify() == 0 || item.getClassify() == 3) && (item.getStatus() == 0 || item.getStatus() == 1 || item.getStatus() == 2)) {
            if (transactionType == 1) {
                iconType = 0;
            } else {
                iconType = 1;
            }
        } else if (item.getClassify() == 1) {
            iconType = 3;
        } else if (item.getClassify() == 2) {
            iconType = 2;
        }
        mStatusTv.setTextColor(status_color[item.getStatus()]);
        Glide.with(mContext).load(status_icon[iconType]).into(icon);
    }
}
