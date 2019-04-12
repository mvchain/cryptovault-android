package com.mvc.ttpay_android.adapter.rvAdapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mvc.ttpay_android.R;
import com.mvc.ttpay_android.bean.VerificationMnemonicBean;

import java.util.List;

public class CheckMnemonicsAdapter extends BaseQuickAdapter<VerificationMnemonicBean, BaseViewHolder> {
    public CheckMnemonicsAdapter(int layoutResId, @Nullable List<VerificationMnemonicBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, VerificationMnemonicBean item) {
        helper.setText(R.id.content, item.getContent());
        helper.addOnClickListener(R.id.content);
    }
}
