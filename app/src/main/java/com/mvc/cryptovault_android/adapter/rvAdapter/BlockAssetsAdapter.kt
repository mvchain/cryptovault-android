package com.mvc.cryptovault_android.adapter.rvAdapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.bean.BlockBalanceBean
import com.mvc.cryptovault_android.utils.TextUtils

class BlockAssetsAdapter(layoutResId: Int, data: List<BlockBalanceBean.DataBean>) : BaseQuickAdapter<BlockBalanceBean.DataBean, BaseViewHolder>(layoutResId, data) {
    override fun convert(helper: BaseViewHolder, item: BlockBalanceBean.DataBean) {
        helper.setText(R.id.item_type, item.tokenName)
        helper.setText(R.id.item_actual, TextUtils.doubleToFour(item.value))
    }
}