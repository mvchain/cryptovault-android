package com.mvc.ttpay_android.adapter.rvAdapter

import com.allen.library.SuperTextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mvc.ttpay_android.MyApplication
import com.mvc.ttpay_android.R
import com.mvc.ttpay_android.bean.OptionBean
import com.mvc.ttpay_android.utils.TextUtils
import java.util.ArrayList

class OptionAdapter(layoutResId: Int, data: ArrayList<OptionBean.DataBean>?) : BaseQuickAdapter<OptionBean.DataBean, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder?, item: OptionBean.DataBean) {
        var superText = helper?.getView<SuperTextView>(R.id.option_title)
        superText!!.setLeftTopString(item.name)
        if(item.needSign == 1){
            superText.setLeftBottomString("${MyApplication.getAppContext().getString(R.string.remaining_check_in)}${item.times}${MyApplication.getAppContext().getString(R.string.times)}")
        }else{
            superText.setLeftBottomString("${MyApplication.getAppContext().getString(R.string.remaining_period)}${item.times}${MyApplication.getAppContext().getString(R.string.day)}")
        }
        helper?.setText(R.id.yesterday_earnings, TextUtils.doubleToEight(item.value) + item.tokenName)
        helper?.setText(R.id.option_price, TextUtils.doubleToEight(item.partake) + item.baseTokenName)
        helper?.addOnClickListener(R.id.item_layout)
    }
}