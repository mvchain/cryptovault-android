package com.mvc.ttpay_android.adapter.rvAdapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mvc.ttpay_android.R
import com.mvc.ttpay_android.bean.TrandChildBean

class RatioAdapter(layoutResId: Int, data: ArrayList<TrandChildBean.DataBean>) : BaseQuickAdapter<TrandChildBean.DataBean, BaseViewHolder>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder, item: TrandChildBean.DataBean) {
        helper.setText(R.id.item_content, "${item.tokenName}")
        helper.addOnClickListener(R.id.item_content)
    }
}