package com.mvc.cryptovault_android.utils;

import android.util.Log;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.mvc.cryptovault_android.bean.ExchangeRateBean;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParsePosition;

import static com.mvc.cryptovault_android.common.Constant.SP.DEFAULE_RATE;
import static com.mvc.cryptovault_android.common.Constant.SP.SET_RATE;

public class TextUtils {
    public static Integer stringToInt(String string) {
        return Integer.valueOf(string);
    }

    public static Integer doubleToInt(double db) {
        return (int) db;
    }

    public static Double stringToDouble(String string) {
        return Double.parseDouble(string);
    }

    public static String doubleToFour(double price) {
        BigDecimal decimal = new BigDecimal(Double.toString(price));
        return decimal.setScale(4, RoundingMode.DOWN).toString();
    }

    public static String doubleToFourPrice(double price) {
        DecimalFormat format = new DecimalFormat("0.0000");
        return format.format(price);
    }
    public static String doubleToSix(double price) {
        BigDecimal decimal = new BigDecimal(Double.toString(price));
        return decimal.setScale(6, RoundingMode.DOWN).toString();
    }

    public static String doubleToDouble(double price) {
        DecimalFormat format = new DecimalFormat("0.00");
        return format.format(price);
    }

    //
    public static float stringToFloat(String price) {
        return Float.valueOf(price);
    }

    public static String toBigDecimal(double price) {
        return new BigDecimal(Double.toString(price)).setScale(4, RoundingMode.DOWN).toString();
    }

    public static String toFourFloat(float price) {
        DecimalFormat format = new DecimalFormat("0.0000");
        return format.format(price);
    }

    public static String rateToPrice(double price) {
        DecimalFormat format = new DecimalFormat("0.00");
        String set_rate = SPUtils.getInstance().getString(SET_RATE);
        String default_rate = SPUtils.getInstance().getString(DEFAULE_RATE);
        ExchangeRateBean.DataBean setBean = (ExchangeRateBean.DataBean) JsonHelper.stringToJson(set_rate, ExchangeRateBean.DataBean.class);
        ExchangeRateBean.DataBean defaultBean = (ExchangeRateBean.DataBean) JsonHelper.stringToJson(default_rate, ExchangeRateBean.DataBean.class);
        if (setBean.getName().equals("CNY")) {
            return format.format(price * setBean.getValue());
        } else {
            return format.format(price / setBean.getValue());
        }
    }
}
