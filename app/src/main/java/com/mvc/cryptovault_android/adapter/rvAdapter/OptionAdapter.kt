package com.mvc.cryptovault_android.adapter.rvAdapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.bean.OptionBean
import com.mvc.cryptovault_android.utils.TextUtils
import java.util.ArrayList

class OptionAdapter(layoutResId: Int, data: ArrayList<OptionBean.DataBean>?) : BaseQuickAdapter<OptionBean.DataBean, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder?, item: OptionBean.DataBean?) {
        helper?.setText(R.id.option_title, item?.name)
        helper?.setText(R.id.time, "${item?.times}")
        helper?.setText(R.id.yesterday_earnings, TextUtils.doubleToFour(item!!.value))
        helper?.setText(R.id.option_price, TextUtils.doubleToFour(item!!.partake))
        helper?.addOnClickListener(R.id.item_layout)
    }
}