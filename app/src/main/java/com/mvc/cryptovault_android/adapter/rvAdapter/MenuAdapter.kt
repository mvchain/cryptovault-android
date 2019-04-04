package com.mvc.cryptovault_android.adapter.rvAdapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.bean.TrandChildBean

class MenuAdapter(layoutResId: Int, data: ArrayList<String>) : BaseQuickAdapter<String, BaseViewHolder>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder, item: String) {
        helper.setText(R.id.item_content, "$item")
        helper.addOnClickListener(R.id.item_content)
    }
}