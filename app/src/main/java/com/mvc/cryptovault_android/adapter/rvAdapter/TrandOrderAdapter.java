package com.mvc.cryptovault_android.adapter.rvAdapter;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.bean.TrandOrderBean;

import java.util.List;

public class TrandOrderAdapter extends BaseQuickAdapter<TrandOrderBean.DataBean, BaseViewHolder> {
    public TrandOrderAdapter(int layoutResId, @Nullable List<TrandOrderBean.DataBean> data) {
        super(layoutResId, data);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void convert(BaseViewHolder helper, TrandOrderBean.DataBean item) {
        TextView mPrice = helper.getView(R.id.order_item_forsale);
        TextView mSubmit = helper.getView(R.id.order_item_submit);
        if (item.getStatus() == 0) {//进行中的订单
            helper.getView(R.id.order_item_num_layout).setVisibility(View.GONE);
            helper.getView(R.id.order_item_seller_layout).setVisibility(View.GONE);
            helper.getView(R.id.order_item_purchase_layout).setVisibility(View.GONE);
            helper.getView(R.id.order_item_pendpurh_layout).setVisibility(View.VISIBLE);
            helper.getView(R.id.order_item_forsale_layout).setVisibility(View.VISIBLE);
            TextView pendHint = helper.getView(R.id.order_item_pendpurh_hint);
            TextView pendContent = helper.getView(R.id.order_item_pendpurh);
            mSubmit.setText(R.string.trand_withdrawal);
            mSubmit.setVisibility(View.VISIBLE);
            mSubmit.setBackground(mContext.getDrawable(R.drawable.bg_toge_child_item_tv_blue));
            mSubmit.setTextColor(mContext.getColor(R.color.white));
            mSubmit.setEnabled(true);
            if (item.getTransactionType() == 1) {
                pendHint.setText("待购买数量");
            } else {
                pendHint.setText("待出售数量");
            }
            mSubmit.setText("撤单");
            pendContent.setText(item.getDeal() + "");
        } else { //已完成订单
            mSubmit.setText(R.string.trand_withdrawal);
            mSubmit.setVisibility(View.VISIBLE);
            mSubmit.setBackground(mContext.getDrawable(R.drawable.bg_toge_child_item_tv_gray));
            mSubmit.setTextColor(mContext.getColor(R.color.gray_white));
            mSubmit.setEnabled(false);
            helper.getView(R.id.order_item_num_layout).setVisibility(View.VISIBLE);
            helper.getView(R.id.order_item_seller_layout).setVisibility(View.VISIBLE);
            helper.getView(R.id.order_item_purchase_layout).setVisibility(View.VISIBLE);
            helper.getView(R.id.order_item_forsale_layout).setVisibility(View.VISIBLE);
            helper.getView(R.id.order_item_pendpurh_layout).setVisibility(View.GONE);
            TextView mSellerHint = helper.getView(R.id.order_item_seller_hint);
            TextView mSellerContent = helper.getView(R.id.order_item_seller);
            TextView mOrderNum = helper.getView(R.id.order_item_num);
            TextView mPurchase = helper.getView(R.id.order_item_purchase);
            if (item.getTransactionType() == 1) {
                mSellerHint.setText("买家");

            } else {
                mSellerHint.setText("卖家");
            }
            mSubmit.setText("已成交");
            mSellerContent.setText(item.getNickname());
            mOrderNum.setText(item.getOrderNumber());
            mPurchase.setText(item.getDeal() + "");
        }
        mPrice.setText(item.getPrice() + " VRT");
        helper.addOnClickListener(R.id.order_item_submit);
    }
}
