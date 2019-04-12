package com.mvc.ttpay_android.adapter.rvAdapter;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mvc.ttpay_android.R;
import com.mvc.ttpay_android.bean.HistroyBean;
import com.mvc.ttpay_android.utils.TextUtils;

import java.util.List;

public class HistoryChildAdapter extends BaseQuickAdapter<HistroyBean.DataBean, BaseViewHolder> {
    private String[] status = {"转帐中", "转帐中", "转账成功", "转账失败"};
    private int[] status_color = {R.color.login_edit_bg, R.color.login_edit_bg, R.color.login_edit_bg, R.color.error};
    private int[] status_icon = {R.drawable.sent_icon, R.drawable.receive_icon, R.drawable.zc_icon, R.drawable.jy_icon, R.drawable.financial_selected_bold};

    public HistoryChildAdapter(int layoutResId, @Nullable List<HistroyBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HistroyBean.DataBean item) {
        int iconType = 0;
        int transactionType = item.getTransactionType();
        ImageView icon = helper.getView(R.id.his_child_icon);
        helper.setText(R.id.his_child_time, TimeUtils.millis2String(item.getCreatedAt()));
        String price = TextUtils.INSTANCE.doubleToEight(item.getValue());
        TextView priceView = helper.getView(R.id.his_child_price);
        priceView.setText((item.getTransactionType() == 1 ? "+" : "-") + price);
        TextView mStatusTv = helper.getView(R.id.his_child_status);
        if (item.getClassify() == 0) {
            mStatusTv.setVisibility(View.VISIBLE);
            mStatusTv.setText(status[item.getStatus() > 3 ? 3 : item.getStatus()]);
            mStatusTv.setTextColor(status_color[item.getStatus() > 3 ? 3 : item.getStatus()]);
        }
        if (item.getTransactionType() == 0) {
            priceView.setTextColor(ContextCompat.getColor(mContext, R.color.trand_gray));
        } else {
            priceView.setTextColor(ContextCompat.getColor(mContext, R.color.login_content));
        }
        if ((item.getClassify() == 0 || item.getClassify() == 3)) {
            if (transactionType == 1) {
                iconType = 1;
                helper.setText(R.id.his_child_title, item.getOrderRemark() + " 收入");
            } else {
                iconType = 0;
                helper.setText(R.id.his_child_title, item.getOrderRemark() + " 支出");
            }
        } else if (item.getClassify() == 1) {
            iconType = 3;
            helper.setText(R.id.his_child_title, item.getOrderRemark() + " 交易");
        } else if (item.getClassify() == 2) {
            iconType = 2;
            StringBuffer buffer = new StringBuffer();
            switch (item.getStatus()) {
                case 9:
                    buffer.append("退回");
                    break;
                case 2:
                    buffer.append("发币");
                    break;
                case 0:
                    buffer.append("预约");
                    break;
            }
            helper.setText(R.id.his_child_title, item.getOrderRemark() + " 众筹" + buffer.toString());
        } else if (item.getClassify() == 4) {
            iconType = 4;
            StringBuffer buffer = new StringBuffer();
            switch (item.getStatus()) {
                case 4:
                    buffer.append("取出");
                    break;
                case 5:
                    buffer.append("提成");
                    break;
                case 6:
                    buffer.append("收益");
                    break;
            }
            helper.setText(R.id.his_child_title, item.getOrderRemark() + " " + buffer.toString());
        }
        Glide.with(mContext).load(status_icon[iconType]).into(icon);
        helper.addOnClickListener(R.id.his_layout);
    }
}
