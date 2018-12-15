package com.mvc.cryptovault_android.adapter.rvAdapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.bean.FilterBean;

import java.util.List;

public class FilterAdapter extends BaseQuickAdapter<FilterBean, BaseViewHolder> {
    public FilterAdapter(int layoutResId, @Nullable List<FilterBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FilterBean item) {
        TextView tv = helper.getView(R.id.filter_item);
        tv.setText(item.getTitle());
        if (item.isCheck()) {
            tv.setBackground(mContext.getDrawable(R.drawable.shape_filter_blue_14dp));
            tv.setTextColor(mContext.getColor(R.color.white));
        }else{
            tv.setBackground(mContext.getDrawable(R.drawable.shape_filter_light_blue_14dp));
            tv.setTextColor(mContext.getColor(R.color.trand_gray));
        }
        helper.addOnClickListener(R.id.filter_item);
    }
}
