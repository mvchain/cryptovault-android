package com.mvc.cryptovault_android.adapter.rvAdapter;

import android.support.annotation.Nullable;
import android.util.TimeUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.bean.FinancialListBean;

import java.text.SimpleDateFormat;
import java.util.List;

public class FinanciaAdapter extends BaseQuickAdapter<FinancialListBean.DataBean, BaseViewHolder> {
    public FinanciaAdapter(int layoutResId, @Nullable List<FinancialListBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FinancialListBean.DataBean item) {
        helper.setText(R.id.stop_time, com.blankj.utilcode.util.TimeUtils.millis2String(item.getStopAt(), new SimpleDateFormat("yyyy-MM-dd")));
    }
}
