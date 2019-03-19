package com.mvc.cryptovault_android.adapter.rvAdapter;

import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.bean.MsgBean;

import java.util.List;

public class MsgAdapter extends BaseQuickAdapter<MsgBean.DataBean, BaseViewHolder> {
    public MsgAdapter(int layoutResId, @Nullable List<MsgBean.DataBean> data) {
        super(layoutResId, data);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void convert(BaseViewHolder helper, MsgBean.DataBean item) {
        helper.setText(R.id.item_title, item.getMessage());
        helper.setText(R.id.item_time, TimeUtils.millis2String(item.getCreatedAt()));
    }
}
