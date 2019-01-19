package com.mvc.cryptovault_android.adapter.rvAdapter

import com.blankj.utilcode.util.TimeUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mvc.cryptovault_android.R
import com.mvc.cryptovault_android.bean.FinancialListBean

import java.text.SimpleDateFormat

class FinanciaAdapter(layoutResId: Int, data: List<FinancialListBean.DataBean>?) : BaseQuickAdapter<FinancialListBean.DataBean, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder, item: FinancialListBean.DataBean) {
        helper.setText(R.id.stop_time, "下线时间：${TimeUtils.millis2String(item.stopAt, SimpleDateFormat("yyyy-MM-dd"))}")
        helper.setText(R.id.income, "${item.incomeMin}-${item.incomeMax} %")
        helper.setText(R.id.name, item.name)
        helper.setText(R.id.tag_time, "签到${item.times}天")
        helper.setText(R.id.tag_start, "${item.minValue}${item.baseTokenName}起投")
        helper.addOnClickListener(R.id.financial_layout)
    }
}
