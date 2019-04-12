package com.mvc.ttpay_android.adapter.rvAdapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mvc.ttpay_android.R
import com.mvc.ttpay_android.bean.BlockTransactionBean
import java.text.SimpleDateFormat
import java.util.*

class BlockTransactionAdapter(layoutResId: Int, data: List<BlockTransactionBean.DataBean>) : BaseQuickAdapter<BlockTransactionBean.DataBean, BaseViewHolder>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder, item: BlockTransactionBean.DataBean) {
        helper.setText(R.id.item_hash, item.hash)
        helper.setText(R.id.item_number, "${item.confirm}个确认")
        helper.setText(R.id.item_time, "${SimpleDateFormat("yyyy-MM-dd").format(Date(item.createdAt))}" +
                 "\n${SimpleDateFormat("HH:mm:ss").format(Date(item.createdAt))}")
        helper.addOnClickListener(R.id.layout)
    }
}