package com.mvc.cryptovault_android.adapter.rvAdapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mvc.cryptovault_android.R;

import java.util.List;

public class RateAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public RateAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.rate_item_content, item);
        helper.addOnClickListener(R.id.rate_item_content);
    }
}
