package com.mvc.ttpay_android.adapter.rvAdapter

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.blankj.utilcode.util.TimeUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.mvc.ttpay_android.MyApplication
import com.mvc.ttpay_android.R
import com.mvc.ttpay_android.bean.FinancialListBean
import com.mvc.ttpay_android.utils.TextUtils

import java.text.SimpleDateFormat

class FinanciaAdapter(layoutResId: Int, data: List<FinancialListBean.DataBean>?) : BaseQuickAdapter<FinancialListBean.DataBean, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder, item: FinancialListBean.DataBean) {
        var bar = helper.getView<ProgressBar>(R.id.remaining_amount_progress)
        helper.setText(R.id.stop_time, "${MyApplication.getAppContext().getString(R.string.offline_time)}：${TimeUtils.millis2String(item.stopAt, SimpleDateFormat("yyyy-MM-dd"))}")
        var income = if (item.incomeMax <= 0 && item.incomeMin <= 0) {
            "0"
        } else if (item.incomeMin <= 0) {
            TextUtils.doubleToDouble(item.incomeMax)
        } else if (item.incomeMax <= 0) {
            TextUtils.doubleToDouble(item.incomeMin)
        } else {
            "${TextUtils.doubleToDouble(item.incomeMin)}-${TextUtils.doubleToDouble(item.incomeMax)}"
        }
        if(item.needSign == 1){
            helper.setText(R.id.tag_time, "${MyApplication.getAppContext().getString(R.string.need_to_sign_in)}${item.times}${MyApplication.getAppContext().getString(R.string.times)}")
            helper.getView<ImageView>(R.id.sign_out).visibility = View.VISIBLE
        }else{
            helper.setText(R.id.tag_time, "${MyApplication.getAppContext().getString(R.string.financial_cycle)}${item.times}${MyApplication.getAppContext().getString(R.string.day)}")
            helper.getView<ImageView>(R.id.sign_out).visibility = View.INVISIBLE
        }
        helper.setText(R.id.income, "$income%")
        helper.setText(R.id.name, item.name)
        helper.setText(R.id.remaining_amount, "${MyApplication.getAppContext().getString(R.string.product_remaining_amount)}:${TextUtils.doubleToEight(item.limitValue - item.sold)} ${item.baseTokenName}")
        bar.max = item.limitValue.toInt()
        bar.progress = (item.limitValue - item.sold).toInt()
        helper.setText(R.id.tag_start, "${TextUtils.doubleToEight(item.minValue)}${item.baseTokenName}${MyApplication.getAppContext().getString(R.string.starting)}")
        helper.addOnClickListener(R.id.financial_layout)
    }
}
