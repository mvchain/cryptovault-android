package com.mvc.cryptovault_android.adapter.rvAdapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mvc.cryptovault_android.R;

import java.util.List;

public class MineMnemonicsAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public MineMnemonicsAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.content, item);
    }
}
