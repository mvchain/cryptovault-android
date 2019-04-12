package com.mvc.ttpay_android.adapter.rvAdapter;

import android.support.annotation.Nullable;

import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mvc.ttpay_android.R;
import com.mvc.ttpay_android.bean.InvatationBean;

import java.text.SimpleDateFormat;
import java.util.List;

public class InvatitionAdapter extends BaseQuickAdapter<InvatationBean.DataBean, BaseViewHolder> {
    public InvatitionAdapter(int layoutResId, @Nullable List<InvatationBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, InvatationBean.DataBean item) {
        helper.setText(R.id.name,item.getNickname());
        helper.setText(R.id.time,TimeUtils.millis2String(item.getCreatedAt(),new SimpleDateFormat("yyyy-MM-dd")));
    }
}
