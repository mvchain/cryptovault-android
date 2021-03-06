package com.mvc.cryptovault_android.adapter.rvAdapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.bean.BlockListBean
import com.mvc.cryptovault_android.bean.BlockNodeBean
import java.text.SimpleDateFormat
import java.util.*

class BlockNodeAdapter(layoutResId: Int, data: List<BlockNodeBean>) : BaseQuickAdapter<BlockNodeBean, BaseViewHolder>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder, item: BlockNodeBean) {
        helper.setText(R.id.item_node_name, "${item.nodeName}")
        helper.setText(R.id.item_node_height, "${item.nodeHeight}")
    }
}