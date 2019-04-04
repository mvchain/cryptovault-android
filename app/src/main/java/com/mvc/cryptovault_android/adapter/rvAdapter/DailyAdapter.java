package com.mvc.cryptovault_android.adapter.rvAdapter;

import android.support.annotation.Nullable;

import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.bean.OptionDailyIncomeBean;
import com.mvc.cryptovault_android.utils.TextUtils;

import java.text.SimpleDateFormat;
import java.util.List;

public class DailyAdapter extends BaseQuickAdapter<OptionDailyIncomeBean.DataBean, BaseViewHolder> {
    public DailyAdapter(int layoutResId, @Nullable List<OptionDailyIncomeBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, OptionDailyIncomeBean.DataBean item) {
        helper.setText(R.id.time, TimeUtils.millis2String(item.getCreatedAt(), new SimpleDateFormat("yyyy-MM-dd")));
        helper.setText(R.id.price, TextUtils.doubleToEight(item.getIncome()));
    }
}
