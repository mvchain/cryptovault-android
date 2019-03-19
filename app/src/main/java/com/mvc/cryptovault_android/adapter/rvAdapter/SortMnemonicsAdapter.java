package com.mvc.cryptovault_android.adapter.rvAdapter;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mvc.cryptovault_android.R;
import com.mvc.cryptovault_android.bean.VerificationMnemonicBean;

import java.util.List;

public class SortMnemonicsAdapter extends BaseQuickAdapter<VerificationMnemonicBean, BaseViewHolder> {
    public SortMnemonicsAdapter(int layoutResId, @Nullable List<VerificationMnemonicBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, VerificationMnemonicBean item) {
        TextView contentView = helper.getView(R.id.content);
        helper.setText(R.id.content, item.getContent());
        contentView.setText(item.getContent());
        if (item.isCheck()) {
            contentView.setTextColor(ContextCompat.getColor(mContext, R.color.white));
            contentView.setBackgroundResource(R.drawable.shape_item_verification_mnemonics_bg_gray);
        } else {
            contentView.setTextColor(ContextCompat.getColor(mContext, R.color.login_content));
            contentView.setBackgroundResource(R.drawable.shape_item_verification_mnemonics_bg_white_gray);
        }
        helper.addOnClickListener(R.id.layout);
    }
}
