package com.mvc.cryptovault_android.adapter.rvAdapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.bean.BlockListBean
import java.text.SimpleDateFormat
import java.util.*

class BlockListAdapter(layoutResId: Int, data: List<BlockListBean.DataBean>) : BaseQuickAdapter<BlockListBean.DataBean, BaseViewHolder>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder, item: BlockListBean.DataBean) {
        helper.setText(R.id.item_hash, "${item.blockId}")
        helper.setText(R.id.item_number, "${item.transactions}")
        helper.setText(R.id.item_time, "${SimpleDateFormat("yyyy-MM-dd").format(Date(item.createdAt))}" +
                "\n${SimpleDateFormat("HH:mm:ss").format(Date(item.createdAt))}")
    }
}