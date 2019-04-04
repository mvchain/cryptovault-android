package com.mvc.cryptovault_android.adapter.rvAdapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.bean.PublishDetailListBean
import com.mvc.cryptovault_android.utils.TextUtils

import java.text.SimpleDateFormat

class PublishDetailAdapter(layoutResId: Int, data: List<PublishDetailListBean.DataBean>?) : BaseQuickAdapter<PublishDetailListBean.DataBean, BaseViewHolder>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder, item: PublishDetailListBean.DataBean) {
        helper.setText(R.id.publish_time, SimpleDateFormat("yyyy.MM.dd").format(item.createdAt))
        helper.setText(R.id.publish_number,"+ ${TextUtils.doubleToEight(item.value)}${item.tokenName}")
    }
}
