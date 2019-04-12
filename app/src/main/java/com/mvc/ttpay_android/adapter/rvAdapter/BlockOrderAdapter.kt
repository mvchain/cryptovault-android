package com.mvc.ttpay_android.adapter.rvAdapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mvc.ttpay_android.R
import com.mvc.ttpay_android.bean.BlockOrderBean
import java.text.SimpleDateFormat
import java.util.*

class BlockOrderAdapter(layoutResId: Int, data: List<BlockOrderBean.DataBean>) : BaseQuickAdapter<BlockOrderBean.DataBean, BaseViewHolder>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder, item: BlockOrderBean.DataBean) {
        helper.setText(R.id.item_type, if (item.classify == 0) "转账" else "币币交易")
        helper.setText(R.id.item_actual, SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date(item.createdAt)))
        helper.addOnClickListener(R.id.layout)
    }
}