package com.mvc.cryptovault_android.adapter.rvAdapter

import android.widget.ProgressBar
import com.blankj.utilcode.util.TimeUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.bean.FinancialListBean
import com.mvc.cryptovault_android.utils.TextUtils

import java.text.SimpleDateFormat

class FinanciaAdapter(layoutResId: Int, data: List<FinancialListBean.DataBean>?) : BaseQuickAdapter<FinancialListBean.DataBean, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder, item: FinancialListBean.DataBean) {
        var bar = helper.getView<ProgressBar>(R.id.remaining_amount_progress)
        helper.setText(R.id.stop_time, "下线时间：${TimeUtils.millis2String(item.stopAt, SimpleDateFormat("yyyy-MM-dd"))}")
        var income: String
        if (item.incomeMax <= 0 && item.incomeMin <= 0) {
            income = "0"
        } else if (item.incomeMin <= 0) {
            income = TextUtils.doubleToDouble(item.incomeMax)
        } else if (item.incomeMax <= 0) {
            income = TextUtils.doubleToDouble(item.incomeMin)
        } else {
            income = "${TextUtils.doubleToDouble(item.incomeMin)}-${TextUtils.doubleToDouble(item.incomeMax)}"
        }
        helper.setText(R.id.income, "$income %")
        helper.setText(R.id.name, item.name)
        helper.setText(R.id.remaining_amount, "产品剩余额度:${TextUtils.doubleToFourPrice(item.limitValue - item.sold)} ${item.baseTokenName}")
        bar.max = item.limitValue.toInt()
        bar.progress = (item.limitValue - item.sold).toInt()
        helper.setText(R.id.tag_time, "理财周期${item.times}天")
        helper.setText(R.id.tag_start, "${TextUtils.doubleToEight(item.minValue)}${item.baseTokenName}起投")
        helper.addOnClickListener(R.id.financial_layout)
    }
}
