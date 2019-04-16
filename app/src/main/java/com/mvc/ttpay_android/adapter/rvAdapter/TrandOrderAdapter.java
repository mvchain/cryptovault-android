package com.mvc.ttpay_android.adapter.rvAdapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mvc.ttpay_android.MyApplication;
import com.mvc.ttpay_android.R;
import com.mvc.ttpay_android.api.ApiStore;
import com.mvc.ttpay_android.bean.TrandChildBean;
import com.mvc.ttpay_android.bean.TrandOrderBean;
import com.mvc.ttpay_android.common.Constant;
import com.mvc.ttpay_android.event.TrandOrderEvent;
import com.mvc.ttpay_android.utils.JsonHelper;
import com.mvc.ttpay_android.utils.RetrofitUtils;
import com.mvc.ttpay_android.utils.RxHelper;
import com.mvc.ttpay_android.utils.TextUtils;
import com.mvc.ttpay_android.view.DialogHelper;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import static com.mvc.ttpay_android.common.Constant.SP.TOKEN;

public class TrandOrderAdapter extends BaseQuickAdapter<TrandOrderBean.DataBean, BaseViewHolder> {
    private List<TrandChildBean.DataBean> orderBean;
    private Dialog mHintDialog;

    public TrandOrderAdapter(int layoutResId, @Nullable List<TrandOrderBean.DataBean> data) {
        super(layoutResId, data);
        TrandChildBean trandChildBean = (TrandChildBean) JsonHelper.stringToJson(SPUtils.getInstance().getString(Constant.SP.TRAND_LIST), TrandChildBean.class);
        if (trandChildBean != null) {
            orderBean = trandChildBean.getData();
        }
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
        helper.setText(R.id.order_item_time, TimeUtils.millis2String(item.getCreatedAt()));
        int position = 0;
        if (item.getTransactionType() == 2) {
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
        mPendContent.setText(item.getDeal() + " MVC");
        mPrice.setText(TextUtils.INSTANCE.doubleToEight(item.getPrice()) + " " + dataBean.getPair().substring(0, dataBean.getPair().indexOf("/")));
        if (item.getStatus() == 0) {//进行中的订单
            helper.getView(R.id.order_item_num_layout).setVisibility(View.GONE);
            helper.getView(R.id.order_item_seller_layout).setVisibility(View.GONE);
            helper.getView(R.id.order_item_purchase_layout).setVisibility(View.GONE);
            helper.getView(R.id.order_item_pendpurh_layout).setVisibility(View.VISIBLE);
            helper.getView(R.id.order_item_forsale_layout).setVisibility(View.VISIBLE);
            mSubmit.setText(R.string.trand_withdrawal);
            mSubmit.setVisibility(View.VISIBLE);
            mSubmit.setBackground(ContextCompat.getDrawable(mContext,R.drawable.bg_login_submit));
            mSubmit.setTextColor(ContextCompat.getColor(mContext,R.color.white));
            mSubmit.setEnabled(true);
            if (item.getTransactionType() == 1) {
            }
            mSubmit.setText(R.string.trand_withdrawal);
        } else { //已完成订单
            mSubmit.setText(R.string.trand_withdrawal);
            mSubmit.setVisibility(View.VISIBLE);
            mSubmit.setBackground(ContextCompat.getDrawable(mContext,R.drawable.bg_toge_child_item_tv_gray));
            mSubmit.setTextColor(ContextCompat.getColor(mContext,R.color.gray_white));
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
            mHintDialog = DialogHelper.Companion.getInstance().create(mContext, "确认撤除 " + dataBean.getPair() + "的挂单?", viewId -> {
                switch (viewId) {
                    case R.id.hint_cancle:
                        mHintDialog.dismiss();
                        break;
                    case R.id.hint_enter:
                        mHintDialog.dismiss();
                        String token = SPUtils.getInstance().getString(TOKEN);
                        RetrofitUtils.client(MyApplication.getBaseUrl(),ApiStore.class).cancleOrder(token, item.getId())
                                .compose(RxHelper.rxSchedulerHelper())
                                .subscribe(updateBean -> {
                                    if (updateBean.getCode() == 200) {
                                        EventBus.getDefault().post(new TrandOrderEvent());
                                        ToastUtils.showLong("订单撤销成功");
                                    } else {
                                        ToastUtils.showLong("取消失败");
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
