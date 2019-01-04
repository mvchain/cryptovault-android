package com.mvc.cryptovault_android.adapter.rvAdapter;

import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.bean.IncreaseBean;

import java.util.List;

public class IncreaseAdapter extends BaseQuickAdapter<IncreaseBean, BaseViewHolder> {
    public IncreaseAdapter(int layoutResId, @Nullable List<IncreaseBean> data) {
        super(layoutResId, data);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void convert(BaseViewHolder helper, IncreaseBean item) {
        ImageView icon = helper.getView(R.id.item_increase_icon);
        TextView add = helper.getView(R.id.item_increase_add);
        helper.setText(R.id.item_increase_title, item.getTitle());
        helper.setText(R.id.item_increase_content, item.getZhContent());
        add.setVisibility(item.isVisi() ? View.VISIBLE : View.GONE);
        if (!item.isAdd()) {
            add.setBackground(ContextCompat.getDrawable(mContext,R.drawable.shape_round_orangey_15dp));
            add.setText(R.string.increase_remove);
        } else {
            add.setBackground(ContextCompat.getDrawable(mContext,R.drawable.shape_round_ching_15dp));
            add.setText(R.string.increase_add);
        }
        Glide.with(mContext).load(item.getResId()).into(icon);
        helper.addOnClickListener(R.id.item_increase_add);
    }
}
