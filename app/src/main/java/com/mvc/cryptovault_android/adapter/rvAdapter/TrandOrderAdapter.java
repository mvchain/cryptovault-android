package com.mvc.cryptovault_android.adapter.rvAdapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.api.ApiStore;
import com.mvc.cryptovault_android.bean.TrandChildBean;
import com.mvc.cryptovault_android.bean.TrandOrderBean;
import com.mvc.cryptovault_android.bean.UpdateBean;
import com.mvc.cryptovault_android.common.Constant;
import com.mvc.cryptovault_android.event.TrandOrderEvent;
import com.mvc.cryptovault_android.utils.JsonHelper;
import com.mvc.cryptovault_android.utils.RetrofitUtils;
import com.mvc.cryptovault_android.utils.RxHelper;
import com.mvc.cryptovault_android.view.DialogHelper;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import io.reactivex.functions.Consumer;

import static com.mvc.cryptovault_android.common.Constant.SP.TOKEN;

public class TrandOrderAdapter extends BaseQuickAdapter<TrandOrderBean.DataBean, BaseViewHolder> {
    private List<TrandChildBean.DataBean> orderBean;
    private Dialog mHintDialog;

    public TrandOrderAdapter(int layoutResId, @Nullable List<TrandOrderBean.DataBean> data) {
        super(layoutResId, data);
        orderBean = ((TrandChildBean) JsonHelper.stringToJson(SPUtils.getInstance().getString(Constant.SP.TRAND_LIST), TrandChildBean.class)).getData();
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void convert(BaseViewHolder helper, TrandOrderBean.DataBean item) {
        TextView mPrice = helper.getView(R.id.order_item_forsale);
        TextView mSubmit = helper.getView(R.id.order_item_submit);
        TextView mTitle = helper.getView(R.id.order_item_title);
        TextView pendHint = helper.getView(R.id.order_item_pendpurh_hint);
        TextView mSellerHint = helper.getView(R.id.order_item_seller_hint);
        TextView mOrderNum = helper.getView(R.id.order_item_num);
        TextView mPurchase = helper.getView(R.id.order_item_purchase);
        TextView mPendContent = helper.getView(R.id.order_item_pendpurh);
        int position = 0;
        if (item.getTransactionType() == 1) {
            mSellerHint.setText("买家");
            pendHint.setText("待购买数量");
            for (int i = 0; i < orderBean.size(); i++) {
                if (orderBean.get(i).getPairId() == item.getPairId()) {
                    mTitle.setText("购买 " + orderBean.get(i).getPair());
                    position = i;
                    break;
                }
            }
        } else {
            pendHint.setText("待出售数量");
            mSellerHint.setText("卖家");
            for (int i = 0; i < orderBean.size(); i++) {
                if (orderBean.get(i).getPairId() == item.getPairId()) {
                    mTitle.setText("出售 " + orderBean.get(i).getPair());
                    position = i;
                    break;
                }
            }
        }
        TrandChildBean.DataBean dataBean = orderBean.get(position);
        mPurchase.setText(item.getDeal() + " " + dataBean.getTokenName());
        mPendContent.setText(item.getDeal() + " " + dataBean.getTokenName());
        mPrice.setText(item.getPrice() + dataBean.getPair().substring(0, dataBean.getPair().indexOf("/")));
        if (item.getStatus() == 0) {//进行中的订单
            helper.getView(R.id.order_item_num_layout).setVisibility(View.GONE);
            helper.getView(R.id.order_item_seller_layout).setVisibility(View.GONE);
            helper.getView(R.id.order_item_purchase_layout).setVisibility(View.GONE);
            helper.getView(R.id.order_item_pendpurh_layout).setVisibility(View.VISIBLE);
            helper.getView(R.id.order_item_forsale_layout).setVisibility(View.VISIBLE);
            mSubmit.setText(R.string.trand_withdrawal);
            mSubmit.setVisibility(View.VISIBLE);
            mSubmit.setBackground(mContext.getDrawable(R.drawable.bg_toge_child_item_tv_blue));
            mSubmit.setTextColor(mContext.getColor(R.color.white));
            mSubmit.setEnabled(true);
            if (item.getTransactionType() == 1) {
            }
            mSubmit.setText("撤单");
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
            TextView mSellerContent = helper.getView(R.id.order_item_seller);
            mSubmit.setText("已成交");
            mSellerContent.setText(item.getNickname());
            mOrderNum.setText(item.getOrderNumber());
        }
        helper.getView(R.id.order_item_submit).setOnClickListener(v -> {
            mHintDialog = DialogHelper.getInstance().create(mContext, "确认撤除 " + dataBean.getPair() + "的挂单?", viewId -> {
                switch (viewId) {
                    case R.id.hint_cancle:
                        mHintDialog.dismiss();
                        break;
                    case R.id.hint_enter:
                        mHintDialog.dismiss();
                        String token = SPUtils.getInstance().getString(TOKEN);
                        RetrofitUtils.client(ApiStore.class).cancleOrder(token, item.getId())
                                .compose(RxHelper.rxSchedulerHelper())
                                .subscribe(updateBean -> {
                                    if (updateBean.getCode() == 200) {
                                        EventBus.getDefault().post(new TrandOrderEvent());
                                        Toast.makeText(mContext, "订单撤销成功", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(mContext, "取消失败", Toast.LENGTH_SHORT).show();
                                    }
                                }, throwable -> {
                                    LogUtils.e(TAG, throwable.getMessage());
                                });
                        break;
                }
            });
            mHintDialog.show();
        });
    }
}
