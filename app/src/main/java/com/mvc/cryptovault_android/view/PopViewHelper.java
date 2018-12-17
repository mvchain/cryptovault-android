package com.mvc.cryptovault_android.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.adapter.rvAdapter.RateAdapter;
import com.mvc.cryptovault_android.listener.IPayWindowListener;
import com.mvc.cryptovault_android.listener.IPopViewListener;
import com.mvc.cryptovault_android.listener.PswMaxListener;

import java.util.ArrayList;

public class PopViewHelper {
    private PopupWindow mPopView;
    private static PopViewHelper mDialogHelper;

    public static PopViewHelper getInstance() {
        if (mDialogHelper == null) {
            synchronized (PopViewHelper.class) {
                mDialogHelper = new PopViewHelper();
            }
        }
        return mDialogHelper;
    }


    public PopupWindow create(Context context, int layoutId, ArrayList<String> content, IPopViewListener iPopViewListener) {
        LinearLayout linear = (LinearLayout) LayoutInflater.from(context.getApplicationContext()).inflate(layoutId, null);
        RecyclerView mRateView = linear.findViewById(R.id.rate_rv);
        RateAdapter adapter = new RateAdapter(R.layout.item_rate_rv, content);
        mRateView.setAdapter(adapter);
        mRateView.addItemDecoration(new RuleRecyclerLines(context.getApplicationContext(), RuleRecyclerLines.HORIZONTAL_LIST, 1));
        adapter.setOnItemChildClickListener((adapter1, view, position) -> {
            switch (view.getId()) {
                case R.id.rate_item_content:
                    if (iPopViewListener != null) {
                        mPopView.dismiss();
                        iPopViewListener.changeRate(position);
                    }
                    break;
            }
        });
        mPopView = new PopupWindow(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopView.setOnDismissListener(() -> {
            if (iPopViewListener != null) {
                iPopViewListener.dismiss();
            }
        });
        mPopView.setOutsideTouchable(true);
        mPopView.setBackgroundDrawable(new ColorDrawable());
        mPopView.setContentView(linear);
        return mPopView;
    }

    @SuppressLint("WrongConstant")
    public PopupWindow create(Context context, int layoutId, String title, String childTitle, String price, String address, String money, boolean isOut, IPayWindowListener iPayWindowListener, PswMaxListener maxListener) {
        LinearLayout linear = (LinearLayout) LayoutInflater.from(context.getApplicationContext()).inflate(layoutId, null);
        linear.findViewById(R.id.line).setVisibility(isOut ? View.VISIBLE : View.GONE);
        //初始化控件
        TextView mTitlePay = linear.findViewById(R.id.pay_title);
        mTitlePay.setText(title);
        ImageView mClosePay = linear.findViewById(R.id.pay_close);
        mClosePay.setOnClickListener(v -> iPayWindowListener.onclick(v));
        TextView mChildTitlePay = linear.findViewById(R.id.pay_child_title);
        mChildTitlePay.setText(childTitle);
        TextView mPricePay = linear.findViewById(R.id.pay_price);
        mPricePay.setText(price);
        TextView mAddressPay = linear.findViewById(R.id.pay_address);
        mAddressPay.setText(address);
        LinearLayout mAddressLayoutPay = linear.findViewById(R.id.pay_address_layout);
        mAddressLayoutPay.setVisibility(isOut ? View.VISIBLE : View.GONE);
        TextView mFeePay = linear.findViewById(R.id.pay_fee);
        mFeePay.setText(money);
        LinearLayout mFeeLayoutPay = linear.findViewById(R.id.pay_fee_layout);
        mFeeLayoutPay.setVisibility(isOut ? View.VISIBLE : View.GONE);
        PswText mTextPay = linear.findViewById(R.id.pay_text);
        mTextPay.setOnClickListener(v -> iPayWindowListener.onclick(v));
        mTextPay.setMaxListener(maxListener);
        TextView mForgetPay = linear.findViewById(R.id.pay_forget);
        mForgetPay.setOnClickListener(v -> iPayWindowListener.onclick(v));
        mPopView = new PopupWindow(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopView.setOnDismissListener(() -> {
            if (iPayWindowListener != null) {
                iPayWindowListener.dismiss();
            }
        });
        mPopView.setContentView(linear);
        mPopView.setFocusable(true);
        mPopView.setBackgroundDrawable(new BitmapDrawable());
        mPopView.setOutsideTouchable(false);
        mPopView.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return mPopView;
    }
    @SuppressLint("WrongConstant")
    public PopupWindow create(Context context, int layoutId, String title, String childTitle, String price, String money, IPayWindowListener iPayWindowListener, PswMaxListener maxListener) {
        LinearLayout linear = (LinearLayout) LayoutInflater.from(context.getApplicationContext()).inflate(layoutId, null);
        linear.findViewById(R.id.line).setVisibility(View.GONE);
        //初始化控件
        TextView mTitlePay = linear.findViewById(R.id.pay_title);
        mTitlePay.setText(title);
        ImageView mClosePay = linear.findViewById(R.id.pay_close);
        mClosePay.setOnClickListener(v -> iPayWindowListener.onclick(v));
        TextView mChildTitlePay = linear.findViewById(R.id.pay_child_title);
        mChildTitlePay.setText(childTitle);
        TextView mPricePay = linear.findViewById(R.id.pay_price);
        mPricePay.setText(price);
        TextView mAddressPay = linear.findViewById(R.id.pay_address);
        LinearLayout mAddressLayoutPay = linear.findViewById(R.id.pay_address_layout);
        mAddressLayoutPay.setVisibility(View.GONE);
        TextView mFeePay = linear.findViewById(R.id.pay_fee);
        mFeePay.setText(money);
        LinearLayout mFeeLayoutPay = linear.findViewById(R.id.pay_fee_layout);
        ((TextView)mFeeLayoutPay.findViewById(R.id.price)).setText("预约数量");
        mFeeLayoutPay.setVisibility(View.VISIBLE);
        PswText mTextPay = linear.findViewById(R.id.pay_text);
        mTextPay.setOnClickListener(v -> iPayWindowListener.onclick(v));
        mTextPay.setMaxListener(maxListener);
        TextView mForgetPay = linear.findViewById(R.id.pay_forget);
        mForgetPay.setOnClickListener(v -> iPayWindowListener.onclick(v));
        mPopView = new PopupWindow(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopView.setOnDismissListener(() -> {
            if (iPayWindowListener != null) {
                iPayWindowListener.dismiss();
            }
        });
        mPopView.setContentView(linear);
        mPopView.setFocusable(true);
        mPopView.setBackgroundDrawable(new BitmapDrawable());
        mPopView.setOutsideTouchable(false);
        mPopView.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return mPopView;
    }

    public void dismiss() {
        if (mPopView != null && mPopView.isShowing()) {
            mPopView.dismiss();
        }
    }
}
