package com.mvc.cryptovault_android.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.adapter.rvAdapter.RateAdapter;
import com.mvc.cryptovault_android.listener.IPopViewListener;

import java.util.ArrayList;

public class PopViewHelper {
    private PopupWindow mPopView;
    private static PopViewHelper mDialogHelper;
    private IPopViewListener iPopViewListener;

    public static PopViewHelper getInstance() {
        if (mDialogHelper == null) {
            synchronized (PopViewHelper.class) {
                mDialogHelper = new PopViewHelper();
            }
        }
        return mDialogHelper;
    }

    public PopupWindow create(Context context, int layoutId, ArrayList<String> content) {
        LinearLayout linear = (LinearLayout) LayoutInflater.from(context.getApplicationContext()).inflate(layoutId, null);
        RecyclerView mRateView = linear.findViewById(R.id.rate_rv);
        RateAdapter adapter = new RateAdapter(R.layout.item_rate_rv, content);
        mRateView.setAdapter(adapter);
        mRateView.addItemDecoration(new RuleRecyclerLines(context.getApplicationContext(), RuleRecyclerLines.HORIZONTAL_LIST, 1));
        adapter.setOnItemChildClickListener((adapter1, view, position) -> {
            switch (view.getId()) {
                case R.id.rate_item_content:
                    mPopView.dismiss();
                    if (iPopViewListener != null) {
                        iPopViewListener.toRate(position);
                    }
                    break;
            }
        });
        mPopView = new PopupWindow(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopView.setOnDismissListener(()->{
            if (iPopViewListener != null) {
                iPopViewListener.dismiss();
            }
        });
        mPopView.setOutsideTouchable(true);
        mPopView.setBackgroundDrawable(new ColorDrawable());
        mPopView.setContentView(linear);
        return mPopView;
    }

    public void dismiss() {
        if (mPopView != null && mPopView.isShowing()) {
            mPopView.dismiss();
        }
    }

    public PopViewHelper setiPopViewListener(IPopViewListener iPopViewListener) {
        this.iPopViewListener = iPopViewListener;
        return this;
    }
}
