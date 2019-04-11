package com.mvc.cryptovault_android.utils

import com.blankj.utilcode.util.SPUtils
import com.mvc.cryptovault_android.bean.ExchangeRateBean

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat

import com.mvc.cryptovault_android.common.Constant.SP.DEFAULT_RATE
import com.mvc.cryptovault_android.common.Constant.SP.SET_RATE

object TextUtils {
    fun stringToInt(string: String): Int {
        return Integer.valueOf(string)
    }

    fun doubleToInt(db: Double): Int? {
        return db.toInt()
    }

    fun stringToDouble(string: String): Double {
        return java.lang.Double.parseDouble(string)
    }

    fun doubleToEight(price: Double): String {
        val format = DecimalFormat("###################.########")
        format.maximumFractionDigits = 8
        val number = format.format(price)
        return when (number.indexOf('.')) {
            -1 -> {
                number
            }
            else -> {
                number
            }
        }
    }

    fun doubleToFourPrice(price: Double): String {
        val format = DecimalFormat("0.0000")
        return format.format(price)
    }

    fun doubleToSix(price: Double): String {
        val decimal = BigDecimal(java.lang.Double.toString(price))
        return decimal.setScale(6, RoundingMode.DOWN).toString()
    }

    fun doubleToDouble(price: Double): String {
        val format = DecimalFormat("0.00")
        return format.format(price)
    }

    //
    fun stringToFloat(price: String): Float {
        return java.lang.Float.valueOf(price)!!
    }

    fun toBigDecimal(price: Double): String {
        return BigDecimal(java.lang.Double.toString(price)).setScale(4, RoundingMode.DOWN).toString()
    }

    fun toFourFloat(price: Float): String {
        val format = DecimalFormat("0.0000")
        return format.format(price.toDouble())
    }

    fun rateToPrice(price: Double): String {
        val format = DecimalFormat("0.00")
        val set_rate = SPUtils.getInstance().getString(SET_RATE)
        val default_rate = SPUtils.getInstance().getString(DEFAULT_RATE)
        val setBean = JsonHelper.stringToJson(set_rate, ExchangeRateBean.DataBean::class.java) as ExchangeRateBean.DataBean
        val defaultBean = JsonHelper.stringToJson(default_rate, ExchangeRateBean.DataBean::class.java) as ExchangeRateBean.DataBean
        return if (setBean.name == "CNY") {
            format.format(price * setBean.value)
        } else {
            format.format(price / setBean.value)
        }
    }
}
