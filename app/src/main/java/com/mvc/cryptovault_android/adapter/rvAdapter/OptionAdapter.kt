package com.mvc.cryptovault_android.adapter.rvAdapter

import com.allen.library.SuperTextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.bean.OptionBean
import com.mvc.cryptovault_android.utils.TextUtils
import java.util.ArrayList

class OptionAdapter(layoutResId: Int, data: ArrayList<OptionBean.DataBean>?) : BaseQuickAdapter<OptionBean.DataBean, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder?, item: OptionBean.DataBean?) {
        var superText = helper?.getView<SuperTextView>(R.id.option_title)
        superText!!.setLeftTopString(item?.name)
        superText!!.setLeftBottomString("剩余签到${item?.times}天")
        helper?.setText(R.id.yesterday_earnings, TextUtils.doubleToEight(item!!.value) + item.tokenName)
        helper?.setText(R.id.option_price, TextUtils.doubleToEight(item!!.partake) + item.baseTokenName)
        helper?.addOnClickListener(R.id.item_layout)
    }
}